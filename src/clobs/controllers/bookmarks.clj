(ns clobs.controllers.bookmarks
    (:require [clobs.data.bookmarks         :as     bookmarks-data]
              [clobs.data.user_bookmark     :as     user-bm-data]
              [clobs.data.tags              :as     tags-data]
              [clobs.responses              :refer  [conflict-status ok-status created-status empty-res]]
              [clojure.pprint               :refer  [pprint]]
              [clojure.string               :refer  [replace]]
              [net.cgrand.enlive-html       :as     html]
              [org.httpkit.client           :as     http]))

; impure
(defn web-scrap! [url]
    (some-> url
            http/get {:insecure? true}               ;gets content from url
            deref                   ;dereferences content from url
            html/html-snippet
            (html/select [:title])  ;selects title tag
            first                   ;returns the first element of a strucutre with the tags content
            :content                ;get tag content
            first))                 ;get first element from content

; pure
(defn refine-url [url]
    (-> url
        (replace #".*?://" "")
        (replace #"/.*" "")))

; impure
(defn generate-bookmark-name! ;webscraping function
    [url]
    (let [name (web-scrap! url)]
        (if (nil? name)
            (refine-url url)
            name)))

; impure
(defn create-bookmark!
    [url]
    (let [bookmark (bookmarks-data/get-url url)]
        (if bookmark
            (:id bookmark)
            (as-> url u
                  (bookmarks-data/insert u (generate-bookmark-name! u))
                  (:generated_key u)))))

; impure
(defn insert
    [request]
    (let [body (:body request)
          url (:url body)
          name (:name body)
          private (:private body)
          tags (set (:tags body))
          user-id (get-in request [:session :user-id])
          bookmark-id (create-bookmark! url)]
        (if (user-bm-data/get-userbm user-id bookmark-id)
            (conflict-status {:error "Bookmark already registered!"} )
            (do
              (tags-data/insert-many-tags! tags bookmark-id user-id)
              (-> (user-bm-data/insert bookmark-id user-id name private)
                created-status )))))

(defn assoc-tags
  [bookmark user-id]
  (assoc bookmark :tags (tags-data/recover-user-tags (:id bookmark) user-id :value)))

(defn get-one
    [request]
    (let [bookmark-id (get-in request [:params :id])
          bookmark (bookmarks-data/get-bookmark bookmark-id)]
        (ok-status bookmark)))

(defn get-all
    [request]
    (let [user-id (get-in request [:session :user-id])
          bookmarks (bookmarks-data/get-all user-id)]
        (ok-status (map #(assoc-tags % user-id) bookmarks))))

(defn update!
    [request]
    (let [user-id     (get-in request [:session :user-id])
          bookmark-id (get-in request [:body :id])
          name        (get-in request [:body :name])
          private     (get-in request [:body :private])
          tags        (set (get-in request [:body :tags]))]
        (tags-data/update-tags! tags bookmark-id user-id)
        (user-bm-data/update-user-bookmark user-id bookmark-id name private)
        (ok-status {:user-id user-id
                    :bookmark-id bookmark-id
                    :name name
                    :private private})))

(defn delete
    [request]
    (let [user-id (get-in request [:session :user-id])
          bookmark-id (get-in request [:params :id])]
        (user-bm-data/remove-user-bookmark user-id bookmark-id)
        empty-res))

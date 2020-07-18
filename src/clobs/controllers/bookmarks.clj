(ns clobs.controllers.bookmarks
    (:require [clobs.data.bookmarks         :as     bookmarks-data]
              [clobs.data.user_bookmark     :as     user-bm-data]
              [clobs.responses              :refer  [conflict-status ok-status created-status]]
              [clojure.pprint               :refer  [pprint]]
              [clojure.string               :refer  [replace]]
              [net.cgrand.enlive-html       :as     html]
              [org.httpkit.client           :as     http]))

(defn web-scrap! [url]
    (some-> url
            http/get                ;gets content from url -> SIDE EFFECT!
            deref                   ;dereferences content from url
            html/html-snippet
            (html/select [:title])  ;selects title tag
            first                   ;returns the first element of a strucutre with the tags content
            :content                ;get tag content
            first))                 ;get first element from content

(defn refine-url [url]
    (-> url
        (replace #".*?://" "")
        (replace #"/.*" "")))

(defn generate-bookmark-name! ;webscraping function
    [url]
    (let [name (web-scrap! url)]
        (if (nil? name)
            (refine-url url)
            name)))
          
(defn create-bookmark
    [url]
    (let [bookmark (bookmarks-data/get-url url)]
        (if bookmark
            (:id bookmark)
            (as-> url u
                  (bookmarks-data/insert u (generate-bookmark-name! u))
                  (:generated_key u)))))

(defn insert
    [request]
    (let [body (:body request)
          url (:url body)
          name (:name body)
          private (:private body)
          user-id (get-in request [:session :user-id])
          bookmark-id (create-bookmark url)]
        (if (user-bm-data/get-userbm user-id bookmark-id)
            (conflict-status {:error "Bookmark already registered!"} )
            (created-status (user-bm-data/insert bookmark-id user-id name private)))))

(defn get
    [request]
    (let [session (:session request)
          bookmark-id (get-in request [:params :id])
          user-id (:user-id session)]
        (ok-status (bookmarks-data/get-bookmark bookmark-id))))

(defn get-all
    [request]
    (let [user-id (get-in request [:session :user-id])]
        (ok-status (bookmarks-data/get-all user-id))))

(defn update
    [request]
    (let [user-id     (get-in request [:session :user-id])
          bookmark-id (get-in request [:body :id])
          name        (get-in request [:body :name])
          private     (get-in request [:body :private])]
        (user-bm-data/update-user-bookmark user-id bookmark-id name private)))

(defn delete
    [request]
    (let [user-id (get-in request [:session :user-id])
          bookmark-id (get-in request [:params :id])]
        (user-bm-data/remove-user-bookmark user-id bookmark-id)))

(ns clobs.blueprints.bookmarks
    (:require [clobs.data.bookmarks         :as     bookmarks-data]
              [clobs.data.user_bookmark     :as     user-bm-data]
              [clobs.auth                   :refer  [response-messages]]
              [clojure.pprint               :refer  [pprint]]
              [net.cgrand.enlive-html       :as     html]
              [org.httpkit.client           :as     http]))


(defn generate-bookmark-name! ;webscraping function
    [url]
    (as-> url u
          (http/get u)              ;gets content from url -> SIDE EFFECT!
          (html/html-snippet @u)    ;dereferences content from url
          (html/select u [:title])  ;selects title tag
          (first u)                 ;returns the first element of a strucutre with the tags content
          (:content u)              ;get tag content
          (first u)))               ;get first element from content
    
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
            ((:conflict response-messages) "Bookmark já cadastrada!")
            ((:created response-messages)(user-bm-data/insert bookmark-id user-id name private)))))

(defn get
    [request]
    (let [session (:session request)
          bookmark-id (get-in request [:params :id])
          user-id (:user-id session)]
        (if (user-bm-data/user-has-bookmark user-id bookmark-id)
            ((:ok-status response-messages)(bookmarks-data/get-bookmark bookmark-id))
            {:status 401})))

(defn get-all
    [request]
    (let [user-id (get-in request [:session :user-id])]
        ((:ok-status response-messages)(bookmarks-data/get-all user-id))))

(defn update
    [request]
    (let [user-id     (get-in request [:session :user-id])
          bookmark-id (get-in request [:body :id])
          name        (get-in request [:body :name])
          private     (get-in request [:body :private])]
        (if (user-bm-data/user-has-bookmark user-id bookmark-id)
            (user-bm-data/update-user-bookmark user-id bookmark-id name private)
            {:status 401})))

(defn delete
    [request]
    (let [user-id (get-in request [:session :user-id])
          bookmark-id (get-in request [:params :id])]
        (if (user-bm-data/user-has-bookmark user-id bookmark-id)
            (user-bm-data/remove-user-bookmark user-id bookmark-id)
            {:status 401})))

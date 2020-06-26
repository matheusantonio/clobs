(ns clobs.blueprints.user_bookmarks
    (:require [clobs.data.bookmarks :as bookmarks-data]
              [clojure.pprint                   :refer [pprint]]
              [clobs.data.user_bookmark :as user-bm-data]
              [net.cgrand.enlive-html :as html]
              [org.httpkit.client :as http]))

;; This file will be used to deal with route requests, validate results, etc

(defn generate-bookmark-name
    [url]
    (first (:content (first (html/select (html/html-snippet @(http/get url))[:title]))))) ;webscrapping here

(defn create-bookmark
    [url]
    (let [bookmark (bookmarks-data/get-url url)]
        (if bookmark
            (:bookmark/id bookmark)
            (as-> url u
                  (bookmarks-data/insert u (generate-bookmark-name u))
                  (:GENERATED_KEY u)))))

(defn insert
    [request]
    (let [body (:body request)
          url (:url body)
          name (:name body)
          private (:private body)
          user-id (get-in request [:session :user-id])
          bookmark-id (create-bookmark url)]
        (if (user-bm-data/get-userbm user-id bookmark-id)
            {:status 401 :error "User already has bookmark!"}
            (user-bm-data/insert bookmark-id user-id name private))))

(defn get
    [id] (bookmarks-data/get-bookmark id))

(def get-all
    bookmarks-data/get-all)

(defn update
    [url name id]
    (bookmarks-data/update url name id))

(defn delete
    [id]
    (bookmarks-data/delete id))
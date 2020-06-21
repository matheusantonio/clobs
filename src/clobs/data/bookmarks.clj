(ns clobs.data.bookmarks
    (:require [clojure.pprint       :refer [pprint]]
              [next.jdbc            :as jdbc]
              [next.jdbc.sql        :as sql]
              [next.jdbc.connection :as connection]))

;; This file will be used for data retrieving

(def db {:dbtype "mysql" :user "matheus" :password "matheus" :dbname "clobs"})
(def ds (jdbc/get-datasource db))

(def bookmarks
    (list
        {
            :bookmarkId 1
            :name "github"
            :url "https://github.com/matheusantonio"
        }
        {
            :bookmarkId 2
            :name "facebook"
            :url "https://facebook.com.br"
        }
        {
            :bookmarkId 3
            :name "google"
            :url "https://google.com.br"
        }
        {
            :bookmarkId 4
            :name "youtube"
            :url "https://youtube.com.br"
        }
        {
            :bookmarkId 5
            :name "clojure lists"
            :url "https://clojuredocs.org/clojure.core/list"
        }))

(defn get-bookmark
    [id]
    (sql/get-by-id ds :bookmark id))

(defn insert
    [url name]
    (sql/insert! ds :bookmark {:url url :name name}))



(def top-bookmarks
    bookmarks)

(def recent-bookmarks
    bookmarks)
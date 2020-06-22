(ns clobs.blueprints.user_bookmarks
    (:require [clobs.data.bookmarks :as bookmarks-data]))

;; This file will be used to deal with route requests, validate results, etc

(defn insert
    [url name]
    (bookmarks-data/insert url name))

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
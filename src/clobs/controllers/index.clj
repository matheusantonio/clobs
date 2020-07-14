(ns clobs.controllers.index
    (:require [clobs.data.bookmarks :as    bookmarks-data]
              [clobs.auth           :refer [ok-status]]))

;; This file will be used to deal with route requests, validate results, etc

(defn top
    []
    (ok-status (bookmarks-data/top-bookmarks)))

(defn recent
    []
    (ok-status (bookmarks-data/recent-bookmarks)))

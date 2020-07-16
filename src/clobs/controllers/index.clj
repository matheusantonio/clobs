(ns clobs.controllers.index
    (:require [clobs.data.bookmarks  :as    bookmarks-data]
              [clobs.responses       :refer [ok-status]]))

(defn top
    []
    (ok-status (bookmarks-data/top-bookmarks)))

(defn recent
    []
    (ok-status (bookmarks-data/recent-bookmarks)))

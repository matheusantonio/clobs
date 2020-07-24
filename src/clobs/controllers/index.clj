(ns clobs.controllers.index
    (:require [clobs.data.bookmarks  :as    bookmarks-data]
              [clobs.responses       :refer [ok-status]]))

(defn top
    []
    (-> (bookmarks-data/top-bookmarks)
        ok-status))

(defn recent
    []
    (-> (bookmarks-data/recent-bookmarks)
        ok-status))

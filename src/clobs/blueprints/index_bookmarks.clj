(ns clobs.blueprints.index_bookmarks
    (:require [clobs.data.bookmarks :as bookmarks-data]))

;; This file will be used to deal with route requests, validate results, etc

(def top
    bookmarks-data/top-bookmarks)

(def recent
    bookmarks-data/recent-bookmarks)

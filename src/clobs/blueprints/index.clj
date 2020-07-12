(ns clobs.blueprints.index
    (:require [clobs.data.bookmarks :as    bookmarks-data]
              [clobs.auth           :refer [ok-status]]))

;; This file will be used to deal with route requests, validate results, etc

(def top
    (ok-status bookmarks-data/top-bookmarks))

(def recent
    (ok-status bookmarks-data/recent-bookmarks))

(ns clobs.blueprints.index
    (:require [clobs.data.bookmarks :as    bookmarks-data]
              [clobs.auth           :refer [response-messages]]))

;; This file will be used to deal with route requests, validate results, etc

(def top
    ((:ok-status response-messages) bookmarks-data/top-bookmarks))

(def recent
    ((:ok-status response-messages)bookmarks-data/recent-bookmarks))

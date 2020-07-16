(ns clobs.routes.index
    (:require [compojure.core            :refer  [GET]] 
              [clobs.controllers.index   :as     index]))

(def top-bookmarks
    (GET "/top-bookmarks"       []    (index/top))) ;;top 10 bookmarks

(def recent-bookmarks
    (GET "/recent-bookmarks"    []    (index/recent))) ;;last 10 bookmarks added
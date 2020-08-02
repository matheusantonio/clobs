(ns clobs.controllers.index
    (:require [clobs.data.bookmarks  :as    bookmarks-data]
              [clobs.data.tags       :as    tags-data]
              [clojure.pprint        :refer [pprint]]
              [clobs.responses       :refer [ok-status]]))

(defn assoc-tags
  [bookmark]
  (assoc bookmark :tags (tags-data/recover-bookmark-tags (:id bookmark))))

(defn top
  []
  (->> (bookmarks-data/top-bookmarks)
       (map assoc-tags)
       ok-status))

(defn recent
  []
  (->> (bookmarks-data/recent-bookmarks)
       (map assoc-tags)
       ok-status))

(defn search
  [request]
  (let [tag (get-in request [:query-params "tag"])
        limit (read-string (get-in request [:query-params "limit"]))
        offset (read-string (get-in request [:query-params "offset"]))]
    (->> (tags-data/search-tags tag limit offset)
         (map assoc-tags)
         ok-status)))
  
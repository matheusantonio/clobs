(ns clobs.data.tags_bookmarks
    (:require [clojure.pprint       :refer [pprint]]
              [next.jdbc.sql        :as    sql]
              [next.jdbc             :as    jdbc]
              [clobs.data.database  :refer [ds]]))

(defn recover-user-tags!
  [bookmark-id user-id]
  (jdbc/execute! ds 
             [(str "SELECT t.value "
                   "from tag as t "
                   "inner join tagBookmark as tb "
                   "on t.id = tb.tagId "
                   "where tb.bookmarkId = ? "
                   "&& tb.userId = ? ;"
                   ) bookmark-id user-id]))

(defn recover-bookmark-tags!
  [bookmark-id]
  (jdbc/execute! ds
                 [(str "SELECT t.value "
                       "from tag as t "
                       "inner join tagBookmark as tb "
                       "on t.id = tb.tagId "
                       "where tb.bookmarkId = ? ;"
                       ) bookmark-id]))
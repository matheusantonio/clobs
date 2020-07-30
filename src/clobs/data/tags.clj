(ns clobs.data.tags
    (:require [clojure.pprint       :refer [pprint]]
              [next.jdbc.sql        :as sql]
              [clobs.data.database :refer [ds]]))

(defn get-tag
  [id]
  (sql/get-by-id ds :tag id))


(ns clobs.data.tags
  (:require [clojure.pprint        :refer [pprint]]
            [clojure.set           :refer [difference]]
            [next.jdbc.sql         :as    sql]
            [next.jdbc             :as    jdbc]
            [clobs.data.database   :refer [ds]]))

(defn get-tag
  [id]
  (sql/get-by-id ds :tag {:id id}))

(defn tag-exists
  [value]
  (first
   (sql/find-by-keys ds :tag {:value value})))

(defn recover-user-tags!
  [bookmark-id user-id key]
  (map key
       (jdbc/execute! ds
                      [(str "SELECT t.id, t.value "
                            "from tag as t "
                            "inner join tagBookmark as tb "
                            "on t.id = tb.tagId "
                            "where tb.bookmarkId = ? "
                            "&& tb.userId = ? ;") bookmark-id user-id])))

(defn recover-bookmark-tags!
  [bookmark-id]
  (jdbc/execute! ds
                 [(str "SELECT t.value "
                       "from tag as t "
                       "inner join tagBookmark as tb "
                       "on t.id = tb.tagId "
                       "where tb.bookmarkId = ? ;") bookmark-id]))

(defn create-tag
  [value]
  (sql/insert! ds :tag {:value value}))

(defn create-if-not-exists
  [value]
  (let [tag (tag-exists value)]
    (if (nil? tag)
      (:generated_key (create-tag value))
      (:id tag))))

(defn assosciate-tag-bookmark!
  [tag-id bookmark-id user-id]
  (sql/insert! ds :tagBookmark
               {:tagId tag-id :bookmarkId bookmark-id :userId user-id}))

(defn insert-many-tags!
  [tags bookmark-id user-id]
  (doseq [tag tags]
    (assosciate-tag-bookmark!
     (create-if-not-exists tag)
     bookmark-id user-id)))

(defn remove-tag-bookmark!
  [tag-id bookmark-id user-id]
  (sql/delete! ds :tagBookmark
               {:tagId tag-id :bookmarkId bookmark-id :userId user-id}))

(defn create-many
  [tags]
  (set (map create-if-not-exists tags)))

(defn update-tags!
  [tags bookmark-id user-id]
  (let [actual-tags (set (recover-user-tags! bookmark-id user-id :id))
        to-add (difference (create-many tags) actual-tags)
        to-delete (difference actual-tags (create-many tags))]
    (doseq [tag to-delete]
      (remove-tag-bookmark! tag bookmark-id user-id))
    (doseq [tag to-add]
      (assosciate-tag-bookmark! tag bookmark-id user-id))))
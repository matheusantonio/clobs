(ns clobs.data.bookmarks
    (:require [clojure.pprint       :refer [pprint]]
              [next.jdbc            :as jdbc]
              [next.jdbc.sql        :as sql]
              [next.jdbc.connection :as connection]
              [clobs.data.database  :refer [ds]]))

;; This file will be used for data retrieving


(defn get-bookmark
    [id]
    (sql/get-by-id ds :bookmark id))

(defn get-url
    [url]
    (first (sql/find-by-keys ds :bookmark {:url url})))

(defn get-all
    [user-id]
    (jdbc/execute! ds
        [(str "select b.id, b.url, ub.definedName, ub.private "
              "from bookmark as b "
                "inner join userBookmark as ub "
                "on b.id = ub.bookmarkId "
              "where ub.userId = ? ;") user-id]))

(defn insert
    [url name]
    (sql/insert! ds :bookmark {:url url :name name}))

;(defn update
;    [url name id]
;    (sql/update! ds :bookmark {:url url :name name} {:id id}))

(defn delete
    [id]
    (sql/delete! ds :bookmark {:id id} ))

(defn top-bookmarks []
    (jdbc/execute! ds
        [(str
            "select b.*, count(*) as qtd "
            "from userBookmark as ub "
                "inner join bookmark as b "
                "on ub.bookmarkId = b.id "
            "where ub.private = false "
            "group by ub.bookmarkId "
            "order by qtd desc limit 10")]))

(defn recent-bookmarks []
    (jdbc/execute! ds
        [(str "select b.id, b.url, b.name, ub.createdAt "
            "from bookmark as b "
                "inner join userBookmark as ub "
                "on b.id = ub.bookmarkId "
            "where ub.private = false "
            "order by ub.createdAt desc;")]))
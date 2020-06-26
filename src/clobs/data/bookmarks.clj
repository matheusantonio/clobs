(ns clobs.data.bookmarks
    (:require [clojure.pprint       :refer [pprint]]
              [next.jdbc            :as jdbc]
              [next.jdbc.sql        :as sql]
              [next.jdbc.connection :as connection]
              [clobs.data.database  :refer [ds]]))

;; This file will be used for data retrieving



;(comment dummy bookmarks for testing
(def bookmarks
    (list
        {
            :bookmarkId 1
            :name "github"
            :url "https://github.com/matheusantonio"
        }
        {
            :bookmarkId 2
            :name "facebook"
            :url "https://facebook.com.br"
        }
        {
            :bookmarkId 3
            :name "google"
            :url "https://google.com.br"
        }
        {
            :bookmarkId 4
            :name "youtube"
            :url "https://youtube.com.br"
        }
        {
            :bookmarkId 5
            :name "clojure lists"
            :url "https://clojuredocs.org/clojure.core/list"
        }))
;)

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

(def top-bookmarks
    (jdbc/execute! ds
        [(str "select * from bookmark as b "
            "where exists ( "
                    "select b.id, count(*) as qtd "
                    "from userBookmark as ub "
                    "where ub.bookmarkId = b.id "
                    "and ub.private = false "
                    "group by ub.bookmarkId "
                    "order by qtd)"
            "limit 10;")]))

(def recent-bookmarks
    (jdbc/execute! ds
        [(str "select b.id, b.url, b.name, ub.createdAt "
            "from bookmark as b "
                "inner join userBookmark as ub "
                "on b.id = ub.bookmarkId "
            "where ub.private = false "
            "order by ub.createdAt desc;")]))
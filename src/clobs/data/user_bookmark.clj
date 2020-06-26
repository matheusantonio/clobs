(ns clobs.data.user_bookmark
    (:require [clojure.pprint       :refer [pprint]]
              [next.jdbc.sql        :as sql]
              [clobs.data.database :refer [ds]]
              [clojure.string           :as str]))


(defn get-userbm
    [user-id bookmark-id]
    (first (sql/find-by-keys ds :userBookmark {:userId user-id :bookmarkId bookmark-id})))

(defn get-by-user
    [user-id]
    (sql/find-by-keys ds :userBookmark {:userId user-id}))

(defn get-by-bookmark
    [bookmark-id]
    (sql/find-by-keys ds :userBookmark {:bookmarkId bookmark-id}))

(defn insert
    [bookmark-id user-id name private]
    (let [time (str/replace (java.time.LocalDateTime/now) "T" " ")]
        (sql/insert! ds :userBookmark {:userId user-id :bookmarkId bookmark-id
                                        :createdAt time :definedName name
                                        :private private})))

    
(ns clobs.data.users
    (:require [clojure.pprint       :refer [pprint]]
              [next.jdbc.sql        :as sql]
              [buddy.hashers :as hashers]
              [clobs.data.database  :refer [ds]]))

(defn get-user
    [id]
    (sql/get-by-id ds :user id))

(def get-all
    (sql/query ds ["select * from user"]))

(defn get-by-username
    [username]
    (let [user (sql/find-by-keys ds :user {:username username})]
        (if (> (count user) 0)
            (first user)
            nil)))

(defn insert
    [username password]
    (let [password-hash (hashers/encrypt password)]
        (sql/insert! ds :user {:username username :password password-hash})))
    
(defn update
    [username password id]
    (sql/update! ds :user {:username username :password password} {:id id}))

(defn delete
    [id]
    (sql/delete! ds :user {:id id} ))

(defn password-matches?
    [username pass]
    (some-> (get-by-username username)
            :password
            (->> (hashers/check pass))))


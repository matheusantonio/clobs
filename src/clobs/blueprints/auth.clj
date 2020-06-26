(ns clobs.blueprints.auth
    (:require [clobs.data.users                 :as    users-data]
              [clojure.pprint                   :refer [pprint]]
              [ring.util.response               :refer [response]]
              [clobs.data.users                 :refer [password-matches?]]))


;; User management
(defn retrieve-id [username]
    (:user/id (users-data/get-by-username username)))

(defn register-user [username password]
    (if (users-data/get-by-username username)
        (response {:error "User already exists!"})
        (users-data/insert username password)))

;; Session management
(defn login [username password session]
    (if (password-matches? username password)
        (let [new-session (assoc session :user-id (retrieve-id username))]
          (-> (response "Logged in session!")
              (assoc :session new-session)))
        (response {:error "Não autorizado!"})))

(defn logout []
    (-> (response "Session deleted")
        (assoc :session nil)))

(defn current-user [session]
    (let [id (:user-id session)]
        (if id
            (users-data/get-user id)
            nil)))



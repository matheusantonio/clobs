(ns clobs.blueprints.auth
    (:require [clobs.data.users                 :as    users-data]
              [clojure.pprint                   :refer [pprint]]
              [clobs.auth                       :refer [response-messages]]
              [clobs.data.users                 :refer [password-matches?]]))


;; User management
(defn retrieve-id [username]
    (:id (users-data/get-by-username username)))

(defn register-user [username password]
    (if (users-data/get-by-username username)
        ((:conflict response-messages) "User already exists!")
        (users-data/insert username password)))

;; Session management
(defn login [username password session]
    (if (password-matches? username password)
        (let [new-session (assoc session :user-id (retrieve-id username))]
          (-> ((:ok-status response-messages) "Logged in session!")
              (assoc :session new-session)))
        ({:status 401})))

(defn logout []
    (-> ((:ok-status response-messages) "Session deleted")
        (assoc :session nil)))

(defn current-user [session]
    (let [id (:user-id session)]
        (if id
            (users-data/get-user id)
            nil)))



(ns clobs.controllers.auth
    (:require [clobs.data.users                 :as    users-data]
              [clojure.pprint                   :refer [pprint]]
              [clobs.auth                       :refer [conflict-status ok-status unauthorized-status not-acceptable-status created-status]]
              [clobs.data.users                 :refer [password-matches?]]))


;; User management
(defn retrieve-id [username]
    (:id (users-data/get-by-username username)))

(defn register-user [request]
    (let [username (get-in request [:body :username])
          password (get-in request [:body :password])]
        (if (or (nil? username) (nil? password))
            (not-acceptable-status {:error "Username or password not recovered"})
            (if (users-data/get-by-username username)
                (conflict-status {:error "User already exists!"})
                (created-status (users-data/insert username password))))))
    

;; Session management
(defn login [username password session]
    (if (password-matches? username password)
        (let [new-session (assoc session :user-id (retrieve-id username))]
          (-> (ok-status "Logged in session!")
              (assoc :session new-session)))
        (unauthorized-status {:error "Usuário ou senha incorretos"})))

(defn logout []
    (-> (ok-status "Session deleted")
        (assoc :session nil)))

(defn current-user [session]
    (let [id (:user-id session)]
        (if id
            (users-data/get-user id)
            nil)))



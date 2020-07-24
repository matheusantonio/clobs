(ns clobs.controllers.auth
    (:require [clobs.data.users                 :as    users-data]
              [clojure.pprint                   :refer [pprint]]
              [clojure.string                   :as    string]
              [clobs.responses                  :refer [conflict-status ok-status unauthorized-status created-status]]))

;; User management
(defn retrieve-id [username]
    (:id (users-data/get-by-username username)))

(defn register-user [request]
    (let [username (get-in request [:body :username])
          password (get-in request [:body :password])]
        (if (users-data/get-by-username username)
            (conflict-status {:error "User already exists!"})
            (assoc-in
             (created-status (users-data/insert username password))
             [:body :message]
             "Verifique seu e-mail para confirmar seu registro."))))

(defn registration-confirmation [request]
    (let [hash (string/replace (get-in request [:params :hash]) " " "+")
          email (get-in request [:params :email])
          user (users-data/get-by-username email)]
        (if (or (nil? user) (not= (:password user) hash))
            (unauthorized-status {})
            (do (users-data/confirm-registration (:id user))
                (ok-status "Registro confirmado com sucesso.")))))

(defn login [request]
    (let [session (:session request)
          username (get-in request [:body :username])
          password (get-in request [:body :password])]
        (if (and (users-data/password-matches? username password) (users-data/registered? username))
            (let [new-session (assoc session :user-id (retrieve-id username))]
            (-> (ok-status "Logged in session!")
                (assoc :session new-session)))
            (unauthorized-status {:error "Usuário ou senha incorretos"}))))

(defn logout []
    (-> (ok-status "Session deleted")
        (assoc :session nil)))

(defn current-user [session]
    (let [id (:user-id session)]
        (if id
            (users-data/get-user id)
            nil)))



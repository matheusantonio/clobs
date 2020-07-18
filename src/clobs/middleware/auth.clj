(ns clobs.middleware.auth
    (:require [clojure.pprint   :refer [pprint]]
              [clobs.secrets    :refer [smtp-map]]
              [clobs.responses  :refer [unauthorized-status]]
              [clobs.data.users :refer [get-by-username]]
              [postal.core      :refer [send-message]]))

(defn not-authenticated?
    [request]
    (let [session (:session request)]
        (empty? session)))

(defn require-login [handler]
    (fn [request]
        (if (not-authenticated? request)
            (unauthorized-status {})
            (handler request))))

(defn send-email [user-email hash]
    (send-message smtp-map
                  {:from "matheuscardoso@id.uff.br"
                   :to user-email
                   :subject "Clobs Registration"
                   :body (str "Thanks for registering to Clobs application!\n
                            Please, click in the following link:\n
                            http://localhost:3000/clobs/user/confirm?email=" user-email "&hash=" hash
                            "\n Clobs API")}))

(defn send-confirmation-email [handler]
    (fn [request]
        (let [user-email (get-in request [:body :username])
              response (handler request)
              status (:status response)]
            (if (== status 201)
                (let [hash (:password (get-by-username user-email))]
                    (send-email user-email hash)
                    response)
                response))))
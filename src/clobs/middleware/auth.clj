(ns clobs.middleware.auth
    (:require [clojure.pprint  :refer [pprint]]
              [clobs.responses :refer [unauthorized-status]]
              [postal.core     :refer [send-message]]))

(defn not-authenticated?
    [request]
    (let [session (:session request)]
        (empty? session)))

(defn require-login [handler]
    (fn [request]
        (if (not-authenticated? request)
            (unauthorized-status {})
            (handler request))))



(defn send-email [user-email]
    (send-message {:host "smtp.sendgrid.net"
                   :user "apikey"
                   :pass "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"}
                  {:from "matheuscardoso@id.uff.br"
                   :to user-email
                   :subject "Clobs Registration"
                   :body "Thanks for registering to Clobs application!"}))

(defn send-confirmation-email [handler]
    (fn [request]
        (let [user-email (get-in request [:body :username])
              response (handler request)
              status (:status response)]
            (if (== status 201)
                ((send-email user-email)
                 response)
                response))))
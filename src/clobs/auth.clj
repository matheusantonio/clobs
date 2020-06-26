(ns clobs.auth
    (:require [ring.util.response               :refer [response]]
              [clojure.pprint                   :refer [pprint]]))


(def response-messages
    {
        :ok-status (fn [message] {:status 200 :message message})
        :error-status (fn [message] {:status 500 :message message})
        :unauthorized {:status 401 :error "Unauthorized"}
        :not-found {:status 404 :message "Not found"}
        :already-inserted {:status 401 :error "User already has bookmark!"}
    })


(defn is-authenticated?
    [request]
    (let [session (:session request)]
        (empty? session)))

(defn login-required
    ([request handler]
        (response
            (if (is-authenticated? request)
                (:unauthorized response-messages)
                (handler request))))
    ([request handler & params]
        (response
            (if (is-authenticated? request)
                (:unauthorized response-messages)
                (handler request params)))))

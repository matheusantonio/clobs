(ns clobs.auth
    (:require [clojure.pprint                   :refer [pprint]]))

(defn response [status body & {:as headers}]
    {:status status :body body :headers headers})

(def response-messages
    {
        :ok-status (partial response 200)
        :created (partial response 201)

        :error-status (partial response 500)
        
        :unauthorized (partial response 401)
        :not-found (partial response 404)
        :not-acceptable (partial response 406)
        :conflict (partial response 409)
    })


(defn is-authenticated?
    [request]
    (let [session (:session request)]
        (empty? session)))

(defn login-required
    ([request handler]
        (if (is-authenticated? request)
            (:unauthorized response-messages)
            (handler request)))
    ([request handler & params]
        (if (is-authenticated? request)
            (:unauthorized response-messages)
            (handler request params))))

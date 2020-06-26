(ns clobs.auth
    (:require [ring.util.response               :refer [response]]
              [clojure.pprint                   :refer [pprint]]))


(def unauthorized-message {:status 401 :error "Unauthorized"})

(defn is-authenticated?
    [request]
    (let [session (:session request)]
        (empty? session)))

(defn login-required
    ([request handler]
        (response
            (if (is-authenticated? request)
                unauthorized-message
                (handler request))))
    ([request handler & params]
        (response
            (if (is-authenticated? request)
                unauthorized-message
                (handler request params)))))

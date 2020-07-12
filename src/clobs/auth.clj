(ns clobs.auth
    (:require [clojure.pprint                   :refer [pprint]]))

(defn response [status body & {:as headers}]
    {:status status :body body :headers headers})

(def ok-status (partial response 200))
(def created-status (partial response 201))

(def error-status (partial response 500))
        
(def unauthorized-status (partial response 401))
(def not-found-status (partial response 404))
(def not-acceptable-status (partial response 406))
(def conflict-status (partial response 409))


(defn is-authenticated?
    [request]
    (let [session (:session request)]
        (empty? session)))

(defn login-required
    ([request handler]
        (if (is-authenticated? request)
            (unauthorized-status {})
            (handler request)))
    ([request handler & params]
        (if (is-authenticated? request)
            (unauthorized-status {})
            (handler request params))))

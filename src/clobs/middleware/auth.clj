(ns clobs.middleware.auth
    (:require [clojure.pprint       :refer [pprint]]
              [clobs.responses :refer [unauthorized-status]]))

(defn not-authenticated?
    [request]
    (let [session (:session request)]
        (empty? session)))

;; Login required middleware test
(defn require-login [handler]
    (fn [request]
        (if (not-authenticated? request)
            (unauthorized-status {})
            (handler request))))


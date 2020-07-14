(ns clobs.middleware.request
    (:require [clojure.core.match :refer [match]]
              [clobs.responses    :refer [not-acceptable-status]]
              [clojure.pprint       :refer [pprint]]))

(defn body-has-keys?
    [kl request]
    (match kl
        (_ :guard #(empty? %)) true
        :else (let [k (first kl)
                    v (get-in request [:body k])]
                (if (nil? v)
                    false
                    (body-has-keys? (rest kl) request)))))

(defn require-params [handler & params]
    (fn [request]
        (if (body-has-keys? params request)
            (handler request)
            (not-acceptable-status {:error "Some parameters are missing"}))))
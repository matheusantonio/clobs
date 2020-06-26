(ns clobs.auth
    (:require [ring.util.response               :refer [response]]
              [clojure.pprint                   :refer [pprint]]))


;(POST "/" request (login-required request bookmarks/insert))

(defn login-required
    [request handler]
    (let [session (:session request)]
        (response
            (if (empty? session)
                {:status 401 :error "Unauthorized"}
                (handler request)))))

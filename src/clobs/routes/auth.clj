(ns clobs.routes.auth
    (:require [compojure.core           :refer [context GET POST ]]
              [clobs.controllers.auth   :as     auth]
              [clobs.responses          :refer [ok-status unauthorized-status]]
              [clobs.middleware.request :refer [require-params]]))

(def routes
    (context "/auth" []
      
        (POST "/login" [] (-> auth/login
                              (require-params :username :password)))
        
        (GET "/logout" [] (auth/logout))

        (GET "/loged" {:keys [session]}
            (if (empty? session)
              (unauthorized-status { :error "Not authenticated"})
              (let [username (:username (auth/current-user session))]
                (ok-status {:username username}))))
    ))
(ns clobs.routes.auth
    (:require [compojure.core           :refer [context GET POST ]]
              [clobs.controllers.auth   :as     auth]
              [clobs.auth               :refer [ok-status unauthorized-status]]))

(def routes
    (context "/auth" []
      
        (POST "/login" request
          (let [session (:session request)
                username (get-in request [:body :username])
                password (get-in request [:body :password])]
            (auth/login username password session)))
        
        (GET "/logout" [] (auth/logout))

        (GET "/loged" {:keys [session]}
            (if (empty? session)
              (unauthorized-status { :error "Not authenticated"})
              (let [username (:username (auth/current-user session))]
                (ok-status {:username username}))))
    ))
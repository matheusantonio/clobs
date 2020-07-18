(ns clobs.routes.user
    (:require [compojure.core           :refer [context POST GET]]
              [clobs.controllers.auth   :as     auth]
              [clobs.middleware.request :refer [require-params]]
              [clobs.middleware.auth    :refer [send-confirmation-email]]))

(def routes
    (context "/user" []

        (POST "/register"   []    (-> auth/register-user
                                      send-confirmation-email
                                      (require-params :username :password)))
                                      
        (GET "/confirm"     []    auth/registration-confirmation)))
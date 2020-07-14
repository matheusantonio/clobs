(ns clobs.routes.user
    (:require [compojure.core           :refer [context defroutes POST]]
              [clobs.controllers.auth   :as     auth]
              [clobs.middleware.request :refer [require-params]]))

(def routes
    (context "/user" []

        (POST "/register"   []    (-> auth/register-user
                                      (require-params :username :password)))

    ))
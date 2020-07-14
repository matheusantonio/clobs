(ns clobs.routes.user
    (:require [compojure.core           :refer [context POST]]
              [clobs.controllers.auth   :as     auth]))

(def routes
    (context "/user" []

        (POST "/register"   request    (auth/register-user request))

    ))
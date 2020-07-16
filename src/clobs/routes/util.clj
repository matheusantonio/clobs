(ns clobs.routes.util
    (:require [compojure.route   :refer [not-found]]
              [clobs.responses   :refer [ok-status]]
              [compojure.core    :refer [context POST]]))

(def routes-debug
    (context "/debug" []
        (POST "/"          request    (ok-status (with-out-str (clojure.pprint/pprint request))))
        (POST "/:id"       request    (ok-status (with-out-str (clojure.pprint/pprint request))))))

(def route-not-found
    (not-found {:error "Not found"}))
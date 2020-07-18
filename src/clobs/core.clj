(ns clobs.core
  (:require [ring.adapter.jetty               :refer [run-jetty]]
            [clojure.pprint                   :refer [pprint]]
            [ring.middleware.json             :refer [wrap-json-response wrap-json-body]]
            [ring.middleware.params           :refer [wrap-params]]
            [ring.middleware.keyword-params   :refer [wrap-keyword-params]]
            [ring.middleware.session          :as     session]
            [ring.middleware.session.cookie   :refer [cookie-store]]
            [ring.middleware.cors             :refer [wrap-cors]]
            ;; Application routes
            [compojure.core                   :refer [routes context]]
            [clobs.routes.index               :as     index]
            [clobs.routes.bookmarks           :as     bookmarks]
            [clobs.routes.user                :as     user]
            [clobs.routes.auth                :as     auth]
            [clobs.routes.util                :as     util]))

(def my-routes
  (routes
    (context "/clobs" []
    
      ;;home page
      index/top-bookmarks
      index/recent-bookmarks

      ;;user bookmarks
      bookmarks/routes

      ;; Register users
      user/routes

      ;;Authentication
      auth/routes

      ;;from tutorial, for debugging
      util/routes-debug)

    util/route-not-found))

(def app
  (-> my-routes
      (session/wrap-session {:store (cookie-store {:key "33 118 164 239 9"})})
      (wrap-json-body {:keywords? true}) ; maps json body to clojure map
      wrap-json-response
      wrap-keyword-params
      wrap-params
      (wrap-cors :access-control-allow-origin [#"http://localhost:8080"] ; Vue pages
                 :access-control-allow-methods [:get :put :post :delete]
                 :access-control-allow-headers #{"accept"
                                                 "accept-encoding"
                                                 "accept-language"
                                                 "authorization"
                                                 "content-type"
                                                 "origin"}
                 :access-control-allow-credentials "true")))

(defn -main
  []
  (run-jetty app {:port 3000}))


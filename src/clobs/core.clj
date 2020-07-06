(ns clobs.core
  (:require [ring.adapter.jetty               :refer [run-jetty]]
            [clojure.pprint                   :refer [pprint]]
            [compojure.core                   :refer [routes context GET POST PUT DELETE]]
            [compojure.route                  :refer [not-found]]
            [ring.middleware.json             :refer [wrap-json-response wrap-json-body]]
            [ring.middleware.session          :as     session]
            [ring.middleware.session.cookie   :refer [cookie-store]]
            [ring.middleware.cors             :refer [wrap-cors]]
            [clobs.blueprints.index           :as     index]
            [clobs.blueprints.bookmarks       :as     bookmarks]
            [clobs.blueprints.auth            :as     auth]
            [clobs.auth                       :refer [login-required response-messages]]))

(def my-routes
  (routes
    (context "/clobs" []
    
      ;;home page
      (GET "/top-bookmarks"       []              index/top) ;;top 10 bookmarks
      (GET "/recent-bookmarks"    []              index/recent) ;;last 10 bookmarks added

      ;;user bookmarks
      (context "/bookmarks" []
        
        (GET "/"               request            (login-required request bookmarks/get-all)) ;;all user bookmarks
        (GET "/:id"            request            (login-required request bookmarks/get))     ;;bookmark by id

        (POST "/"              request            (login-required request bookmarks/insert))  ;;Create a bookmark
       
        (PUT "/"               request            (login-required request bookmarks/update))  ;;update an existing bookmark
          
        (DELETE "/:id"         request            (login-required request bookmarks/delete))  ;;delete an existing bookmark  
      )

      (context "/user" []

        (POST "/register" {{:keys [username password]} :body} (auth/register-user username password))

      )

      ;;Authentication
      (context "/auth" []
      
        (POST "/login" request
          (let [session (:session request)
                username (get-in request [:body :username])
                password (get-in request [:body :password])]
            (auth/login username password session)))
        
        (GET "/logout" [] (auth/logout))

        (GET "/loged" {:keys [session]}
            (if (empty? session)
              {:status 401}
              (let [username (:username (auth/current-user session))]
                ((:ok-status response-messages) username ))))
      )

      ;;from tutorial, for debugging
      (POST "/debug"                        request         ((:ok-status response-messages) (with-out-str (clojure.pprint/pprint request))))
      (POST "/debug/:id"                    request         ((:ok-status response-messages) (with-out-str (clojure.pprint/pprint request))))
    
    )

    (not-found {:error "Not found"})))


(def app
  (-> my-routes
      (session/wrap-session {:store (cookie-store {:key "33 118 164 239 9"})})
      (wrap-json-body {:keywords? true}) ; maps json body to clojure map
      wrap-json-response
      (wrap-cors :access-control-allow-origin [#"http://localhost:8080"]
                 :access-control-allow-methods [:get :put :post :delete]
                 :access-control-allow-headers #{"accept"
                                                 "accept-encoding"
                                                 "accept-language"
                                                 "authorization"
                                                 "content-type"
                                                 "origin"}
                 :access-control-allow-credentials "true")
      ))

(defn -main
  []
  (run-jetty app {:port 3000}))


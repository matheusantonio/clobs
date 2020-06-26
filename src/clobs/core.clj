(ns clobs.core
  (:require [ring.adapter.jetty               :refer [run-jetty]]
            [clojure.pprint                   :refer [pprint]]
            [compojure.core                   :refer [routes context GET POST PUT DELETE]]
            [compojure.route                  :refer [not-found]]
            [ring.middleware.json             :refer [wrap-json-response wrap-json-body]]
            [ring.middleware.session          :as session]
            [ring.util.response               :refer [response]]
            [clobs.blueprints.index_bookmarks :as index]
            [clobs.blueprints.user_bookmarks  :as bookmarks]
            [clobs.blueprints.auth            :as auth]
            [clobs.auth                       :refer [login-required]]))

(def my-routes
  (routes
    (context "/clobs" []
    
      ;;home page
      (GET "/top-bookmarks"       []              (response index/top)) ;;top 10 bookmarks
      (GET "/recent-bookmarks"    []              (response index/recent)) ;;last 10 bookmarks added
      

      ;;user bookmarks
      (context "/bookmarks" []
        
        (GET "/"               []              (response bookmarks/get-all)) ;;index page (all user bookmarks)
        (GET "/:id"            [id]            (response (bookmarks/get (Integer/parseInt id)))) ;;bookmark by id

        (POST "/" request (login-required request bookmarks/insert))

        ;(POST "/" {:keys [body]}      ;;create a new bookmark
        ;  (let [{:keys [url name]} body]
        ;    (response (bookmarks/insert url name))))
        
        (PUT "/" {:keys [body]}       ;;update an existing bookmark
          (let [{:keys [url name id]} body]
            (response (bookmarks/update url name id))))
          
        (DELETE "/:id" [id]          ;;delete an existing bookmark
          (response (bookmarks/delete id))) 
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

        (GET "/logged" {:keys [session]}
            (response
              (if (empty? session)
                {:status 401 :message "Não está autorizado!"}
                (let [username (:user/username (auth/current-user session))]
                  {:status 200 :message username}))))
      )

      ;;from tutorial, for debugging
      (POST "/debug"                    request         (response (with-out-str (clojure.pprint/pprint request))))
    
    )

    (not-found {:error "Not found"})))

(def app
  (-> my-routes
      session/wrap-session
      (wrap-json-body {:keywords? true}) ; maps json body to clojure map
      wrap-json-response ))

(defn -main
  []
  (run-jetty app {:port 3000}))
(ns clobs.core
  (:require [ring.adapter.jetty               :refer [run-jetty]]
            [clojure.pprint                   :refer [pprint]]
            [compojure.core                   :refer [routes GET POST PUT DELETE]]
            [compojure.route                  :refer [not-found]]
            [ring.middleware.json             :refer [wrap-json-response wrap-json-body]]
            [ring.util.response               :refer [response]]
            [clobs.blueprints.index_bookmarks :as index]
            [clobs.blueprints.user_bookmarks  :as user]))

(def my-routes
  (routes
    ;;home page
    (GET "/clobs/top-bookmarks"       []              (response index/top)) ;;top 10 bookmarks
    (GET "/clobs/recent-bookmarks"    []              (response index/recent)) ;;last 10 bookmarks added
    
    ;;user bookmarks
    (GET "/clobs/user"                []              (response user/get-all)) ;;index page (all user bookmarks)
    (GET "/clobs/user/:id"            [id]            (response (user/get (Integer/parseInt id)))) ;;bookmark by id

    (POST "/clobs/user" {:keys [body]}      ;;create a new bookmark
      (let [{:keys [url name]} body]
        (response (user/insert url name))))
    
    (PUT "/clobs/user" {:keys [body]}       ;;update an existing bookmark
      (let [{:keys [url name id]} body]
        (response (user/update url name id))))
       
    (DELETE "/clobs/user/:id" [id]          ;;delete an existing bookmark
      (response (user/delete id))) 

    ;;from tutorial
    (POST "/debug"                    request         (response (with-out-str (clojure.pprint/pprint request))))
    
    (not-found {:error "Not found"})))

(def app
  (-> my-routes
      (wrap-json-body {:keywords? true})
      wrap-json-response ))

(defn -main
  []
  (run-jetty app {:port 3000}))
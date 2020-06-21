(ns clobs.core
  (:require [ring.adapter.jetty               :refer [run-jetty]]
            [clojure.pprint                   :refer [pprint]]
            [compojure.core                   :refer [routes GET POST]]
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
    (GET "/clobs/user"                []              (response {})) ;;index page (all user bookmarks)
    (POST "/clobs/user" {:keys [body]}         ;(response (with-out-str (clojure.pprint/pprint request))))              ;;create a new bookmark
      (let [{:keys [url name]} body]
        (response (user/insert url name))))
    ;(UPDATE "/clobs/user"             [id url name]   (response {})) ;;update an existing bookmark
    ;(DELETE "/clobs/user"             [id]            (response {})) ;;delete an existing bookmark

    ;;from tutorial
    (GET "/clobs/user/:id"             [id]            (response (user/get (Integer/parseInt id))))
    (POST "/debug"                    request         (response (with-out-str (clojure.pprint/pprint request))))
    
    (not-found {:error "Not found"})))

(def app
  (-> my-routes
      (wrap-json-body {:keywords? true})
      wrap-json-response ))

(defn -main
  []
  (run-jetty app {:port 3000}))
(ns clobs.routes.bookmarks
    (:require [compojure.core               :refer [context wrap-routes GET POST PUT DELETE]]
              [clobs.controllers.bookmarks  :as     bookmarks]
              [clobs.middleware.auth        :refer [require-login]]
              [clobs.middleware.request     :refer [require-params]]))


(def bookmarks-context
    (context "/bookmarks" []
        
        (GET "/"          []   bookmarks/get-all) ;;all user bookmarks

        (GET "/:id"       []   bookmarks/get)     ;;bookmark by id

        (POST "/"         []   (-> bookmarks/insert
                                   (require-params :url :name :private)))  ;;Create a bookmark

        (PUT "/"          []   (-> bookmarks/update
                                   (require-params :id :name :private)))  ;;update an existing bookmark
          
        (DELETE "/:id"    []   bookmarks/delete)  ;;delete an existing bookmark  
    ))


(def routes
    (-> bookmarks-context
        (wrap-routes require-login)))
(ns clobs.routes.bookmarks
    (:require [compojure.core               :refer [context wrap-routes GET POST PUT DELETE]]
              [clobs.controllers.bookmarks  :as     bookmarks]
              [clobs.middleware.auth        :refer [require-login]]
              [clobs.middleware.request     :refer [user-owns require-params param-exists body-exists]]))

(def bookmarks-context
    (context "/bookmarks" []
        
        (GET "/"        []  bookmarks/get-all) ;;all user bookmarks

        (GET "/:id"     []  (-> bookmarks/get-one  ;;bookmark by id
                                (user-owns :params)
                                (param-exists :bookmark :id)))     

        (POST "/"       []  (-> bookmarks/insert  ;;Create a bookmark
                                (require-params :url :name :private)))  

        (PUT "/"        []  (-> bookmarks/update!  ;;update an existing bookmark
                                (user-owns :body)
                                (body-exists :bookmark :id)
                                (require-params :id :name :private)))  
          
        (DELETE "/:id"  []  (-> bookmarks/delete  ;;delete an existing bookmark 
                                (user-owns :params)
                                (param-exists :bookmark :id)))))   
    
(def routes
    (-> bookmarks-context
        (wrap-routes require-login)))
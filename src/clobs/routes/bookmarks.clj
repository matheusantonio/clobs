(ns clobs.routes.bookmarks
    (:require [compojure.core               :refer [context GET POST PUT DELETE]]
              [clobs.controllers.bookmarks  :as     bookmarks]
              [clobs.auth                   :refer [login-required]]))


(def routes
    (context "/bookmarks" []
        
        (GET "/"          request   (login-required request bookmarks/get-all)) ;;all user bookmarks
        (GET "/:id"       request   (login-required request bookmarks/get))     ;;bookmark by id

        (POST "/"         request   (login-required request bookmarks/insert))  ;;Create a bookmark
       
        (PUT "/"          request   (login-required request bookmarks/update))  ;;update an existing bookmark
          
        (DELETE "/:id"    request   (login-required request bookmarks/delete))  ;;delete an existing bookmark  
    ))
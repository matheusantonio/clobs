(ns clobs.data.database
    (:require [next.jdbc            :as jdbc]
              [next.jdbc.sql        :as sql]
              [next.jdbc.connection :as connection]))


(def db {:dbtype "mysql" :user "matheus" :password "matheus" :dbname "clobs"})
(def ds (jdbc/get-datasource db))
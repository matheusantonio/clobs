(ns clobs.data.database
    (:require [next.jdbc            :as jdbc]
              [next.jdbc.sql        :as sql]
              [next.jdbc.result-set :as rs]
              [next.jdbc.connection :as connection]))


(def db {:dbtype "mysql" :user "matheus" :password "matheus" :dbname "clobs"})
(def ds (jdbc/with-options (jdbc/get-datasource db) {:builder-fn rs/as-unqualified-lower-maps}))
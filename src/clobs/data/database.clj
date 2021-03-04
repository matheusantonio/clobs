(ns clobs.data.database
    (:require [next.jdbc            :as jdbc]
              [next.jdbc.sql        :as sql]
              [next.jdbc.result-set :as rs]))


(def db {:dbtype "mysql" :user "matheus" :password "matheus" :dbname "clobs" :host "clobs_bd" :useSSL "false"}) ;
(def ds (jdbc/with-options (jdbc/get-datasource db) {:builder-fn rs/as-unqualified-lower-maps}))

(defn find-by-key
    [t k v]
    (sql/find-by-keys ds t {k v}))

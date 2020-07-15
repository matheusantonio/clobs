(ns clobs.middleware.request
    (:require [clojure.core.match   :refer [match]]
              [clobs.responses      :refer [not-acceptable-status not-found-status]]
              [clobs.data.database  :refer [find-by-key]]
              [clojure.pprint       :refer [pprint]]))

; Parameters requirement middleware
(defn body-has-keys?
    [kl request]
    (match kl
        (_ :guard #(empty? %)) true
        :else (let [k (first kl)
                    v (get-in request [:body k])]
                (if (nil? v)
                    false
                    (body-has-keys? (rest kl) request)))))

(defn require-params [handler & params]
    (fn [request]
        (if (body-has-keys? params request)
            (handler request)
            (not-acceptable-status {:error "Some parameters are missing"}))))


; Existing data middleware
(defn get-from [src key]
    (fn [request]
        (get-in request [src key])))

(defn returns-value? [handler request table key]
    (fn [src]
        (let [v ((get-from src key) request)
              r (find-by-key table key v)]
            (if (empty? r)
                (not-found-status {:error "Key not found"})
                (handler request)))))

(defn param-exists [handler table key]
    (fn [request]
        ((returns-value? handler request table key) :params)))

(defn body-exists [handler table key]
    (fn [request]
        ((returns-value? handler request table key) :body)))

; recebo a chave por parametro
; recupero o valor da chave nos parametros/corpo
; verifico se existe no banco 
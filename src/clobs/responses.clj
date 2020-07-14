(ns clobs.responses)

(defn response [status body & {:as headers}]
    {:status status :body body :headers headers})

; 200
(def ok-status (partial response 200))
(def created-status (partial response 201))

; 400
(def unauthorized-status (partial response 401))
(def not-found-status (partial response 404))
(def not-acceptable-status (partial response 406))
(def conflict-status (partial response 409))

; 500
(def error-status (partial response 500))
        
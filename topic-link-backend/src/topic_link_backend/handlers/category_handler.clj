(ns topic-link-backend.handlers.category-handler
  (:require [ring.util.http-response :as response]
            [topic-link-backend.services.category-service :as category-service]
            [taoensso.timbre :as logger]))

(defn get-all [_]
  (logger/info "User requested for all categories")
  (let [result (category-service/get-all)]
    (if (:error result)
      (response/conflict result)
      (response/ok result))))

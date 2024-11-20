(ns topic-link-backend.handlers.new-handler
  (:require [ring.util.http-response :as response]))

(defn hello [request]
  (response/ok {:message "Hello backend!"}))

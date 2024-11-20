(ns topic-link-backend.handlers.new-handler
  (:require [ring.util.http-response :as response]))

(defn hello-protected [request]
  (response/ok {:message "Hello backend!"}))

(defn hello-not-protected [request]
  (response/ok {:message "Hello backend!"}))

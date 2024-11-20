(ns topic-link-backend.routes
  (:require [reitit.ring :as ring]
            [schema.core :as s]
            [reitit.swagger :as swagger]
            [reitit.swagger-ui :as swagger-ui]
            [reitit.ring.middleware.muuntaja :as rrmm]
            [reitit.coercion.schema :as rcs]
            [reitit.ring.coercion :as rrc]
            [ring.middleware.params :as params]
            [ring.middleware.cors :refer [wrap-cors]]
            [muuntaja.core :as muuntaja]
            [topic-link-backend.handlers.new-handler :as handler]))


(defn app []
  (ring/ring-handler
    (ring/router
      [
       ["/hello" {:get {:summary    "Ping server"
                   :handler    handler/hello
                   :responses  {200 {:body {:message s/Str}}}}}]
       ["" {:no-doc true}
        ["/swagger.json" {:get {:no-doc  true
                                :swagger
                                {:info {:title       "Topic Link API"
                                        :description "API documentation for Topic Link social network"
                                        :version     "0.1.0"}}
                                :handler (swagger/create-swagger-handler)}}]
        ["/api-docs/*" {:get (swagger-ui/create-swagger-ui-handler)}]]]
      {:data {:muuntaja muuntaja/instance
              :coercion rcs/coercion
              :middleware [params/wrap-params
                           rrmm/format-middleware
                           rrc/coerce-exceptions-middleware
                           rrc/coerce-response-middleware
                           rrc/coerce-request-middleware
                           [wrap-cors :access-control-allow-origin #".*"
                            :access-control-allow-methods [:get :put :post :patch :delete]]]}})
    (ring/create-default-handler
      {:not-found (constantly {:status 404 :body "Not found"})})))

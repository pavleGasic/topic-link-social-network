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
            [topic-link-backend.utils.auth :refer [wrap-jwt-auth auth-middleware]]
            [topic-link-backend.handlers.new-handler :as handler]
            [topic-link-backend.handlers.user-handler :as user-handler]
            [topic-link-backend.handlers.category-handler :as category-handler]
            [topic-link-backend.handlers.community-handler :as community-handler]))


(defn app []
  (ring/ring-handler
    (ring/router
      [
       ["/hello-protected" {:get {:summary    "Ping server auth"
                                  :middleware [wrap-jwt-auth auth-middleware]
                                  :swagger    {:security [{:token []}]}
                                  :handler    handler/hello-protected
                                  :responses  {200 {:body {:message s/Str}}}}}]
       ["/hello-not-protected" {:get {:summary   "Ping server not auth"
                                      :swagger   {:security [{:token []}]}
                                      :handler   handler/hello-not-protected
                                      :responses {200 {:body {:message s/Str}}}}}]
       ["/user/login" {:post {:summary    "User login to server"
                              :parameters {:body {:emailOrUsername s/Str
                                                  :password        s/Str}}
                              :handler    user-handler/login}}]
       ["/user/register" {:post {:summary    "User registration to server"
                                 :parameters {:body {:email      s/Str
                                                     :password   s/Str
                                                     :firstName  s/Str
                                                     :lastName   s/Str
                                                     :username   s/Str
                                                     :categories [s/Str]}}
                                 :handler    user-handler/register}}]
       ["/community/create" {:post {:summary    "Create community by user"
                                    :middleware [wrap-jwt-auth auth-middleware]
                                    :swagger    {:security [{:token []}]}
                                    :parameters {:body {:name        s/Str
                                                        :description s/Str
                                                        :rules       [s/Str]
                                                        }}
                                    :handler    community-handler/create-community}}]
       ["/category/get-all" {:get {:summary "Get all categories"
                                   :handler category-handler/get-all}}]
       ["/swagger.json" {:get {:no-doc  true
                               :handler (swagger/create-swagger-handler)}}]
       ["/api-docs/*" {:get {:no-doc true :handler (swagger-ui/create-swagger-ui-handler)}}]]
      {:data {:swagger
              {:securityDefinitions {:token {:type "apiKey"
                                             :name "Authorization"
                                             :in   "header"}}
               :info                {:title       "Topic Link API"
                                     :description "API documentation for Topic Link social network"
                                     :version     "0.1.0"}}
              :muuntaja   muuntaja/instance
              :coercion   rcs/coercion
              :middleware [params/wrap-params
                           rrmm/format-middleware
                           rrc/coerce-exceptions-middleware
                           rrc/coerce-response-middleware
                           rrc/coerce-request-middleware
                           [wrap-cors :access-control-allow-origin #".*"
                            :access-control-allow-methods [:get :put :post :patch :delete]]]}})
    (ring/create-default-handler
      {:not-found (constantly {:status 404 :body "Not found"})})))

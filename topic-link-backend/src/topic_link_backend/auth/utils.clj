(ns topic-link-backend.auth.utils
  (:require [buddy.sign.jwt :as jwt]
            [clojure.edn :as edn]
            [buddy.auth.backends :as backends]
            [buddy.auth :refer [authenticated?]]
            [buddy.auth.middleware :refer [wrap-authentication]]))

(def jwt-secret (:secret-key (:jwt (edn/read-string (slurp "resources/config.edn")))))
(def backend (backends/jws {:secret jwt-secret}))

(defn wrap-jwt-auth
  [handler]
  (wrap-authentication handler backend))

(defn create-token [payload]
  (jwt/sign payload jwt-secret))

(defn auth-middleware
  [handler]
  (fn [request]
    (if (authenticated? request)
      (handler request)
      {:status 401 :body {:error "Unauthorized"}})))

(ns topic-link-backend.core
  (:require [topic-link-backend.routes :as routes]
            [ring.adapter.jetty :as jetty]
            [taoensso.timbre :as logger]
            [topic-link-backend.logging :as logging]
            [topic-link-backend.data-access.db-connection :as db]))

(defn -main [& _]
  (logging/setup-logging)
  (logger/info "Server started on http://localhost:8080")
  (logger/info "Swagger documentation is placed on http://localhost:8080/api-docs/index.html")
  (db/connect-to-database)
  (logger/info "Connected to MongoDB")
  (jetty/run-jetty (routes/app) {:port 8080 :join? false?}))
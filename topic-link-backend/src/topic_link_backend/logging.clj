(ns topic-link-backend.logging
  (:require [taoensso.timbre :as logger]))

(defn setup-logging []
  "Setup function for server logging"
  (logger/merge-config!
    {:level :info})
  (logger/info "Logging initialized"))

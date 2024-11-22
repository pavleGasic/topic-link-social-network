(ns topic-link-backend.data-access.db-connection
  (:require [clojure.edn :as edn]
            [taoensso.timbre :as logger]
            [monger.core :as monger-core]
            [monger.credentials :as monger-credentials]))

(defonce db-connection (atom nil))

(defn load-config []
  (edn/read-string (slurp "resources/config.edn")))

(defn connect-to-database []
  (let [{:keys [host port db username password]} (:database (load-config))
        credentials (monger-credentials/create username db password)
        connection (monger-core/connect-with-credentials host port credentials)]
    (reset! db-connection {:connection connection
                           :db (monger-core/get-db connection db)})))

(defn get-db []
  (get @db-connection :db))

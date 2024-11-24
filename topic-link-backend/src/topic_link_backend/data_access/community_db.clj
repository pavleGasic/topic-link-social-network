(ns topic-link-backend.data-access.community-db
  (:require [monger.collection :as monger]
            [topic-link-backend.data-access.db-connection :refer [get-db]]
            [java-time.api :as jt]))


(defn insert-community [community]
  (monger/insert (get-db) "communities" (assoc community :created (jt/local-date-time))))

(defn community-exists? [community-name]
  (boolean (monger/find-one-as-map
             (get-db)
             "communities"
             {:name community-name})))

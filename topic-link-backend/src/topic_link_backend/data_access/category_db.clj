(ns topic-link-backend.data-access.category-db
  (:require [monger.collection :as monger]
            [topic-link-backend.data-access.db-connection :refer [get-db]])
  (:import org.bson.types.ObjectId))


(defn find-all []
  (map #(update % :_id str) (monger/find-maps (get-db) "categories")))

(defn find-one [id]
  (monger/find-one-as-map (get-db) "categories" {:_id (ObjectId. id)}))
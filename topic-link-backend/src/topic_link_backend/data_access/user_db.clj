(ns topic-link-backend.data-access.user-db
  (:require [monger.collection :as monger]
            [topic-link-backend.data-access.db-connection :refer [get-db]]))


(defn insert-user [user]
  (monger/insert (get-db) "users" user))

(defn user-exists? [email]
  (boolean (monger/find-one-as-map (get-db) "users" {:email email})))

(defn find-user-by-email [email]
  (let [user (monger/find-one-as-map (get-db) "users" {:email email})]
    (if user
      (dissoc user :_id)
      nil)))
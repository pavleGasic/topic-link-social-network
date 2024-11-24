(ns topic-link-backend.data-access.user-db
  (:require [monger.collection :as monger]
            [topic-link-backend.data-access.db-connection :refer [get-db]]))


(defn insert-user [user]
  (monger/insert (get-db) "users" user))

(defn user-exists? [email-or-username]
  (boolean (monger/find-one-as-map
             (get-db)
             "users"
             {"$or" [{:email email-or-username}
                     {:username email-or-username}]})))

(defn find-user-by-email-or-username [email-or-username]
  (update (monger/find-one-as-map
                              (get-db)
                              "users"
                              {"$or" [{:email email-or-username}
                                      {:username email-or-username}]}
                              ) :_id str))
(ns topic-link-backend.services.user-service
  (:require [topic-link-backend.utils.auth :refer [create-token verify-password? hash-password]]
            [taoensso.timbre :as logger]
            [topic-link-backend.data-access.user-db :as user-db]))


(defn register [user]
  (let [email (:email user)
        password (:password user)]
    (if (user-db/user-exists? email)
      ((logger/warn "Requested register with existing email")
       {:error "User with provided email already exist"})
      (let [hashed-password (hash-password password)
            user-with-hash (assoc user :password hashed-password)]
        (user-db/insert-user user-with-hash)
        (logger/info (str "Successfully inserted user in database with email/username: " (:emailOrUsername user-with-hash)))
        {:email email :token (create-token (dissoc (user-db/find-user-by-email-or-username email) :password :categories))}))))

(defn login [user]
  (let [email-or-username (:emailOrUsername user)
        password (:password user)
        existing-user (user-db/find-user-by-email-or-username email-or-username)]
    (if (and existing-user (verify-password? password (:password existing-user)))
      (let [token (create-token (dissoc existing-user :password :categories))]
        (logger/info (str "User " email-or-username " successfully logged in"))
        {:email email-or-username :token token})
      (do
        (logger/warn (str "Invalid login attempt for user with email/username " email-or-username))
        {:error "Invalid email or password"}))))
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
        (logger/info (str "Successfully inserted user in database with email: " (:email user-with-hash)))
        {:email email :token (create-token (dissoc user-with-hash :password))}))))

(defn login [user]
  (let [email (:email user)
        password (:password user)
        existing-user (user-db/find-user-by-email email)]
    (if (and existing-user (verify-password? password (:password existing-user)))
      (let [token (create-token (dissoc existing-user :password))]
        (logger/info (str "User " email " successfully logged in"))
        {:email email :token token})
      (do
        (logger/warn (str "Invalid login attempt for user with email " email))
        {:error "Invalid email or password"}))))
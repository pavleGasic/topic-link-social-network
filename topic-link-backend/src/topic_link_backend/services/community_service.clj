(ns topic-link-backend.services.community-service
  (:require [topic-link-backend.data-access.community-db :as community-db]
            [taoensso.timbre :as logger]
            [topic-link-backend.utils.auth :as auth]))


(defn create-community [community token]
  (let [community-name (:name community)]
    (if (community-db/community-exists? community-name)
      (do
        (logger/warn "Tried to create community with name that exist in communities collection")
        {:error "Community already exists"
         :type "conflict"})
      (do
        (let [token-claims (auth/unsign-token token)
              owner-id (:_id token-claims)
              email (:email token-claims)]
          (if (= owner-id nil)
            (do
              (logger/warn "Token invalid. There is no id claim in token!")
              {:error "Authentication error"
               :type "unauthorized"})
            (do
              (community-db/insert-community (assoc community :ownerId owner-id))
              (logger/info (str "Successfully created community by user email:" email))
              {:message "Community successfully created"})))))))
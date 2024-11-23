(ns topic-link-backend.services.category-service
  (:require [topic-link-backend.data-access.category-db :as category-db]
            [taoensso.timbre :as logger]))

(defn get-all []
  (let [categories (category-db/find-all)]
    (if (seq categories)
      (do
        (logger/info "Successfully retrieved all categories from database")
        categories)
      (do
        (logger/warn "There is no categories in database")
        {:error "User with provided email already exist"}))))
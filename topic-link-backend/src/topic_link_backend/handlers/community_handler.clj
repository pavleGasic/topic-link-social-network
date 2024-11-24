(ns topic-link-backend.handlers.community-handler
  (:require [ring.util.http-response :as response]
            [taoensso.timbre :as logger]
            [topic-link-backend.services.community-service :as community-service]
            [topic-link-backend.utils.auth :as auth]))

(defn create-community
  [{:keys [parameters headers]}]
  (let [community (:body parameters)
        token (auth/get-token-value (get headers "authorization"))
        result (community-service/create-community community token)]
    (logger/info (str "Requested to create new community with name: " (:name community)))
    (if (:error result)
      (if (= (:type result) "conflict")
        (response/conflict result)
        (response/unauthorized result))
      (response/ok result))))
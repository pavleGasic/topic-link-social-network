(ns topic-link-backend.handlers.user-handler
  (:require [ring.util.http-response :as response]
            [taoensso.timbre :as logger]
            [topic-link-backend.services.user-service :as user-service]))

(defn login
  [{:keys [parameters]}]
  (let [data (:body parameters)
        result (user-service/login data)]
    (logger/info (str "User with email " (:email data) " requested login"))
    (if (:error result)
      (response/unauthorized {:error (:error result)})
      (response/ok result))))

(defn register
  [{:keys [parameters]}]
  (let [data (:body parameters)
        result (user-service/register data)]
    (logger/info (str "User with email " (:email data) " requested register"))
    (if (:error result)
      (response/conflict {:error (:error result)})
      (response/ok result))))


;;get post feed for specific user
;;search for topics (and maybe topic type)
;;subscribe for some topic
;;post user preferences (for displaying feed)
;;post create topic and set topic type
;;post create post for specific topic (guest and auth mode, handling photos or maybe videos)
;;post create challenge attempt (one per user)
;;post create voting for choose challenge
;;post create voting for challenge winner
;;handling voting with web sockets!
;;enable guest mode but only post posts in different section
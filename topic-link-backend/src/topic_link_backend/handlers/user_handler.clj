(ns topic-link-backend.handlers.user-handler
  (:require [ring.util.http-response :as response]
            [topic-link-backend.auth.utils :refer [create-token]]
            [taoensso.timbre :as logger]))

(defn login
  [{:keys [parameters]}]
  (let [data (:body parameters)]
    (logger/info parameters)
    (response/ok {:user  data
                  :token (create-token data)})))
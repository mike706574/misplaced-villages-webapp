(ns cities.server.api.handler
  (:require [cities.server.api.routes :as api-routes]
            [ring.middleware.defaults :refer [wrap-defaults
                                              api-defaults]]
            [taoensso.timbre :as log]))

(defn wrap-logging
  [handler]
  (fn [{:keys [uri method] :as request}]
    (let [label (str method " \"" uri "\"")]
      (try
        (log/debug label)
        (let [{:keys [status] :as response} (handler request)]
          (log/debug (str label " -> " status))
          response)
        (catch Exception e
          (log/error e label)
          {:status 500})))))

(defn handler
  [deps]
  (-> (api-routes/routes deps)
      (wrap-defaults api-defaults)
      (wrap-logging)))

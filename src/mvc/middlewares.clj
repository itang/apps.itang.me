(ns mvc.middlewares
  (:use [mvc.scopes :only [*request*]]))

(defn wrap-request-map [handler]
  (fn [request]
    (binding [*request* request]
      (handler request))))
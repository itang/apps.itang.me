(ns www-itang-me.controllers.githubclient-controllers
  (:use [compojure.core]
        [clojure.tools.logging :only (info warn error)]
        [www-itang-me.utils])
  (:require [www-itang-me.libs.github-client :as github]))

(defn- view-json-page
  [call]
  (try
    (view-json
      (success-message "success" (reverse (sort-by #(:updated_at %) (call)))))
    (catch Exception e
      (warn (.getMessage e))
      (view-json
        (failture-message (.getMessage e) {})))))

(defn githubclient-routes
  "github client路由"
  []
  (context "/github" _
    (GET "/users/:user/repos/:page/:per-page" [user page per-page]
      (view-json-page #(github/user-repos user {:page page :per-page per-page})))
    (GET "/users/:user/watched/:page/:per-page" [user page per-page]
      (view-json-page #(github/user-watching user {:page page :per-page per-page})))))

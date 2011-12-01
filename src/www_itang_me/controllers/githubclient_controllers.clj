(ns www-itang-me.controllers.githubclient-controllers
  (:use [compojure.core]
        [clojure.tools.logging :only (info warn error)]
        [cheshire.core]
        [www-itang-me.utils])
  (:require [www-itang-me.libs.github-client :as github]))

(def github-api-v3-url "https://api.github.com")
(def DEFAULT-PAGE 1)
(def DEFAULT-PER-PAGE 10)

(defn- view-json-page
  [call]
  (try
    (view-json
      (success-message "success" (reverse (sort-by #(get % "pushed_at") (call)))))
    (catch Exception e
      (warn (.getMessage e))
      (view-json (failture-message (.getMessage e) {})))))

(defn githubclient-routes
  "github client路由"
  []
  (context "/github" _
    (GET "/users/:user/repos/:page/:per-page" [user page per-page]
      (view-json-page #(github/user-repos user {:page page :per-page per-page})))
    (GET "/users/:user/watched/:page/:per-page" [user page per-page]
      (view-json-page #(github/watching user {:page page :per-page per-page})))))

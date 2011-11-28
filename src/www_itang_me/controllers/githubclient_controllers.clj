(ns www-itang-me.controllers.githubclient-controllers
  (:use [compojure.core])
  (:use [cheshire.core])
  (:use [clojure.tools.logging :only (info warn error)])
  (:use appengine-magic.services.url-fetch)
  (:use www-itang-me.utils))

(def github-api-v3-url "https://api.github.com")
(def DEFAULT-PAGE 1)
(def DEFAULT-PER-PAGE 10)

(defn- as-url [url page per-page]
  (str url
    "?page=" (if-not page DEFAULT-PAGE page)
    "&per_page=" (if-not per-page DEFAULT-PER-PAGE per-page)))

(defn- http-get-string [url page per-page]
  (let [response (fetch (as-url url page per-page) :method :get )]
    (String. (:content response))))

(defn- view-json-page
  ([resource]
    (view-json-page resource DEFAULT-PAGE))
  ([resource page]
    (view-json-page resource page DEFAULT-PER-PAGE))
  ([resource page per-page]
    (try
      (let [url (str github-api-v3-url resource)
            repos-str (if-not page (http-get-string url) (http-get-string url page per-page))]
        (view-json
          (success-message "success" (reverse (sort-by #(get % "pushed_at") (parse-string repos-str))))))
      (catch Exception e
        (warn (.getMessage e))
        (view-json (failture-message (.getMessage e) {}))))))

(defn githubclient-routes
  "github client路由"
  []
  (context "/github" _
    (GET "/users/:user/repos/:page/:per-page" [user page per-page]
      (view-json-page (str "/users/" user "/repos") page per-page))

    (GET "/users/:user/watched/:page/:per-page" [user page per-page]
      (view-json-page (str "/users/" user "/watched") page per-page))))

(ns www-itang-me.controllers.githubclient-controllers
  (:use [compojure.core])
  (:use [cheshire.core])
  (:use appengine-magic.services.url-fetch)
  (:use www-itang-me.utils))

(def github-api-v3-url "https://api.github.com")

(defn- http-get-string
  ([url] (http-get-string url 0 500))
  ([url page per-page]
    (let [response (fetch (str url "?page=" page "&per_page=" per-page) :method :get )]
      (String. (:content response)))))

(defn- view-json-page [resource page per-page]
  (let [url (str github-api-v3-url resource)
        repos-str (if (nil? page) (http-get-string url) (http-get-string url page per-page))]
    (view-json (reverse (sort-by :pushed_at (parse-string repos-str))))))

(defn githubclient-routes
  "github client路由"
  []
  (context "/github" _
    (GET "/users/:user/repos" [user page per-page]
      (view-json-page (str "/users/" user "/repos") page per-page))

    (GET "/users/:user/watched" [user page per-page]
      (view-json-page (str "/users/" user "/watched") page per-page))))

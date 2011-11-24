(ns www-itang-me.controllers.githubclient_controllers
  (:use [compojure.core])
  (:use [cheshire.core])
  (:use appengine-magic.services.url-fetch)
  (:use www-itang-me.utils))

(def github_api_v3_url "https://api.github.com")

(defn- http_get_string
  ([url] (http_get_string url 0 500))
  ([url page per_page]
    (let [response (fetch (str url "?page=" page "&per_page=" per_page) :method :get )]
      (String. (:content response)))))

(defn- get_view_json_page [resource page per_page]
  (let [url (str github_api_v3_url resource)]
    (println (str "page:" page))
    (if (nil? page)
      ;; (view_json (sort-by #(vec (map % [:updated_at ])) (parse-string (http_get_string url))))
      (view_json (reverse (sort-by :pushed_at (parse-string (http_get_string url)))))
      (view_json (reverse (sort-by :pushed_at (parse-string (http_get_string url page per_page))))))))

(defn githubclient_controllers
  []
  (context "/github" _
    (GET "/users/:user/repos" [user page per_page]
      (get_view_json_page (str "/users/" user "/repos") page per_page))

    (GET "/users/:user/watched" [user page per_page]
      (get_view_json_page (str "/users/" user "/watched") page per_page))))

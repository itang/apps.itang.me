(ns www-itang-me.controllers.githubclient_controllers
  (:use [compojure.core])
  (:use [cheshire.core])
  (:use appengine-magic.services.url-fetch)
  (:use www-itang-me.utils)
  )

(defn githubclient_controllers
  []
  (POST "/github" _
    (do
      (let [response (fetch "http://github.com/api/v2/json/repos/show/itang" :method :get )]
        (println (String. (:content response)))
        (view_json (String. (:content response)))
        )
      )))

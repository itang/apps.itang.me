(ns www-itang-me.core
  (:use [compojure.core])
  (:require [compojure.route :as route])
  (:require [appengine-magic.core :as ae]))


(defroutes www-itang-me-app-handler
  ;; HTML upload form
  (GET "/" _
       {:status 200
        :headers {"Content-Type" "text/html"}
        :body (str "<html><body>"
                   "<h1>Hello,World</h1>"
                   (.format (java.text.SimpleDateFormat. "yyyy-MM-dd HH:mm:ss") (java.util.Date.))
                   "</body></html>")})
  (ANY "*" _
       {:status 200
        :headers {"Content-Type" "text/plain"}
        :body "not found"}))


(ae/def-appengine-app www-itang-me-app #'www-itang-me-app-handler)
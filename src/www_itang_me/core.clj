(ns www-itang-me.core
  (:use [compojure.core])
  (:use [hiccup core page-helpers])
  (:require [compojure.route :as route])
  (:require [appengine-magic.core :as ae]))


(defroutes www-itang-me-app-handler
  ;; index page
  (GET "/" _
    (let [now (.format (java.text.SimpleDateFormat. "yyyy-MM-dd HH:mm:ss") (java.util.Date.))]
      {:status 200
       :headers {"Content-Type" "text/html"}
       :body (html5
               [:head [:meta {:name "description" :content ""}]
                [:meta {:author "description" :content "itang - 唐古拉山"}]
                [:title "Hello World"]
                (include-css "/public/libs/bootstrap-1.4.0/bootstrap.min.css")
                (include-js "/public/libs/jquery-1.7.0/jquery.min.js")]
               [:body [:div {:class "container"}
                       [:h1 "Hello World"]
                       now]])}))

  (ANY "*" _
    {:status 200
     :headers {"Content-Type" "text/plain"}
     :body "not found"}))

(ae/def-appengine-app www-itang-me-app #'www-itang-me-app-handler)

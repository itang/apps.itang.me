(ns www-itang-me.core
  (:use [compojure.core])
  (:require [appengine-magic.core :as ae])
  (:use [www-itang-me.utils :only (todo-html)])
  (:use www-itang-me.controllers.main-controllers)
  (:use www-itang-me.controllers.bookmarker-controllers)
  (:use www-itang-me.controllers.githubclient-controllers))

(defroutes www-itang-me-app-handler
  (main-routes)

  (bookmarker-routes)

  (githubclient-routes)

  (GET "/blog" _
    (todo-html "Blog"))

  (GET "/projects" _
    (todo-html "Projects"))

  (GET "/apps" _
    (todo-html "Applications"))

  (GET "/about" _
    (todo-html "About me"))

  (ANY "*" _
    {:status 200
     :headers {"Content-Type" "text/plain"}
     :body "输错网址了吧你?!"}))

(ae/def-appengine-app www-itang-me-app #'www-itang-me-app-handler)

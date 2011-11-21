(ns www-itang-me.core
  (:use [compojure.core])
  (:require [appengine-magic.core :as ae])
  (:use www-itang-me.utils)
  (:use www-itang-me.controllers.main_controllers)
  (:use www-itang-me.controllers.bookmarker_controllers)
  )

(defroutes www-itang-me-app-handler
  (GET "/" _
    (main_controllers))

  (GET "/blog" _
    (todo_html "Blog"))

  (GET "/open_source" _
    (todo_html "Open Source"))

  (GET "/apps" _
    (todo_html "Applications"))

  (bookmarker_controllers)

  (GET "/about" _
    (todo_html "About me"))

  (ANY "*" _
    {:status 200
     :headers {"Content-Type" "text/plain"}
     :body "输错网址了吧你?!"}))

(ae/def-appengine-app www-itang-me-app #'www-itang-me-app-handler)

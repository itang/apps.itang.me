(ns www-itang-me.core
  (:use compojure.core)
  (:require [appengine-magic.core :as ae])
  (:use [mvc.controller-helpers])
  (:use [www-itang-me.controllers
         main-controllers
         bookmarker-controllers
         githubclient-controllers]))

(defroutes www-itang-me-app-handler
  (main-routes)

  (bookmarker-routes)

  (githubclient-routes)

  (GET "/blog" _
    (Todo "Blog"))

  (GET "/projects" _
    (Todo "Projects"))

  (GET "/apps" _
    (Todo "Applications"))

  (GET "/apps/todolist" _
    (Todo "Todolist"))

  (GET "/mobile" _
    (Todo "移动版"))

  (GET "/about" _
    (Todo "About me"))

  (ANY "*" _
    (Ok "输错网址了吧你?!")))

(ae/def-appengine-app www-itang-me-app #'www-itang-me-app-handler)

(ns www-itang-me.core
  (:use [compojure.core :only [defroutes GET POST ANY]]
        [ring.middleware.params :only [wrap-params]])
  (:require [appengine-magic.core :as ae])
  (:use [mvc.controller-helpers]
        [mvc.scopes :only [*request*]])
  (:use [www-itang-me.controllers
         main-controllers
         project-controllers
         bookmarker-controllers
         githubclient-controllers
         admin-controllers
         proxy-controllers
         setting-controllers]))

(defroutes www-itang-me-app-handler
  (main-routes)

  (project-routes)

  (bookmarker-routes)

  (githubclient-routes)

  (admin-routes)

  (proxy-routes)

  (setting-routes)

  (GET "/blog" _
    (Todo "Blog"))

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

(defn dev-mode? []
  (= "dev" (System/getenv "SERVER_SOFTWARE")))

(defn wrap-request-map [handler]
  (fn [request]
    (binding [*request* request]
      (handler request))))

;(defn- wrap-route-updating [handler]
;  (if (dev-mode?)
;    (wrap-reload-modified handler ["src"])
;    handler))

(def wrapped-handler (-> www-itang-me-app-handler
                         (wrap-params)
                         (wrap-request-map)))


(ae/def-appengine-app www-itang-me-app #'wrapped-handler)

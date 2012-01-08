(ns www-itang-me.controllers.project-controllers
  (:use [compojure.core :only [context GET POST]])
  (:require [www-itang-me.models.projects :as Project]
            [www-itang-me.views.project-views :as views])
  (:use [mvc.controller-helpers :only (Json)]
        [www-itang-me.utils :only (empty-else)]))

(defn project-routes
  []
  (context "/projects" _
    (GET "/" _
      (views/index (Project/projects)))

    (POST "/ding/:name" [name]
      (let [project (Project/ding name)]
        (if project
          (Json {:success true :message "操作成功!" :data project})
          (Json {:success false :message "操作失败!"}))))

    (POST "/sync" _
      (do
        (let [projects (Project/sync-watching-projects-from-github "itang")]
          (Json {:success true :message "同步成功!" :data projects}))))))
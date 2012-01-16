(ns www-itang-me.controllers.project-controllers
  (:use [compojure.core :only [context GET POST DELETE]]
        [cljtang.lang :only (empty-else)])
  (:require [www-itang-me.models.projects :as Project]
            [www-itang-me.views.project-views :as views])
  (:use [mvc.controller-helpers :only (Json)]))

(defn project-routes
  []
  (context "/projects" _
    (GET "/" _
      (views/index
        (Project/projects)
        (Project/project-sync-logs)))

    (DELETE "/:name" [name]
      (let [p (Project/remove-project name)]
        (Json {:success true :message "移除项目成功!" :data p})))

    (POST "/ding/:name" [name]
      (let [project (Project/ding name)]
        (if project
          (Json {:success true :message "操作成功!" :data project})
          (Json {:success false :message "操作失败!"}))))

    (POST "/sync" _
      (let [projects (Project/sync-watching-projects-from-github "itang")]
        (do
          (Project/add-sync-log)
          (Json {:success true :message "同步成功!" :data projects}))))))
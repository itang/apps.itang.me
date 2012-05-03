(ns www-itang-me.controllers.project-controllers
  (:use [compojure.core :only [context GET POST DELETE]]
        [cljtang.lang :only (empty-else)])
  (:require [www-itang-me.auth :as auth]
            [www-itang-me.models.projects :as Project])
  (:use [mvc.controller-helpers :only (Json Html)])
  (:use [www-itang-me.views.layout :only (main-layout)]
        [www-itang-me.views.projects :only (project-index)]))

(defn project-routes
  []
  (context "/projects" _
    (GET "/" _
      (Html (main-layout
              (project-index {:logs (Project/project-sync-logs)
                              :projects (Project/projects)}))))

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
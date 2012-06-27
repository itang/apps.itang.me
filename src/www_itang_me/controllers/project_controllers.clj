(ns www-itang-me.controllers.project-controllers
  (:use [compojure.core :only [context GET POST DELETE]]
        [cljtang.lang :only (empty-else)])
  (:require [libs.github-client :as github]
            [www-itang-me.auth :as auth]
            [www-itang-me.models.projects :as Project])
  (:use [mvc.controller-helpers :only (Json Html)])
  (:require [www-itang-me.views.layouts :as layouts]
            [www-itang-me.views.projects :as views-projects]))

(defn project-routes
  []
  (context "/projects" _
    (GET "/" _
      (Html (layouts/main
              "项目 - 爱唐"
              "projects"
              (views-projects/index {:logs (Project/project-sync-logs)
                                     :attention-projects (Project/projects)
                                     :my-projects (github/user-repos "itang" {:page 1 :per-page 100})}))))

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
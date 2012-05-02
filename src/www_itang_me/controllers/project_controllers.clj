(ns www-itang-me.controllers.project-controllers
  (:use [compojure.core :only [context GET POST DELETE]]
        [cljtang.lang :only (empty-else)])
  (:require [www-itang-me.models.projects :as Project]
            [www-itang-me.views.project-views :as views]
            [www-itang-me.auth :as auth])
  (:use [mvc.controller-helpers :only (Json Html)])
  (:require [net.cgrand.enlive-html :as html]))

(def max-show-logs-num 6)
(def max-half-show-logs-num (/ max-show-logs-num 2))

(defn- log-as [logs]
  (for [log logs]
     (:sync_at log)))

(defn- show-logs [sync-logs]
  (if (<= (count sync-logs) max-show-logs-num)
    (log-as sync-logs)
    (concat
      (log-as (take max-half-show-logs-num sync-logs))
      ["..."]
      (log-as (take-last max-half-show-logs-num sync-logs)))))

(html/deftemplate projects-index "views/projects/index.html"
  [ctx]
  [:#btnSync ] #(when (auth/is-me?) %)
  [:li.log-item ]
  (html/clone-for [log (show-logs (:logs ctx))] (html/content log))
  [:#projects :tbody :tr ]
  (html/clone-for [[index project] (map #(vector %1 %2) (iterate inc 1) (:projects ctx))]
    [:td.no ] (html/content (str index))
    [:td.name :a ] (html/do->
                     (html/content (str (:name project)))
                     (html/set-attr :href (:website project))
                     (html/set-attr :title (str (:description project) "\n" (:updated_at project))))
    [:td.language ] (html/content (:language project))
    [:td.description :small] (html/content (:description project))
    [:td.updated_at ] (html/content (-> (:updated_at project) (.replaceAll "T|Z" " ") .trim))
    [:td.attention ] (html/content (str (:attention project)))
    [:td.actions :button ] (html/set-attr :data (:name project))))

(defn project-routes
  []
  (context "/projects" _
    (GET "/" _
      (Html (projects-index {:logs (Project/project-sync-logs)
                             :projects (Project/projects)})))
    ;;(views/index
    ;; (Project/projects)
    ;;(Project/project-sync-logs)))

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
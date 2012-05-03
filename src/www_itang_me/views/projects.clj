(ns www-itang-me.views.projects
  (:require [net.cgrand.enlive-html :as html]
            [www-itang-me.models.projects :as Project]
            [www-itang-me.auth :as auth]))

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

(html/defsnippet project-index "views/projects/index.html" [:div#body-content ]
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
    [:td.description :small ] (html/content (:description project))
    [:td.updated_at ] (html/content (-> (:updated_at project) (.replaceAll "T|Z" " ") .trim))
    [:td.attention ] (html/content (str (:attention project)))
    [:td.actions :button ] (html/set-attr :data (:name project))))

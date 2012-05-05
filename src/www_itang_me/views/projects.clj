(ns www-itang-me.views.projects
  (:use [net.cgrand.enlive-html])
  (:require [www-itang-me.models.projects :as Project]
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

(defsnippet index "views/projects/index.html" [:div#body-content ]
  [ctx]
  [:#btnSync ]
  #(when (auth/is-me?) %)

  [:li.log-item ]
  (clone-for [log (show-logs (:logs ctx))] (content log))

  [:#projects :tbody :tr ]
  (clone-for [[index project] (map #(vector %1 %2) (iterate inc 1) (:projects ctx))]
    [:td.no ] (content (str index))
    [:td.name :a ] (do->
                     (content (str (:name project)))
                     (set-attr :href (:website project))
                     (set-attr :title (str (:description project) "\n" (:updated_at project))))
    [:td.language ] (content (:language project))
    [:td.description :small ] (content (:description project))
    [:td.updated_at ] (content (-> (:updated_at project) (.replaceAll "T|Z" " ") .trim))
    [:td.attention ] (content (str (:attention project)))
    [:td.actions :button ] (set-attr :data (:name project))))

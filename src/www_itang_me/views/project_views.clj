(ns www-itang-me.views.project-views
  (:use [hiccup core page])
  (:require [www-itang-me.auth :as auth])
  (:use mvc.view-helpers
        www-itang-me.utils
        www-itang-me.views.layouts))

(def max-show-logs-num 6)
(def max-half-show-logs-num (/ max-show-logs-num 2))

(defn- log-as-li [logs]
  (for [log logs]
    [:li (:sync_at log)]))

(defn- show-logs [sync-logs]
  (if (<= (count sync-logs) max-show-logs-num)
    (log-as-li sync-logs)
    (concat (log-as-li (take max-half-show-logs-num sync-logs))
      [[:li "..."]]
      (log-as-li (take-last max-half-show-logs-num sync-logs)))))

(defn index
  [projects project-sync-logs]
  (default-layout
    (list
      (include-lib-min-js "jquery.tablesorter" "2.0.5")
      (include-app-js "projects")
      [:div.sidebar {}
       [:div.well {}
        [:h4 "同步日志"]
        [:ul {}
         (show-logs project-sync-logs)]]]
      [:div.content {}
       (when (auth/is-me?)
         [:button#btnSync {:class "btn primary"} "同步"])
       [:ul.tabs {}
        [:li.active [:a {:href "#by_updated"} "关注的github项目"]]
        [:li [:a {:href "#by_group"} "按语言分组浏览(todo)"]]
        [:li [:a {:href "#by_tag"} "按标签浏览(todo)"]]]
       [:table#projects.zebra-striped {}
        [:thead [:tr {}
                 [:th {:width "40px"} "#"]
                 [:th {:width "100px"} "名称"]
                 [:th {:width "80px"} "语言"]
                 [:th {:width "360px"} "描述"]
                 [:th "更新时间"]
                 [:th "关注度"]
                 [:th "*"]]]
        [:tbody ;; TODO 使用库函数
         (for [[index project] (map #(vector %1 %2) (iterate inc 1) projects)]
           [:tr {}
            [:td index]
            [:td [:a {:href (:website project)
                      :title (str (:description project) "\n" (:updated_at project))
                      :target "_blank"}
                  [:b (:name project)]]]
            [:td [:small (:language project)]]
            [:td [:small (:description project)]]
            [:td (-> (:updated_at project) (.replaceAll "T|Z" " ") .trim)]
            [:td (:attention project)]
            [:td [:button {:class "btn primary ding" :data (:name project)} "ding!"]]])]]])))
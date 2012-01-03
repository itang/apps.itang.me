(ns www-itang-me.views.project-views
  (:use [hiccup core page-helpers])
  (:use mvc.view-helpers
        www-itang-me.utils
        www-itang-me.views.layouts))

(defn index
  [projects]
  (default-layout
    (list
     (include-lib-min-js "jquery.tablesorter" "2.0.5")
     (include-app-js "projects")
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
          [:td  index]
          [:td [:a {:href (:website project)
                    :title (str (:description project) "\n" (:updated_at project))
                    :target "_blank"}
                [:b (:name project)]]]
          [:td [:small (:language project)]]
          [:td [:small (:description project)]]
          [:td (-> (:updated_at project) (.replaceAll "T|Z" " ") .trim)]
          [:td (:attention project)]
          [:td [:button  {:class "btn primary ding" :data (:name project)} "ding!"]]])]])))
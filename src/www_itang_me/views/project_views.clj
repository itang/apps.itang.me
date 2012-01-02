(ns www-itang-me.views.project-views
  (:use [hiccup core page-helpers])
  (:use mvc.view-helpers
        www-itang-me.utils
        www-itang-me.views.layouts))

(defn index
  [projects]
  (default-layout
    (list [:ul {}
           ;; TODO 使用库函数
           (for [[index project] (map #(vector %1 %2) (iterate inc 1) projects)]
             [:li {}
              (str index "&nbsp;")
              [:a {:href (:html_url project)
                   :title (str (:description project) "\n" (:updated_at project))
                   :target "_blank"}
               (:name project)]
              "&nbsp;"
              [:small (:language project)]])])))
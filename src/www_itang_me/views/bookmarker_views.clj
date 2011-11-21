(ns www-itang-me.views.bookmarker_views
  (:use [hiccup core page-helpers])
  (:use www-itang-me.utils)
  (:use www-itang-me.views.layouts)
  )

(defn index [bookmarkers home_site]
  (default_layout
    (list
      (include-js "/public/app/scripts/bookmarkers.js")
      [:div.sidebar {}
       [:div.well {}
        (str "共收藏<b>" (count bookmarkers) "</b>个站点")
        [:input#refresh {:type "button" :value "刷新"}]
        [:u {}
         (for [site bookmarkers]
           [:li [:a.sitelink {:href (:url site) :id (:id site) :target "_blank" :title (:tags site)} (:title site)]])]]]
      [:div.content {}
       [:div#frame_container {}
        [:iframe#frameWindow.frame {:src home_site :width "100%" :height "100%"}]]
       (get_footer)])))



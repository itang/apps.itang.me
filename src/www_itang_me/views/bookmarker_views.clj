(ns www-itang-me.views.bookmarker-views
  (:use [hiccup core page-helpers]
    [mvc.view-helpers :only (include-app-js)]
    [www-itang-me.views.layouts :only (default-layout get-footer)]))

(defn index
  [bookmarkers home-site]
  (default-layout
    (list
      (include-app-js "bookmarkers")
      [:div.sidebar {}
       [:div.well {}
        (str "共收藏<b>" (count bookmarkers) "</b>个站点")
        [:input#refresh {:type "button" :value "刷新" :class "btn"}]
        [:u {}
         (for [site bookmarkers]
           [:li {}
            [:a.sitelink
             {:href (:url site) :id (:id site) :target "_blank" :title (:tags site) :data-it (:name site)}
             (:title site)]
            [:small {}
             "&nbsp;("
             [:span {:id (str (:name site) "hits")} (:hits site)]
             ")"]])]]] ;;end div.sidebar
      [:div.content {}
       [:div#frame_container {}
        [:iframe#frameWindow.frame {:src home-site :width "100%" :height "100%"}]]
       (get-footer)])
    :title "书签"))



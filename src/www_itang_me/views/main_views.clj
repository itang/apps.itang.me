(ns www-itang-me.views.main-views
  (:use [hiccup core page-helpers])
  (:use [www-itang-me.utils :only (now)])
  (:use www-itang-me.views.layouts))

(defn index
  [hot-bookmarkers app]
  (default-layout
    (list
      [:div.sidebar {}
       [:div.well {}
        [:h5 "Menu"]
        [:u {}
         [:li [:a {:href "/apps/bookmarkers"} "Bookmarkers"]]
         [:li [:a {:href "/projects"} "My projects"]]
         [:li [:a {:href "/about"} "About"]]
         ]]]
      [:div.content {}
       [:div.hero-unit {}
        [:h2 "TIMELINE"]]
       [:div.row {}
        [:div.span4 {}
         [:h3 "常用书签"]
         [:ul {}
          (for [site hot-bookmarkers]
            [:li [:a {:href (:url site) :target "_blank" :title (:tags site)} (:title site)]])]]
        ;;github widget
        (include-js "/public/app/scripts/github-client.js")
        [:div.span4 {}
         [:h3 "我的github项目"]
         [:div [:ul#myrepos ]]]]

       [:div.row {}
        [:div.span4 {}
         [:h3 "最新唠叨"]
         [:div (now)]]
        [:div.span4 {}
         [:h3 "关注的github项目"]
         [:div [:ul#mywatched ]]]]
       (get-footer app)])))



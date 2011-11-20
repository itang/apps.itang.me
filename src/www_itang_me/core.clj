(ns www-itang-me.core
  (:use [compojure.core])
  (:require [compojure.route :as route])
  (:use [hiccup core page-helpers])
  (:require [appengine-magic.core :as ae])
  (:use www-itang-me.models.bookmarkers)
  (:use www-itang-me.utils)
  (:use www-itang-me.layouts))

(defroutes www-itang-me-app-handler
  (GET "/" _
    (default_layout
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
          [:div.span6 {}
           [:h3 "常用书签"]
           [:ul {}
            (for [site (hot_bookmarkers)]
              [:li [:a {:href (:url site) :target "_blank"} (:name site)]])]]
          [:div.span6 {}
           [:h3 "关注的github项目"]
           [:div (now)]]]
         [:div.row {}
          [:div.span6 {}
           [:h3 "最新唠叨"]
           [:div (now)]]]
         (get_footer)])))

  (GET "/blog" _
    (todo_html "Blog"))

  (GET "/open_source" _
    (todo_html "Open Source"))

  (GET "/apps" _
    (todo_html "Applications"))

  (GET "/apps/bookmarkers" _
    (default_layout
      (list
        (include-js "/public/app/scripts/bookmarkers.js")
        [:div.sidebar {}
         [:div.well {}
          (str "共收藏<b>" (count (all_bookmarkers)) "</b>个站点")
          [:input#refresh {:type "button" :value "刷新"}]
          [:u {}
           (for [site (all_bookmarkers)]
             [:li [:a.sitelink {:href (:url site) :id (:id site) :target "_blank"} (:name site)]])]]]
        [:div.content {}
         [:div#frame_container {}
          [:iframe#frameWindow.frame {:src (get_default_show_site) :width "100%" :height "100%"}]]
         (get_footer)])))

  (GET "/about" _
    (todo_html "About me"))

  (ANY "*" _
    {:status 200
     :headers {"Content-Type" "text/plain"}
     :body "输错网址了吧你?!"}))

(ae/def-appengine-app www-itang-me-app #'www-itang-me-app-handler)

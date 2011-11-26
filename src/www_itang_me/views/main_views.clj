(ns www-itang-me.views.main-views
  (:use [hiccup core page-helpers])
  (:use [www-itang-me.utils :only (now javascript-tag-ext)])
  (:use www-itang-me.views.layouts))

(defn index
  [hot-bookmarkers app]
  (default-layout
    (list
      [:div.sidebar {}
       [:div.well {}
        [:h4 "\"做一个出色的码匠\""]
        [:u {}
         [:li [:a {:href "/apps/bookmarkers"} "Bookmarkers"]]
         [:li [:a {:href "/projects"} "My projects"]]
         [:li [:a {:href "/about"} "About"]]
         ]]]
      [:div.content {}
       [:div.hero-unit {}
        [:h2 "TIMELINE"]]

       [:section#mytools_section {}
        [:div.page-header {}
         [:h1 "我的工具" "&nbsp;" [:small "方便自己使用的一些工具集"]]]
        [:div.row {}
         [:div.span5 {}
          [:h3 "常用书签"]
          [:ul {}
           (for [site hot-bookmarkers]
             [:li [:a {:href (:url site) :target "_blank" :title (:tags site)} (:title site)]])]
          [:a {:href "/apps/bookmarkers" :class "btn pull-center"} "更多"]]
         [:div.span5 {}
          [:h3 "最新唠叨"]
          [:div {}
           [:p (now)]
           [:a {:href "#" :class "btn pull-center"} "更多"]]]]]

       [:section#github_section {}
        [:div.page-header {}
         [:h1 "我的项目" "&nbsp;" [:small "参与或关注的项目"]]]

        [:div.row {}
         ;;github widget
         (include-js "/public/app/scripts/github-client.js")
         [:div.span5 {}
          [:h3 "关注的github项目"]
          [:div {}
           [:ul#mywatched {}
            (javascript-tag-ext {:id "repos-template" :type "text/x-handlebars-template"}
              [:li {}
               "{{index}} &nbsp;"
               [:a {:href "{{html_url}}" :title "{{description}}\n{{pushed_at}}" :target "_blank"}
                "{{name}}"]
               [:small "({{language}})"]])]
           [:a {:href "/projects" :class "btn pull-center" :target "_blank"} "更多"]]]
         [:div.span5 {}
          [:h3 "我的github项目"]
          [:div [:ul#myrepos ]]]
         ]
        ] ;;end section@github
       (get-footer app)])))



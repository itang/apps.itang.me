(ns www-itang-me.views.main-views
  (:use [hiccup core page])
  (:use mvc.view-helpers
        www-itang-me.utils
        www-itang-me.views.layouts))

(defn index
  [hot-bookmarkers app]
  (default-layout
    (list
      [:div.span3 {}
       [:div {:class "well sidebar-nav"}
        [:div.nav-header "\"做一个出色的码匠\""]
        [:div.pull-right "------ itang"]
        [:hr ]
        [:div.nav-header "我的网络"]
        [:u {:class "nav nav-list"}
         [:li [:a {:href "https://twitter.com/#!/livetang" :target "_blank"} "livetang@twitter"]]
         [:li [:a {:href "http://www.douban.com/people/itang/" :target "_blank"} "itang@douban"]]
         [:li [:a {:href "http://itang.iteye.com" :target "_blank"} "itang@iteye"]]]]]
      [:div.span9 {}
       [:div.hero-unit {}
        [:h2 "TIMELINE"]]

       [:section#mytools_section {}
        [:div.page-header {}
         [:h1 "应用" "&nbsp;" [:small "方便个人的一些应用集"]]]
        [:div.row-fluid {}
         [:div.span5 {}
          [:h3 "常用书签"]
          [:ul {}
           (for [site hot-bookmarkers]
             [:li [:a {:href (:url site) :target "_blank" :title (:tags site)} (:title site)]])]
          [:a {:href "/apps/bookmarkers" :class "btn pull-center"} "更多"]]
         [:div.span4 {}
          [:h3 "最新虾说"]
          [:div {}
           [:p (now)]
           [:a {:href "#" :class "btn pull-center"} "更多"]]]]]

       [:section#github_section {}
        [:div.page-header {}
         [:h1 "项目" "&nbsp;" [:small "参与或关注的项目"]]]
        [:div.row-fluid {}
         ;;github widget
         (include-app-js "github-client")
         ;;(include-app-roy "github-client")
         ;;(include-app-js "roy-loader")
         [:div.span5 {}
          [:h3 "关注的项目"]
          [:div {}
           [:ul#mywatched {}
            (javascript-tag-ext {:id "repos-template" :type "text/x-handlebars-template"}
              [:li {}
               "{{index}}&nbsp;"
               [:a {:href "{{html_url}}{{website}}" :title "{{description}}\n{{updated_at}}" :target "_blank"}
                "{{name}}"]
               [:small "({{language}})"]])]
           [:a {:href "/projects" :class "btn pull-center"} "更多"]]]
         [:div.span4 {}
          [:h3 "我的项目"]
          [:div [:ul#myrepos ]]]]] ;;end section@github
       (get-footer app)])))



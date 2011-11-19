(ns www-itang-me.core
  (:use [compojure.core])
  (:require [compojure.route :as route])
  (:use [hiccup core page-helpers])
  (:require [appengine-magic.core :as ae])
  (:use [appengine-magic.services.user :only (user-logged-in? current-user get-nickname get-email login-url logout-url)])
  (:use www-itang-me.bookmarkers)
  (:use www-itang-me.utils))

;;(:require [appengine-magic.services.user :as user]))
(defroutes www-itang-me-app-handler
  ;; index page
  (GET "/" _
    {:status 200
     :headers {"Content-Type" "text/html"}
     :body (html5
             [:head [:meta {:name "description" :content ""}]
              [:meta {:author "description" :content "itang - 唐古拉山"}]
              [:title "唐古拉山网"]
              (include-css "/public/libs/bootstrap-1.4.0/bootstrap.min.css")
              (include-css "/public/app/styles/main.css")
              [:link {:rel "shortcut icon" :href "/public/app/images/favicon.ico"}]
              (include-js "/public/libs/jquery-1.7.0/jquery.min.js")]
             [:body {}
              [:div.topbar {}
               [:div.topbar-inner {}
                [:div.container-fluid [:a.brand {:href "#"} "唐古拉山网"]
                 [:ul.nav {}
                  [:li.active [:a {:href "#"} "Home"]]
                  [:li [:a {:href "/blog"} "Blog"]]
                  [:li [:a {:href "/open_source"} "Open Source"]]
                  [:li [:a {:href "/apps"} "Apps"]]
                  [:li [:a {:href "/about"} "About"]]]
                 [:p.pull-right {}
                  (if (user-logged-in?)
                    (let [nickname (get-nickname (current-user)) email (get-email (current-user))]
                      (list [:a {:href (str "/users/" nickname)} email] [:a {:href (logout-url)} "退出"]))
                    [:a {:href (login-url)} "登录啊!"])]]]]

              [:div.container-fluid {}
               [:div.sidebar {}
                [:div.well {}
                 [:h5 "Menu"]
                 [:u {}
                  [:li [:a {:href "/bookmarkers"} "Bookmarkers"]]
                  [:li [:a {:href "/projects"} "My projects"]]
                  [:li [:a {:href "/about"} "About"]]
                  ]]]

               [:div.content {}
                [:div.hero-unit {}
                 [:h2 "TODO LIST"]]

                [:div.row {}
                 [:div.span6 {}
                  [:h3 "常用书签"]
                  [:ul {}
                   (for [site (bookmarker_list)]
                     [:li [:a {:href (:href site) :target "_blank"} (:name site)]])]]
                 [:div.span6 {}
                  [:h3 "关注的github项目"]
                  [:div (now)]]]
                [:div.row {}
                 [:div.span6 {}
                  [:h3 "最新唠叨"]
                  [:div (now)]]]

                [:footer {}
                 [:p "&copy; www.itang.me 2011"]
                 ]]]])})
  (GET "/blog" _
    (todo_html "Blog"))

  (GET "/open_source" _
    (todo_html "Open Source"))

  (GET "/apps" _
    (todo_html "Applications"))

  (GET "/about" _
    (todo_html "About me"))

  (ANY "*" _
    {:status 200
     :headers {"Content-Type" "text/plain"}
     :body "输错网址了吧你?!"}))

(ae/def-appengine-app www-itang-me-app #'www-itang-me-app-handler)

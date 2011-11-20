(ns www-itang-me.layouts
  (:use [hiccup core page-helpers])
  (:use [appengine-magic.services.user :only (user-logged-in? current-user get-nickname get-email login-url logout-url)])
  (:use www-itang-me.utils))

(defn get_topbar []
  [:div.topbar {}
   [:div.topbar-inner {}
    [:div.container-fluid [:a.brand {:href "#"} "唐古拉山网"]
     [:ul.nav {}
      [:li.active [:a {:href "/"} "Home"]]
      [:li [:a {:href "/blog"} "Blog"]]
      [:li [:a {:href "/open_source"} "Open Source"]]
      [:li [:a {:href "/apps"} "Apps"]]
      [:li [:a {:href "/about"} "About"]]]
     [:p.pull-right {}
      (if (user-logged-in?)
        (let [nickname (get-nickname (current-user)) email (get-email (current-user))]
          (list [:a {:href (str "/users/" nickname)} email] [:a {:href (logout-url)} "退出"]))
        [:a {:href (login-url)} "登录啊!"])]]]])

(defn get_footer []
  [:footer {}
   [:p "&copy; www.itang.me 2011"]])

(defn default_layout
  "默认布局"
  [content]
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
            (get_topbar)
            [:div.container-fluid {}
             content
             ]])})


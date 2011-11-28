(ns www-itang-me.views.layouts
  (:use [hiccup core page-helpers])
  (:use [appengine-magic.services.user :only (user-logged-in? current-user get-nickname get-email login-url logout-url)])
  (:use [www-itang-me.models.app :only (get-app)]))

(defn get-topbar []
  [:div.topbar {}
   [:div.topbar-inner {}
    [:div.container-fluid [:a.brand {:href "#"} "唐古拉山网"]
     [:ul.nav {}
      [:li.active [:a {:href "/"} "Home"]]
      [:li [:a {:href "/blog"} "Blog"]]
      [:li [:a {:href "/projects"} "Projects"]]
      [:li.dropdown {:data-dropdown "dropdown"}
       [:a.dropdown-toggle {:href "#"} "Apps"]
       [:ul.dropdown-menu {}
        [:li [:a {:href "/apps/bookmarkers"} "Bookmarkers"]]
        [:li [:a {:href "/apps/todolist"} "Todolist"]]
        [:li.divider ]
        ]]
      [:li [:a {:href "/about"} "About"]]]
     [:p.pull-right {}
      (if (user-logged-in?)
        (let [nickname (get-nickname (current-user)) email (get-email (current-user))]
          (list [:a {:href (str "/users/" nickname)} email] [:a {:href (logout-url)} "退出"]))
        [:a {:href (login-url)} "登录啊!"])]]]])

(defn get-footer
  ([] (get-footer (get-app)))
  ([app]
    [:footer {}
     [:p (str "&copy; www.itang.me 2011 | " (:version app))]]))

(defn- rule-path [prefix-path name version ext]
  (str prefix-path "/" name "-" version "/" name ext))

(defn- include-lib-js
  ([name version]
    (include-lib-js name version ".js"))
  ([name version ext]
    (include-js (rule-path "/public/libs" name version ext))))

(defn- include-lib-min-js [name version]
  (include-lib-js name version ".min.js"))

(defn- include-lib-css
  ([name version]
    (include-lib-css name version ".css"))
  ([name version ext]
    (include-css (rule-path "/public/libs" name version ext))))

(defn- include-lib-min-css [name version]
  (include-lib-css name version ".min.css"))

(defn- include-app-css
  ([name]
    (include-app-css name ".css"))
  ([name ext]
    (include-css (str "/public/app/styles/" name ext))))

(defn- include-app-min-css [name]
  (include-app-css name ".min.css"))


(defn default-layout
  "默认布局"
  [content]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (html5
           [:head [:meta {:name "description" :content ""}]
            [:meta {:author "description" :content "itang - 唐古拉山"}]
            [:title "唐古拉山网"]
            (include-lib-min-css "bootstrap" "1.4.0")
            (include-app-css "main")
            [:link {:rel "shortcut icon" :href "/public/app/images/favicon.ico"}]
            (include-lib-min-js "jquery" "1.7.0")
            (include-lib-min-js "underscore" "1.2.2")
            (include-lib-js "handlebars" "1.0.0.beta.4")
            (include-lib-js "bootstrap" "1.4.0" "-dropdown.js")]
           [:body {}
            (get-topbar)
            [:div.container-fluid {}
             content]])})


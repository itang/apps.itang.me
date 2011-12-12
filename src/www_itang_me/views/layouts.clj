(ns www-itang-me.views.layouts
  (:use [hiccup core page-helpers]
    [appengine-magic.services.user
     :only (user-logged-in? current-user get-nickname get-email login-url logout-url)])
  (:require [www-itang-me.auth :as auth]
    [www-itang-me.config :as config])
  (:use mvc.view-helpers
    [www-itang-me.utils :only (getstring)]
    [www-itang-me.models.app :only (get-app)]))

(defn get-topbar []
  [:div.topbar {}
   [:div.topbar-inner {}
    [:div.container-fluid [:a.brand {:href "#"} config/website-name]
     [:ul.nav {}
      [:li.active [:a {:href "/"} "Home"]]
      [:li [:a {:href "/blog"} "Blog"]]
      [:li [:a {:href "/projects"} "Projects"]]
      [:li.dropdown {:data-dropdown "dropdown"}
       [:a.dropdown-toggle {:href "#"} "Apps"]
       [:ul.dropdown-menu {}
        [:li [:a {:href "/apps/bookmarkers"} "Bookmarkers"]]
        [:li [:a {:href "/apps/todolist"} "Todolist"]]
        [:li.divider]
        ]]
      [:li [:a {:href "/mobile"} "Mobile"]]
      (when (auth/is-me?)
        [:li [:a {:href "http://hg.itang.me" :target "_blank"} "hg.itang.me"]])
      [:li [:a {:href "/about"} "About"]]]
     [:p.pull-right {}
      (if (user-logged-in?)
        (let [nickname (get-nickname (current-user))
              email (get-email (current-user))]
          (list [:a {:href (str "/users/" nickname)} nickname] "&nbsp;" [:a {:href (logout-url)} "Sign out"]))
        [:a {:href (login-url)} "Sign in"])]]]])

(defn get-footer
  ([] (get-footer (get-app)))
  ([app]
    [:footer {}
     [:p (str "&copy; 2011 | " (:version app))]]))

(defn default-layout
  "默认布局"
  ;;[content & {:keys [title] :or {title "爱唐网"} :as options}]
  [content & {:keys [title] :as options}]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (html5
     [:head [:meta {:name "description" :content (getstring options "description" "itang的Personal Website")}]
      [:meta {:author "description" :content "itang - 唐古拉山"}]
      [:title (getstring title " - " config/website-title)]
      (include-lib-min-css "bootstrap" "1.4.0")
      (include-app-css "main")
      [:link {:rel "shortcut icon" :href "/public/app/images/favicon.ico"}]
      (include-lib-min-js "underscore" "1.2.2")
      (include-lib-min-js "underscore.string" "2.0.0")
      (include-lib-min-js "jquery" "1.7.0")
      (include-lib-js "handlebars" "1.0.0.beta.4")
      (include-lib-js "bootstrap" "1.4.0" "-dropdown.js")]
     [:body {}
      (get-topbar)
      [:div.container-fluid {}
       content]])})


(ns www-itang-me.views.layouts
  (:use [hiccup core page]
        [appengine-magic.services.user
         :only (user-logged-in? current-user get-nickname get-email login-url logout-url)])
  (:require [www-itang-me.auth :as auth]
            [www-itang-me.config :as config])
  (:use mvc.view-helpers
        [mvc.scopes :only [request]]
        [www-itang-me.utils :only (getstring)]
        [www-itang-me.models.app :only (get-app)]))

(defn- match-uri [uri link]
  ;;TODO 更灵活的匹配规则
  (= uri link))

(defn- menu
  ([name link]
    (menu name link {}))
  ([name link attr]
    (let [a-attr (merge attr {:href link})
          li-attr (if (match-uri (:uri (request)) link)
        {:class "active"}
        nil)]
      [:li li-attr [:a a-attr name]])))

(defn get-topbar []
  [:div {:class "navbar navbar-fixed-top"}
   [:div.navbar-inner {}
    [:div.container-fluid [:a.brand {:href "#"} config/website-name]
     [:div.nav-collapse {}
      [:ul.nav {}
       (menu "Home" "/")
       (menu "Blog" "/blog")
       (menu "Projects" "/projects")
       [:li.dropdown {:data-dropdown "dropdown"}
        [:a.dropdown-toggle {:href "#"} "Apps"]
        [:ul.dropdown-menu {}
         (menu "Bookmarkers" "/apps/bookmarkers")
         (menu "Todolist" "/apps/todolist")
         [:li.divider ]
         (menu "Shuo" "/apps/shuo")]]
       (menu "Mobile" "/mobile")
       (menu "About" "/about")
       (when (auth/is-me?)
         (menu "hg.itang.me" "http://hg.itang.me" {:target "_blank"}))]
      [:p {:class "navbar-text pull-right"}
       (if (user-logged-in?)
         (let [nickname (get-nickname (current-user))
               email (get-email (current-user))]
           (list [:a {:href (str "/users/" nickname)} nickname]
             "&nbsp;" [:a {:href (logout-url)} "Sign out"]))
         [:a {:href (login-url)} "Sign in"])]]]]])

(defn get-footer
  ([] (get-footer (get-app)))
  ([app]
    [:footer {}
     [:div [:span "&copy;&nbsp;"
            [:b "itang"]
            "&nbsp;2011-2012"]
      [:span " | Powered by "
       [:a {:href "http://www.clojure.org" :target "_blank"} "Clojure"]
       " and "
       [:a {:href "http://code.google.com/appengine" :target "_blank"} "GAE"]
       ",&nbsp;v" (:version app)]]]))

(defn default-layout-template
  "默认布局模板"
  ;;[content & {:keys [title] :or {title "爱唐网"} :as options}]
  [content & {:keys [title] :as options}]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (html5
           [:head [:meta {:name "description"
                          :content (getstring options "description" "itang的Personal Website")}]
            [:meta {:author "description" :content "itang - 唐古拉山"}]
            [:title (getstring title " - " config/website-title)]
            (include-css "/public/libs/bootstrap-2.0.2/css/bootstrap.min.css")
            (include-css "/public/libs/bootstrap-2.0.2/css/bootstrap-responsive.min.css")
            (include-app-css "main")
            [:link {:rel "shortcut icon" :href "/public/app/images/favicon.ico"}]
            (include-lib-js "roy" "0.1") ;; have include underscore, first it.
            (include-lib-js "underscore" "1.2.2")
            (include-lib-js "underscore.string" "2.0.0")
            (include-lib-min-js "jquery" "1.7.1")
            (include-lib-js "handlebars" "1.0.0.beta.4")
            (include-js "/public/libs/bootstrap-2.0.2/js/bootstrap.min.js")]
           [:body {}
            (get-topbar)
            content])})

(defn default-layout
  "默认布局"
  [content & options]
  (let [fills [:div.container-fluid {}
               [:div.row-fluid {} content]]]
    (apply default-layout-template (cons fills options))))




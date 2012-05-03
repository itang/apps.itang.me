(ns www-itang-me.views.layout
  (:require [net.cgrand.enlive-html :as html])
  (:use [appengine-magic.services.user
         :only (user-logged-in? current-user get-nickname get-email login-url logout-url)]))

(html/defsnippet top-user-info-login "views/includes/info-login.html" [:div ]
  []
  [:#who ] (html/content (get-nickname (current-user)))
  [:#link-signout ] (html/set-attr :href (logout-url)))

(html/defsnippet top-user-info-to-login "views/includes/info-to-login.html" [:div ]
  []
  [:#link-signin ] (html/set-attr :href (login-url)))

(html/deftemplate main-layout "views/main-layout.html"
  [body]
  [:#top-user-info ] (html/content
                       (if (user-logged-in?)
                         (top-user-info-login)
                         (top-user-info-to-login)))
  [:div#wrap-content ] (html/substitute body))

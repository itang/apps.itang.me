(ns www-itang-me.auth
  (:use [appengine-magic.services.user
         :only (user-logged-in? current-user get-nickname get-email login-url logout-url)]))

(defn is?
  "判断是当前登录用户?"
  [name]
  (if (user-logged-in?)
    (= name (first (.split (get-email (current-user)) "@")))
    false))

(defn is-me?
  []
  (or (is? "live.tang") (is? "itang")))




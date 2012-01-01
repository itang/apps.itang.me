(ns www-itang-me.auth
  (:use [appengine-magic.services.user
         :only (user-logged-in? current-user get-email)])
  (:use [www-itang-me.utils :only [name-from-email]]))

(defn is?
  "判断是当前登录用户?"
  [name]
  (and (user-logged-in?)
    ;; (= name (first (.split (get-email (current-user)) "@")))
    (= name (-> (current-user)
              get-email
              name-from-email))))

(defn is-me? []
  ;;(or (is? "live.tang") (is? "itang")))
  ;;(->> ["live.tang" "itang"] (some is?)))
  (some is? ["live.tang" "itang"]))




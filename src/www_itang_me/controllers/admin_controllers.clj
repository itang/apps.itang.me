(ns www-itang-me.controllers.admin-controllers
  (:use compojure.core)
  (:use [mvc.controller-helpers :only (Ok)]
        [www-itang-me.models.app :only (reset-app)]
        [www-itang-me.models.bookmarkers :only (remove-all-bookmarkers)]))

(defn admin-routes
  []
  (context "/admin" _
    (GET "/cleardata" _
      (do
        (reset-app)
        (remove-all-bookmarkers))
      (Ok "清理数据成功!"))))
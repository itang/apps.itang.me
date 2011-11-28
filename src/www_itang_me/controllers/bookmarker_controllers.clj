(ns www-itang-me.controllers.bookmarker-controllers
  (:use [compojure.core])
  (:require [www-itang-me.models.bookmarkers :as bookmarkers])
  (:require [www-itang-me.views.bookmarker-views :as views])
  (:use [www-itang-me.utils :only (view-json)]))

(defn bookmarker-routes
  []
  (context "/apps/bookmarkers" _
    (GET "/" _
      (views/index (bookmarkers/find-all-bookmarkers) (bookmarkers/get-default-show-site-url)))
    (POST "/:name/inc_hits" [name]
      (do
        (if-let [result (bookmarkers/inc-hits name)]
          (view-json {:success true :message "更新成功!" :data {:currHits (:hits result)}})
          (view-json {:success false :message "不存在!"}))))))

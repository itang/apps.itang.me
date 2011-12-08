(ns www-itang-me.controllers.bookmarker-controllers
  (:use compojure.core)
  (:require [www-itang-me.models.bookmarkers :as bookmarkers]
            [www-itang-me.views.bookmarker-views :as views]
            [www-itang-me.auth :as auth])
  (:use [mvc.controller-helpers :only (Json)]))

(defn bookmarker-routes
  []
  (context "/apps/bookmarkers" _
    (GET "/" _
      (views/index (if (auth/is-me?)
                     (bookmarkers/find-all-bookmarkers)
                     (bookmarkers/find-public-bookmarkers))))

    (POST "/:name/inc_hits" [name]
      (do
        (if-let [result (bookmarkers/inc-hits name)]
          (Json {:success true :message "更新成功!" :data {:currHits (:hits result)}})
          (Json {:success false :message "不存在!"}))))))

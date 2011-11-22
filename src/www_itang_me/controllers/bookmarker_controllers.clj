(ns www-itang-me.controllers.bookmarker_controllers
  (:use [compojure.core])
  (:require [www-itang-me.models.bookmarkers :as bookmarkers])
  (:require [www-itang-me.views.bookmarker_views :as views])
  (:use [www-itang-me.utils :only (view_json)])
  )

(defn bookmarker_controllers
  []
  (context "/apps/bookmarkers" _
    (GET "/" _
      (views/index (bookmarkers/all_bookmarkers) (bookmarkers/default_show_site_url)))
    (POST "/:name/inc_hits" [name]
      (do
        (let [result (bookmarkers/inc_hits name)]
          (if (nil? result)
            (view_json {:success false :message "不存在!"})
            (view_json {:success true :message "更新成功!" :data {:currHits (:hits result)}})
            ))))))

(ns www-itang-me.controllers.bookmarker_controllers
  (:use [compojure.core])
  (:require [www-itang-me.models.bookmarkers :as bookmarkers])
  (:require [www-itang-me.views.bookmarker_views :as views])
  )

(defn bookmarker_controllers
  []
  (GET "/apps/bookmarkers" _
    (views/index (bookmarkers/all_bookmarkers) (bookmarkers/default_show_site_url))))

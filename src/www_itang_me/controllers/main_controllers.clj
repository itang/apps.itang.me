(ns www-itang-me.controllers.main_controllers
  (:use [compojure.core])
  (:require [www-itang-me.models.bookmarkers :as bookmarkers])
  (:require [www-itang-me.views.main_views :as views])
  )

(defn main_controllers
  []
  (GET "/" _
    (views/index (bookmarkers/hot_bookmarkers))
    ))
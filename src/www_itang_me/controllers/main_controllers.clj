(ns www-itang-me.controllers.main-controllers
  (:use compojure.core)
  (:require [www-itang-me.models.bookmarkers :as bookmarkers]
    [www-itang-me.models.app :as app]
    [www-itang-me.views.main-views :as views]))

(defn main-routes
  []
  (GET "/" _
    (views/index (bookmarkers/find-hot-bookmarkers) (app/get-app))))
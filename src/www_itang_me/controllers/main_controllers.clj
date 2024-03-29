(ns www-itang-me.controllers.main-controllers
  (:use compojure.core)
  (:require [www-itang-me.models.bookmarkers :as bookmarkers]
            [www-itang-me.models.app :as app]
            [www-itang-me.auth :as auth])
  (:use mvc.controller-helpers)
  (:require [net.cgrand.enlive-html :as html]))

(html/deftemplate index "views/index.html"
  [ctx]
  [:span#version ] (html/content (:version ctx)))

(defn main-routes
  []
  (GET "/" _
    (Html (index (app/get-app)))))
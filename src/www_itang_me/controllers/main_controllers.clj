(ns www-itang-me.controllers.main-controllers
  (:use compojure.core)
  (:require [www-itang-me.models.bookmarkers :as bookmarkers]
            [www-itang-me.models.app :as app]
            [www-itang-me.views.main-views :as views]
            [www-itang-me.auth :as auth]))

(defn main-routes
  []
  (GET "/" _
    (let [only-public? (not (auth/is-me?))]
      (println only-public?)
      (views/index (bookmarkers/find-hot-bookmarkers only-public?) (app/get-app)))))
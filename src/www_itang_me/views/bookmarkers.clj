(ns www-itang-me.views.bookmarkers
  (:use [net.cgrand.enlive-html])
  (:require [www-itang-me.models.projects :as Project]
            [www-itang-me.auth :as auth]))

(defsnippet index "views/bookmarkers/index.html" [:div#body-content ]
  [bookmarkers]
  [:div.sidebar :div.well :b ]
  (content (str (count bookmarkers)))

  [:ul :li ] (clone-for [bookmarker bookmarkers]
               [:a.sitelink ]
               (do->
                 (set-attr :data-it (:name bookmarker))
                 (set-attr :href (:url bookmarker))
                 (set-attr :title (:tags bookmarker))
                 (content (:title bookmarker)))

               [:small :span ]
               (do->
                 (set-attr :id (str (:name bookmarker) "hits"))
                 (content (str (:hits bookmarker))))))

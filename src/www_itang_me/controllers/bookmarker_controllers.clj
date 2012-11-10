(ns www-itang-me.controllers.bookmarker-controllers
  (:use compojure.core
        [cljtang.lang :only (empty-else)])
  (:require [www-itang-me.models.bookmarkers :as bookmarkers]
            [www-itang-me.views.layouts :as layouts]
            [www-itang-me.views.bookmarkers :as views-bookmarkers]
            [www-itang-me.auth :as auth])
  (:use [mvc.controller-helpers :only (Html Json)]))

(defn bookmarker-routes
  []
  (context "/bookmarkers" _
    (GET "/" _
      (Html (layouts/main
              "书签 - 爱唐"
              "bookmarkers"
              (views-bookmarkers/index (if (auth/is-me?)
                                         (bookmarkers/find-all-bookmarkers)
                                         (bookmarkers/find-public-bookmarkers))))))

    (POST "/" [name title url] ;{{name "name" title "title" url "url"} :params}
      (println name title url)
      (let [bookmarker (bookmarkers/add-bookmarker name (empty-else title name) url)]
        (Json {:success true :message "添加成功!" :data bookmarker})))

    (POST "/:name/inc_hits" [name]
      (do
        (if-let [result (bookmarkers/inc-hits name)]
          (Json {:success true :message "更新成功!" :data {:currHits (:hits result)}})
          (Json {:success false :message "不存在!"}))))))

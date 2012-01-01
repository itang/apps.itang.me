(ns www-itang-me.controllers.setting-controllers
  (:use [compojure.core :only [context POST]])
  (:require [www-itang-me.models.app :as App])
  (:use [mvc.controller-helpers :only (Json)]))

(defn setting-routes []
  (context "/apps/main" _
    (POST "/update-version" [name version]
      (try
        (let [app (App/update-app-version name version)]
          (Json {:success true :message "更新应用版本成功!" :data app}))
        (catch Exception e (Json {:success false :message "更新应用版本失败!" :data (.getMessage e)}))))))
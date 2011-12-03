(ns www-itang-me.models.app
  (:require [appengine-magic.services.datastore :as ds]))

(ds/defentity App [^:key name, version, author])

(defn create-app
  "创建应用配置"
  [name version author]
  (ds/save! (App. name version author)))

(defn get-app
  "获取应用配置"
  []
  (first (ds/query :kind App)))

(defn exists-app?
  "存在应用配置?"
  []
  (not (empty? (ds/query :kind App))))

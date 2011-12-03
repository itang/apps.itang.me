(ns www-itang-me.bootstrap
  (:use [clojure.tools.logging :only (info error)])
  (:require [www-itang-me.models.bookmarkers :as bookmarkers]
    [www-itang-me.models.app :as app]))

(def appinfo {:name "www.itang.me" :version "0.0.1-20111125-SNAPSHOT" :author "itang"})

(defn- need-init-data?
  "需要执行初始化数据操作吗?"
  []
  (not (app/exists-app?)))

(defn- init-data-task
  "初始化数据"
  []
  (if (need-init-data?)
    (do
      (info "start to init data...")
      (info "try to create app...")
      (app/create-app (:name appinfo) (:version appinfo) (:author appinfo))
      (info "finished create app!")
      (info "try to init default bookmarkers...")
      (bookmarkers/init-default-data)
      (info "finished init default bookmarkers!")
      (info "finished  init data!"))
    (info "Don't need to init data!")))

(defn do-bootstrap
  "Bootstrap for application"
  []
  (do
    (init-data-task)))



(ns www-itang-me.bootstrap
  (:require [www-itang-me.models.bookmarkers :as bookmarkers])
  (:require [www-itang-me.models.app :as app]))

(def appinfo {:name "www.itang.me" :version "0.0.1-20111123-SNAPSHOT" :author "itang"})

(defn __need_init_data?
  "需要执行初始化数据操作吗?"
  []
  (not (app/exists_app?)))



(defn __init_data!
  "初始化数据"
  []
  (if (__need_init_data?)
    (do
      (println "Start to init data")
      (println "create app...")
      (app/create_app! (:name appinfo) (:version appinfo) (:author appinfo))
      (println "init default bookmarkers")
      (bookmarkers/init_default_data))
    (println "Don't need to init data!")
    ))

(defn do_bootstrap
  "Bootstrap"
  []
  (do
    (println "try init data...")
    (__init_data!))
  )



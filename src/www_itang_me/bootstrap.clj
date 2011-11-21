(ns www-itang-me.bootstrap
  (:require [www-itang-me.models.bookmarkers :as bookmarkers]))

(defn __init_data!
  "初始化数据"
  []
  (do
    (bookmarkers/init_default_data)))

(defn do_bootstrap
  "Bootstrap"
  []
  (println "init data...")
  (__init_data!))



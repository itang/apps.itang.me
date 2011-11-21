(ns www-itang-me.app_servlet
  (:gen-class :extends javax.servlet.http.HttpServlet
              :exposes-methods {init superInit})
  (:use www-itang-me.core)
  (:use [appengine-magic.servlet :only [make-servlet-service-method]])
  (:require [www-itang-me.bootstrap :as bs]))

(defn -init-void [this]
  (println "BOOTSTRAP")
  (bs/do_bootstrap))

(defn -service [this request response]
  ((make-servlet-service-method www-itang-me-app) this request response))

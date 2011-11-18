(ns www-itang-me.app_servlet
  (:gen-class :extends javax.servlet.http.HttpServlet)
  (:use www-itang-me.core)
  (:use [appengine-magic.servlet :only [make-servlet-service-method]]))


(defn -service [this request response]
  ((make-servlet-service-method www-itang-me-app) this request response))

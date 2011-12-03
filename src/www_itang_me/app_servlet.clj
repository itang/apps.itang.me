(ns www-itang-me.app_servlet
  (:gen-class :extends javax.servlet.http.HttpServlet
    :exposes-methods {init superInit})
  (:use [clojure.tools.logging :only (info error)]
    [appengine-magic.servlet :only [make-servlet-service-method]])
  (:use www-itang-me.core
    [www-itang-me.bootstrap :only (do-bootstrap)]))

(defn -init-void
  "应用引导期执行任务(in servlet 初始化)"
  [this]
  (do
    (info "start to bootstrap...")
    (do-bootstrap)
    (info "bootstrap finished.")))

(defn -service [this request response]
  ((make-servlet-service-method www-itang-me-app) this request response))
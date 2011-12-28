(ns www-itang-me.controllers.proxy-controllers
  (:use compojure.core)
  (:require [www-itang-me.auth :as auth]
            [appengine-magic.services.url-fetch :as http]
            [clojure.java.io :as io])
  (:use [www-itang-me.utils :only (empty-else)]))

(defn- get-page-content [url]
  (String. (:content (http/fetch url :method :get )) "UTF-8"))

;;TODO
(defn- wrap-css [content]
  content)

;;TODO
(defn- wrap-js [content]
  content)

(defn- fetch-page [url]
  (let [content (get-page-content url)]
    ((comp wrap-css wrap-js) content)))

(defn proxy-routes []
  (context "/apps/proxy" _
    (GET "/" [url]
      {:status 200
       :headers {"Content-Type" "text/html"}
       :body (fetch-page url)})))
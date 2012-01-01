(ns www-itang-me.controllers.proxy-controllers
  (:import org.apache.commons.codec.binary.Base64)
  (:use [compojure.core :only [context GET]]
        [clojure.tools.logging :only [info error]])
  (:require [appengine-magic.services.url-fetch :as http]
            [clojure.java.io :as io])
  (:use [mvc.controller-helpers :only [Ok]]
        [www-itang-me.utils :only [empty-else]]))

(defn- fetch-page [url]
  (letfn [(get-page-content [url]
            (String.
              (:content (http/fetch url :method :get )) "UTF-8"))
          (wrap-js [content] content)
          (wrap-css [content] content)]
    (let [content (get-page-content url)]
      (-> content
        wrap-js
        wrap-css))))

(defn- do-proxy [url]
  (Ok (fetch-page url)))

(defn proxy-routes []
  (context "/apps/proxy" _
    (GET "/" [url]
      (let [decode-url
            (String. (Base64/decodeBase64 (apply str (reverse url))))]
        (do
          (info "encode url:" url "\ndecode url:" decode-url)
          (do-proxy decode-url))))))
(ns mvc.controller-helpers
  (:use [hiccup.page-helpers :only (html5)])
  (:require [cheshire.core :as json])
  (:use [mvc.templates :only [stencil-template]]))

(defstruct Message :success :message :data :detailMessage )

(defn as-message [success message data detailMessage]
  (struct-map Message :success success :message message :data data :detailMessage detailMessage))

(defn success-message
  ([message]
    (success-message message {} ""))
  ([message data]
    (success-message message data ""))
  ([message data detailMessage]
    (as-message true message data detailMessage)))

(defn failture-message
  ([message]
    (failture-message message {} ""))
  ([message data]
    (failture-message message data ""))
  ([message data detailMessage]
    (as-message false message data detailMessage)))

(defn Result
  [body & [options]]
  (let [default-options
        {:status 200
         :headers {"Content-Type" "text/html"}
         :body body}]
    (if-not options
      default-options
      (merge default-options options))))

(defn Html
  [body]
  (Result body))

(defn Html-by-template [tpl-name & more]
  (try
    (Html (apply stencil-template tpl-name more))
    (catch Exception e (Html (.getMessage e)))))

(defn Ok
  [body]
  (Html body))

(defn Json
  [json]
  (Result
    (if (string? json) json (json/generate-string json))
    {:headers {"Content-Type" "application/json"}}))

(defn Todo
  "todo page view"
  [todo-item]
  (Ok (html5
        [:head [:title "TODO"]]
        [:body {}
         [:h1 (str "@TODO: " todo-item)]])))
(ns www-itang-me.utils
  (:use [hiccup core page-helpers])
  (:import [java.util Date Calendar TimeZone Locale])
  (:import [java.text DateFormat SimpleDateFormat])
  (:use [cheshire.core]))

(defn todo-html
  "todo page view"
  [todo-item]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (html5 [:h1 (str "@TODO: " todo-item)])})

(defn view-ok
  [body]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body body}
  )

(defn view-json
  [json]
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body (if (string? json)
           json
           (generate-string json))})

(defn now
  "获取格式化后的当前时间"
  []
  (let [formatter (SimpleDateFormat. "yyyy-MM-dd HH:mm:ss")
        myTimeZone (TimeZone/getTimeZone "Asia/Shanghai")]
    (do
      (.setTimeZone formatter myTimeZone)
      (.format formatter (Date.)))))
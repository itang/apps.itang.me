(ns www-itang-me.utils
  (:use [hiccup core page-helpers])
  (:import [java.util Date Calendar TimeZone Locale])
  (:import [java.text DateFormat SimpleDateFormat]))

(defn todo_html [todo_item]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (html5 [:h1 (str "@TODO: " todo_item)])})

(defn now []
  (let [formatter (SimpleDateFormat. "yyyy-MM-dd HH:mm:ss")
        myTimeZone (TimeZone/getTimeZone "Asia/Shanghai")]
    (do
      (.setTimeZone formatter myTimeZone)
      (.format formatter (Date.)))))
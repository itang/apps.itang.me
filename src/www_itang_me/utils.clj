(ns www-itang-me.utils
  (:use [hiccup core page-helpers])
  (:import [java.util Calendar TimeZone Locale])
  (:import java.text.SimpleDateFormat))

(defn todo_html [it]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (html5 [:h1 (str it "(TODO)")])})

(defn now []
  (let [sdf (SimpleDateFormat. "yyyy-MM-dd HH:mm:ss")
        time (.getTime (Calendar/getInstance (TimeZone/getTimeZone "Asia/Shanghai") (Locale/CHINA)))]
    (.format sdf time)))
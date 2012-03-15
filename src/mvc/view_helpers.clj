(ns mvc.view-helpers
  (:use [hiccup.page :only (include-js include-css)]))

(defn javascript-tag-ext
  "javascript tag, 支持属性设定"
  [attributes script]
  [:script (merge {:type "text/javascript"} attributes) script])

(defn roy-tag
  [attributes script]
  [:royscript (merge {:type "text/roy"} attributes) script])

(defn include-roy [path]
  [:royscript {:type "text/roy" :href path} nil])

(defn include-app-roy
  ([name]
    (include-app-roy name ".roy"))
  ([name ext]
    (include-roy (str "/public/app/scripts/" name ext))))

(defn- rule-path [prefix-path name version ext]
  (str prefix-path "/" name "-" version "/" name ext))

(defn include-lib-js
  ([name version]
    (include-lib-js name version ".js"))
  ([name version ext]
    (include-js (rule-path "/public/libs" name version ext))))

(defn include-lib-min-js [name version]
  (include-lib-js name version ".min.js"))

(defn include-lib-css
  ([name version]
    (include-lib-css name version ".css"))
  ([name version ext]
    (include-css (rule-path "/public/libs" name version ext))))

(defn include-lib-min-css [name version]
  (include-lib-css name version ".min.css"))

(defn include-app-js
  ([name]
    (include-app-js name ".js"))
  ([name ext]
    (include-js (str "/public/app/scripts/" name ext))))

(defn include-app-min-js [name]
  (include-app-js name ".min.js"))

(defn include-app-css
  ([name]
    (include-app-css name ".css"))
  ([name ext]
    (include-css (str "/public/app/styles/" name ext))))

(defn include-app-min-css [name]
  (include-app-css name ".min.css"))

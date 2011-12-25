(ns www-itang-me.utils
  (:import [java.util Date Calendar TimeZone Locale]
           [java.text DateFormat SimpleDateFormat])
  (:use [clojure.string :only (blank?)]))

(defn now
  "获取格式化后的当前时间"
  []
  (let [formatter (SimpleDateFormat. "yyyy-MM-dd HH:mm:ss")
        myTimeZone (TimeZone/getTimeZone "Asia/Shanghai")]
    (do
      (.setTimeZone formatter myTimeZone)
      (.format formatter (Date.)))))

(defn getstring
  ([value link-symbol target]
    (if value
      (str value link-symbol target)
      target))
  ([options key link-symbol target]
    (if (and options key)
      (getstring ((keyword key) options) link-symbol target)
      target)))

(defn empty-x? [obj]
  "判定值是否为空"
  (cond
    (nil? obj) true
    (string? obj) (blank? obj) ;; blank?
    (char? obj) (blank? (str obj))
    (coll? obj) (empty? obj) ;; empty? coll
    :else false))

(defmacro if-empty [cond ept-expr els-expr]
  `(if (empty-x? ~cond) ~ept-expr ~els-expr))

(defmacro empty-else [obj default-value-expr]
  `(if-empty ~obj ~default-value-expr ~obj))


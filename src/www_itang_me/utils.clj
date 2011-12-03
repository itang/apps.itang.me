(ns www-itang-me.utils
  (:import [java.util Date Calendar TimeZone Locale]
    [java.text DateFormat SimpleDateFormat]))

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

(ns cljtang.lang
  (:use [clojure.string :only (blank?)]))

(defn more-args-as-map [more-array]
  (cond
    (nil? more-array) nil
    (and (= 1 (count more-array)) (map? (first more-array))) (first more-array)
    :else (apply hash-map more-array)))

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
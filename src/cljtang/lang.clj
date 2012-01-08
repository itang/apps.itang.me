(ns cljtang.lang)

(defn more-args-as-map [more-array]
  (cond
    (nil? more-array) nil
    (and (= 1 (count more-array)) (map? (first more-array))) (first more-array)
    :else (apply hash-map more-array)))
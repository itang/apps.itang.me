(ns cljtang.test.lang
  (:use midje.sweet)
  (:use cljtang.lang))

(fact "more-args-as-map"
  (more-args-as-map nil) => nil
  (more-args-as-map []) => {}
  (more-args-as-map [{:name "itang"}]) => {:name "itang"}
  (more-args-as-map [:name "itang" :age 18]) => {:name "itang" :age 18})
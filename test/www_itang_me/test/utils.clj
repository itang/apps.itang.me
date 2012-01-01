(ns www-itang-me.test.utils
  (:use clojure.test)
  (:use midje.sweet)
  (:use www-itang-me.utils))

(deftest just-test
  (is (= (+ 1 1) 2) "error"))

;;(defn note-expected-failure [] (println "^^^^ The previous failure was expected ^^^^"))

(fact (+ 1 1) => 2)

(facts "getstring"
  (getstring "a" "-" "b") => "a-b"
  (getstring "a" "" "b") => "ab"
  (getstring "a" nil "b") => "ab"
  (getstring "a" nil nil) => "a"

  (getstring nil "-" "b") => "b"
  (getstring nil nil "b") => "b"

  (getstring {:name "a"} :name "-" "b") => "a-b"
  (getstring {:name "a"} "name" "-" "b") => "a-b"

  (getstring nil :name "-" "b") => "b"
  (getstring nil nil "-" "b") => "b"
  (getstring {:name "a"} nil "-" "b") => "b"
  (getstring {:name "a"} :name1 "-" "b") => "b"
  (getstring {:name "a"} "name1" "-" "b") => "b")

(fact "name from email"
  (name-from-email "live.tang@gmail.com") => "live.tang"
  (name-from-email "live.tang@") => "live.tang"
  (name-from-email "live.tang") => "live.tang"
  (name-from-email "live.tanggmail.com") => "live.tanggmail.com"
  (name-from-email "") => ""
  (name-from-email nil) => "")


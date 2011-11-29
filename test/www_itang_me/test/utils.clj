(ns www-itang-me.test.utils
  (:use clojure.test)
  (:use midje.sweet)
  (:use www-itang-me.utils))

(deftest just-test
  (is (= (+ 1 1) 2) "error"))

;;(defn note-expected-failure [] (println "^^^^ The previous failure was expected ^^^^"))

(fact (+ 1 1) => 2)

(facts "todo-html"
  (:status (todo-html "todo")) => 200
  (:headers (todo-html "todo")) => {"Content-Type" "text/html"})

(facts "view-json"
  (:status (view-json "{}")) => 200
  (:headers (view-json "{}")) => {"Content-Type" "application/json"}
  (:body (view-json "{}")) => "{}"
  (:body (view-json {})) => "{}"
  (:body (view-json {:name "itang"})) => "{\"name\":\"itang\"}"
  (:body (view-json [1,2])) => "[1,2]")


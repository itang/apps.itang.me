(ns mvc.test.controller-helpers
  (:use clojure.test)
  (:use midje.sweet)
  (:use mvc.controller-helpers))

(facts "Todo"
  (:status (Todo "todo")) => 200
  (:headers (Todo "todo")) => {"Content-Type" "text/html"})

(facts "Json"
  (:status (Json "{}")) => 200
  (:headers (Json "{}")) => {"Content-Type" "application/json"}
  (:body (Json "{}")) => "{}"
  (:body (Json {})) => "{}"
  (:body (Json {:name "itang"})) => "{\"name\":\"itang\"}"
  (:body (Json [1,2])) => "[1,2]")

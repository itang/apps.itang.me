(defproject www-itang-me "0.0.1-SNAPSHOT"
  :url "http://www.itang.me"
  :description "itang's person website: www.itang.me, powered by #{:clojure :appengine-magic :compojure}!"
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [compojure "0.6.4"]
                 [enlive "1.0.0"]
                 [hiccup "0.3.6"]] ;[joda-time/joda-time "2.0"]
  :dev-dependencies [[appengine-magic "0.4.6-SNAPSHOT"]
                     [lein-eclipse "1.0.0"]]
  :aot [www-itang-me.app_servlet])



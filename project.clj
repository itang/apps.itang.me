(defproject www-itang-me "0.0.1-SNAPSHOT"
  :url "http://www.itang.me"
  :description "itang's person website: www.itang.me, powered by #{:clojure :appengine-magic :compojure}!"
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [org.clojure/tools.logging "0.2.3"]
                 [log4j/log4j "1.2.16"]
                 [commons-codec/commons-codec "1.6"]
                 [compojure "1.1.0"]
                 [hiccup "1.0.0"]
                 [cheshire "4.0.0"]
                 [enlive "1.0.1"]]
  :dev-dependencies [[appengine-magic "0.5.0"]
                     [lein-eclipse "1.0.0"]
                     [midje "1.3.1"]
                     [lein-midje "1.0.8"]
                     [swank-clojure "1.4.0"]]
  :aot [www-itang-me.app_servlet])



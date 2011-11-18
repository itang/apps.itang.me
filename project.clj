(defproject www-itang-me "1.0.0-SNAPSHOT"
  :url "http://www.itang.me"
  :description "itang's person website:www.itang.me, powered by clojure!"
  :dependencies [[org.clojure/clojure "1.2.1"] 
                 [compojure "0.6.4"]]
  :dev-dependencies [[appengine-magic "0.4.6-SNAPSHOT"]
                     [lein-eclipse "1.0.0"]]
  :aot [www-itang-me.app_servlet])



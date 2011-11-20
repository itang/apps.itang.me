(ns www-itang-me.models.bookmarkers)

(defn all_bookmarkers
  "获取所有的书签"
  []
  [{:id 1 :name "dzone" :url "http://www.dzone.com"}
   {:id 2 :name "github" :url "https://github.com"}
   {:id 3 :name "infoq" :url "http://www.infoq.com"}
   {:id 4 :name "infoqcn" :url "http://www.infoq.com/cn"}
   {:id 5 :name "javaeye" :url "http://www.javaeye.com"}
   {:id 6 :name "jQuery" :url "http://jquery.com"}
   {:id 7 :name "pragrammingscala" :url "http://programming-scala.labs.oreilly.com"}
   {:id 8 :name "cnki" :url "http://dict.cnki.net/"}
   {:id 9 :name "gr" :url "https://www.google.com/reader/view/"}
   {:id 10 :name "tianya" :url "http://www.tianya.cn/publicforum/articleslist/0/house.shtml"}
   {:id 11 :name "appengine" :url "https://appengine.google.com"}
   {:id 12 :name "hntv" :url "http://tieba.baidu.com/f?kw=%BA%FE%C4%CF%CE%C0%CA%D3"}
   {:id 13 :name "sina" :url "http://news.sina.com.cn"}
   {:id 14 :name "kangcheng" :url "http://szbbs.soufun.com/board/2810443902/"}])

(defn hot_bookmarkers
  "常用的书签"
  []
  (take 6 (all_bookmarkers)))

(defn get_default_show_site
  "默认显示site"
  []
  "http://www.dzone.com")

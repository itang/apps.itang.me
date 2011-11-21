(ns www-itang-me.models.bookmarkers
  (:require [appengine-magic.services.datastore :as ds]))

(ds/defentity BookmarkerTag [^:key name, total])
(ds/defentity Bookmarker [^:key name, title, url, hits, tags])

(defn add_tag
  [tag]
  )

(defn init_default_data
  []
  (let [bookmarkers
        [(Bookmarker. "dzone" "dzone" "http://www.dzone.com" 1 "default")
         (Bookmarker. "github" "github" "https://github.com" 0 "github")
         (Bookmarker. "infoq" "InfoQ" "http://www.infoq.com" 0 "default")
         (Bookmarker. "infoqcn" "InfoQ中国" "http://www.infoq.com/cn" 0 "default")
         (Bookmarker. "hntv_tieba" "湖南卫视贴吧" "http://tieba.baidu.com/f?kw=%BA%FE%C4%CF%CE%C0%CA%D3" 0 "娱乐")
         (Bookmarker. "kangcheng" "康城论坛" "http://szbbs.soufun.com/board/2810443902" 0 "生活")
         (Bookmarker. "javaeye" "javaeye" "http://www.javaeye.com" 0 "default")
         (Bookmarker. "appengine" "GAE" "https://appengine.google.com" 0 "GAE")
         (Bookmarker. "jQuery" "jQuery" "http://jquery.com" 0 "jQuery")
         (Bookmarker. "gr" "Google Reader" "https://www.google.com/reader/view/" 0 "default")
         (Bookmarker. "pragrammingscala" "pscala" "http://programming-scala.labs.oreilly.com" 0 "scala")
         (Bookmarker. "sina" "sina" "http://news.sina.com.cn" 0 "娱乐")
         ]]
    (ds/save! bookmarkers)))

(defn all_bookmarkers
  "获取所有的书签"
  []
  (ds/query :kind Bookmarker :sort [[:hits :dsc ]]))

(defn hot_bookmarkers
  "常用的书签"
  []
  (take 6 (all_bookmarkers)))

(defn default_show_site_url
  "默认显示site"
  []
  (let [bookmarkers (all_bookmarkers)]
    (if (empty? bookmarkers)
      "http://www.infoq.com"
      (:url (first bookmarkers)))))

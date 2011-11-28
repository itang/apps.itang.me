(ns www-itang-me.models.bookmarkers
  (:require [appengine-magic.services.datastore :as ds]))

(ds/defentity BookmarkerTag [^:key name, total])
(ds/defentity Bookmarker [^:key name, title, url, hits, tags])

(defn add-tag
  [tag]
  ;; @TODO
  )

(defn init-default-data
  "初始化默认的书签数据"
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
         (Bookmarker. "sina" "sina" "http://news.sina.com.cn" 0 "娱乐")]]
    (ds/save! bookmarkers)))

(defn inc-hits
  "点击量加1"
  [name]
  (if-let [bookmarker (ds/retrieve Bookmarker name)]
    (let [update-bookmarker (assoc bookmarker :hits (inc (:hits bookmarker)))]
      (do
        (ds/save! update-bookmarker)
        update-bookmarker))
    nil))

(defn find-all-bookmarkers
  "获取所有的书签"
  []
  (ds/query :kind Bookmarker :sort [[:hits :dsc ]]))

(defn find-hot-bookmarkers
  "常用的书签"
  []
  (take 6 (find-all-bookmarkers)))

(defn get-default-show-site-url
  "默认显示的site"
  []
  (let [bookmarkers (find-all-bookmarkers)]
    (if (empty? bookmarkers) "http://www.infoq.com" (:url (first bookmarkers)))))

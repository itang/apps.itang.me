(ns www-itang-me.models.bookmarkers
  (:require [appengine-magic.services.datastore :as ds]))

(ds/defentity BookmarkerTag [^:key name, total])
(ds/defentity Bookmarker [^:key name, title, url, hits, tags, public])

(defn add-tag
  [tag]
  ;; @TODO
  )

(defn init-default-data
  "初始化默认的书签数据"
  []
  (let [bookmarkers
        [(Bookmarker. "dzone" "dzone" "http://www.dzone.com" 1 "default" true)
         (Bookmarker. "github" "github" "https://github.com" 0 "github" true)
         (Bookmarker. "infoq" "InfoQ" "http://www.infoq.com" 0 "default" true)
         (Bookmarker. "infoqcn" "InfoQ中国" "http://www.infoq.com/cn" 0 "default" true)
         (Bookmarker. "hntv_tieba" "湖南卫视贴吧" "http://tieba.baidu.com/f?kw=%BA%FE%C4%CF%CE%C0%CA%D3" 0 "娱乐" false)
         (Bookmarker. "kangcheng" "康城论坛" "http://szbbs.soufun.com/board/2810443902" 0 "生活" false)
         (Bookmarker. "javaeye" "javaeye" "http://www.javaeye.com" 0 "default" true)
         (Bookmarker. "appengine" "GAE" "https://appengine.google.com" 0 "GAE" false)
         (Bookmarker. "jQuery" "jQuery" "http://jquery.com" 0 "jQuery" true)
         (Bookmarker. "gr" "Google Reader" "https://www.google.com/reader/view/" 0 "default" false)
         (Bookmarker. "pragrammingscala" "pscala" "http://programming-scala.labs.oreilly.com" 0 "scala" true)
         (Bookmarker. "sina" "sina" "http://news.sina.com.cn" 0 "娱乐" true)]]
    (ds/save! bookmarkers)))

(defn add-bookmarker
  "添加书签"
  [name title url & {:keys [hits tags public] :or {hits 0 tags "default" public true} :as options}]
  (ds/save! (Bookmarker. name title url hits tags public)))

(defn inc-hits
  "点击量加1"
  [name]
  (if-let [bookmarker (ds/retrieve Bookmarker name)]
    (let [update-bookmarker (assoc bookmarker :hits (inc (:hits bookmarker)))]
      (do
        (ds/save! update-bookmarker)
        update-bookmarker))
    nil))

(defn find-bookmarkers
  "获取所有的书签"
  [& {:keys [sort only-public?] :or {sort [[:hits :dsc ]] only-public? false} :as options}]
  (if only-public?
    (ds/query :kind Bookmarker :sort (seq sort) :filter (= :public true))
    (ds/query :kind Bookmarker :sort (seq sort))))

(defn find-all-bookmarkers
  "获取所有的书签"
  []
  (find-bookmarkers))

(defn find-public-bookmarkers
  "获取公开的书签"
  []
  (find-bookmarkers :only-public? true))

(defn find-hot-bookmarkers
  "常用的书签"
  [& [only-public?]]
  (take 6 (if only-public?
            (find-public-bookmarkers)
            (find-all-bookmarkers))))

(defn get-default-show-site-url
  "默认显示的site"
  []
  (let [bookmarkers (find-all-bookmarkers)]
    (if (empty? bookmarkers) "http://www.infoq.com" (:url (first bookmarkers)))))

(defn remove-all-bookmarkers
  "删除所有书签"
  []
  (ds/delete! (ds/query :kind Bookmarker)))

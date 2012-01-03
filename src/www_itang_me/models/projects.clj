(ns www-itang-me.models.projects
  (:require [appengine-magic.services.datastore :as ds])
  (:require [libs.github-client :as ghc]
            [www-itang-me.utils :as utils]))

(ds/defentity Project [^:key name  ;名称
                       author      ;作者
                       created_at  ;创建时间
                       updated_at  ;更新时间
                       language    ;语言
                       website     ;网站
                       description ;描述
                       indexed_at  ;收录时间
                       from        ;来源
                       tags        ;标签
                       attention   ;关注度
                       ])

(defn sync-watching-projects-from-github
  "同步关注的github项目"
  [user]
  (let [projects (ghc/find-all-watching-projects user)
        from "github"
        attention 0]
    (ds/save! (map
                (fn [p]
                  (Project. (:name p)
                            (:author p)
                            (:created_at p)
                            (:updated_at p)
                            (:language p)
                            (:html_url p)
                            (:description p)
                            (utils/now)
                            from
                            (:language p)
                            attention))
                projects))))

(defn projects
  "所有项目"
  [& {:keys [sort] :or {sort [[:attention :dsc] [:updated_at :dsc ]]} :as options}]
  (ds/query :kind Project :sort (seq sort)))

(defn find-by-name [name]
  (ds/retrieve Project name))

(defn ding
  "顶，增加关注度"
  [name]
  (if-let [project (find-by-name name)]
    ;(ds/save! (assoc project :attention (inc (:attention project))))
    (ds/save! (update-in project [:attention] (partial + 1)))))
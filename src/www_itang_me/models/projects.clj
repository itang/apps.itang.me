(ns www-itang-me.models.projects
  (:use [clojure.tools.logging :only [info error]])
  (:require [appengine-magic.services.datastore :as ds])
  (:require [libs.github-client :as ghc]
            [www-itang-me.utils :as utils]))

(ds/defentity Project
  [^:key name ;名称
   author ;作者
   created_at ;创建时间
   updated_at ;更新时间
   language ;语言
   website ;网站
   description ;描述
   indexed_at ;收录时间
   from ;来源
   tags ;标签
   attention ;关注度
   ])

(defn projects
  "所有项目"
  [& {:keys [sort] :or {sort [[:attention :dsc ] [:updated_at :dsc ]]} :as options}]
  (ds/query :kind Project :sort (seq sort)))

(defn find-by-name
  "通过名称获取项目"
  [name]
  (ds/retrieve Project name))

(defn ding
  "顶，增加关注度"
  [name]
  (if-let [project (find-by-name name)]
    ;(ds/save! (assoc project :attention (inc (:attention project))))
    ;(ds/save! (update-in project [:attention] (partial + 1)))
    (ds/save! (update-in project [:attention ] inc))))

(defn- new-project-from-gp [w]
  (Project. (:name w)
    (:author w)
    (:created_at w)
    (:updated_at w)
    (:language w)
    (:html_url w)
    (:description w)
    (utils/now)
    "github"
    (:language w)
    0))

(defn- update-project-from-gp [p gp]
  (merge p
    (select-keys gp [:updated_at :html_url :description :language ])))

(defn sync-watching-projects-from-github
  "同步关注的github项目"
  [user]
  (let [projects (projects)
        watchings (ghc/find-all-watching-projects user)
        cfn (fn [p] [(:name p) p])
        project-map (into {} (map cfn projects))
        watching-map (into {} (map cfn watchings))]
    (doseq [[name watching-project] watching-map]
      (if-let [project (project-map name)] ;;同步过的?
        (do (info "update " name)
          (ds/save! (update-project-from-gp project watching-project)))
        (do (info "add" name)
          (ds/save! (new-project-from-gp watching-project)))))))


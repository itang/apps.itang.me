(ns www-itang-me.models.projects
  (:require [appengine-magic.services.datastore :as ds])
  (:require [libs.github-client :as ghc]
            [www-itang-me.utils :as utils]))

(ds/defentity Project [^:key name author
                       created_at updated_at indexed_at
                       language website from
                       tags description])

(defn sync-watching-projects-from-github
  [user]
  (let [projects (ghc/find-all-watching-projects user)]
    (ds/save! (map
                (fn [p]
                  (Project. (:name p) (:author p)
                    (:created_at p) (:updated_at p) (utils/now)
                    (:language p) (:html_url p) "github"
                    (:language p) (:description p)))
                projects))))

(defn projects
  "所有项目"
  [& {:keys [sort] :or {sort [[:updated_at :dsc ]]} :as options}]
  (ds/query :kind Project :sort (seq sort)))
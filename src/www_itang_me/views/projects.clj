(ns www-itang-me.views.projects
  (:use [net.cgrand.enlive-html])
  (:use [mvc.scopes :only (request)])
  (:require [www-itang-me.models.projects :as Project]
            [www-itang-me.auth :as auth]))

(def max-show-logs-num 6)
(def max-half-show-logs-num (/ max-show-logs-num 2))

(defn- log-as [logs]
  (for [log logs]
    (:sync_at log)))

(defn- show-logs [sync-logs]
  (if (<= (count sync-logs) max-show-logs-num)
    (log-as sync-logs)
    (concat
      (log-as (take max-half-show-logs-num sync-logs))
      ["..."]
      (log-as (take-last max-half-show-logs-num sync-logs)))))

(defsnippet index "views/projects/index.html" [:div#body-content ]
  [ctx]
  [:#btnSync ]
  #(when (auth/is-me?) %)

  [:li.log-item ]
  (clone-for [log (show-logs (:logs ctx))] (content log))

  [:#attention_projects :tbody :tr ]
  (clone-for [[index project] (map #(vector %1 %2) (iterate inc 1) (:attention-projects ctx))]
    [[:td (nth-child 1)]] (content (str index))
    [[:td (nth-child 2)] :a ] (do->
                                (content (str (:name project)))
                                (set-attr :href (:website project))
                                (set-attr :title (str (:description project) "\n" (:updated_at project))))
    [[:td (nth-child 3)]] (content (:language project))
    [[:td (nth-child 4)] :small ] (content (:description project))
    [[:td (nth-child 5)]] (content (-> (:updated_at project) (.replaceAll "T|Z" " ") .trim))
    [[:td (nth-child 6)]] (content (str (:attention project)))
    [[:td (nth-child 7)] :button ] (set-attr :data (:name project)))
  [:#my_projects :li ]
  (clone-for [[index project] (map #(vector %1 %2) (iterate inc 1) (:my-projects ctx))]
    [:span.index ] (content (str index))
    [:a ] (do->
            (set-attr :href (:html_url project))
            (set-attr :title (str (:description project) \newline (:updated_at project)))
            (content (:name project)))
    [:small ] (content (:language project))
    ))

(ns www-itang-me.models.app
  (:require [appengine-magic.services.datastore :as ds]))

(ds/defentity App [^:key name, version, author])

(defn create_app!
  [name version author]

  (ds/save! (App. name version author))
  )

(defn get_app
  []
  (first (ds/query :kind App)))

(defn exists_app?
  []
  (not (empty? (ds/query :kind App))))

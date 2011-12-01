(ns www-itang-me.libs.github-client
  (:require [clojure.string :as string]
            [appengine-magic.services.url-fetch :as http]
            [cheshire.core :as json]))

(def github-api-v3-url "https://api.github.com")
(def DEFAULT-PAGE 1)
(def DEFAULT-PER-PAGE 10)

;;  ported from tentacles / src / tentacles / core.clj
(defn- query-map
  "Turn keywords into strings and replace hyphens with underscores."
  [entries]
  (into {}
    (for [[k v] entries]
      [(.replace (name k) "-" "_") v])))

(defn- safe-pare [response]
  (when-not (= 204 (:status response))
    (json/parse-string (String. (:content response) "UTF-8")) true))

(defn- to-query-params [query]
  (if-not query
    ""
    (let [kvs (for [[k v] query] (str k "=" v))]
      (string/join "&" kvs))))

(defn- make-request [method end-point positional query]
  (let [url (str github-api-v3-url (apply format end-point positional) "?" (to-query-params (query)))
        method method]
    (safe-parse
      (fetch url :method method))))

(defn api-call [method end-point positional query]
  (let [query (query-map query)]
    (make-request method end-point positional query)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn user-repos
  "List a user's repositories.
   Options are:
      types -- all (default), public, private, member."
  [user & [options]]
  (api-call :get "users/%s/repos" [user] options))

(defn watching
  "List all the repositories that a user is watching."
  [user & [options]]
  (api-call :get "users/%s/watched" [user] options))
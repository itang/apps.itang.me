(ns mvc.scopes)

(def ^:dynamic *request*)

(defn request [] *request*)
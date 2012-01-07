(ns mvc.scopes)

(declare ^:dynamic *request*)

(defn request [] *request*)
(ns mvc.scopes)

(defonce ^:dynamic *request* nil)

(defn request [] *request*)
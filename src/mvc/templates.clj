(ns mvc.templates
  (:use [stencil.core :only [render-file render-string]])
  (:use [cljtang.lang :only [more-args-as-map]]))

(defn- template-file-by-tpl-name [tpl-name]
  (str "views/" tpl-name))

(defn stencil-template [tpl-name & more]
  (render-file
    (template-file-by-tpl-name tpl-name)
    (more-args-as-map more)))
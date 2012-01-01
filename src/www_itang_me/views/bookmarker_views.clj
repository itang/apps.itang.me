(ns www-itang-me.views.bookmarker-views
  (:use [hiccup core page-helpers]
        [mvc.view-helpers :only [include-app-js include-lib-min-js]]
        [www-itang-me.views.layouts :only [default-layout-template get-footer]]))

(defn- bookmarkers-layout
  "bookmarkers布局"
  [content & options]
  (let [fills
        (list
          [:div.container-fluid {}
           [:div {:class "input pull-left"}
            [:input.xlarge {:id "address-bar" :name "url" :size= "100" :type "text"}]]
           [:div.pull-left {} [:button#go {:class "btn primary"} "GO"]
            [:button#fgo {:class "btn primary"} "FGO"]]]
          [:div.container-fluid {} content])]
    (apply default-layout-template (cons fills options))))

(defn index
  [bookmarkers & [home-site]]
  (bookmarkers-layout
    (list
      (include-lib-min-js "jquery.base64" "1.0")
      (include-app-js "bookmarkers")
      [:div.sidebar {}
       [:div.well {}
        (str "共收藏<b>" (count bookmarkers) "</b>个站点")
        [:input#refresh {:type "button" :value "刷新" :class "btn"}]
        [:u {}
         (for [site bookmarkers]
           [:li {}
            [:a.sitelink {:href (:url site) :id (:id site) :target "_blank" :title (:tags site) :data-it (:name site)}
             (:title site)]
            [:small {}
             "&nbsp;("
             [:span {:id (str (:name site) "hits")} (:hits site)]
             ")"]])]]] ;;end div.sidebar
      [:div.content {}
       [:div#frame_container {}
        [:iframe#frameWindow.frame {:src home-site :width "100%" :height "100%"}]]
       (get-footer)])
    :title "书签"))



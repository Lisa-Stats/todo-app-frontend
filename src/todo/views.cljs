(ns todo.views
  (:require
   [re-frame.core :refer [subscribe]]
   [todo.router :refer [url-for]]))

(defn home-page
  []
  [:div
   [:h2 "Hello world"]
   [:a
    {:href (url-for :todos)}
    "Click me to go to todos page"]])

(defn todos-page
  []
  [:div
   [:h2 "Hello todos"]
   [:a
    {:href (url-for :home)}
    "Click me to go home"]])

(defn pages
  [page-name]
  (case page-name
    :home  [home-page]
    :todos [todos-page]))

(defn todo-app
  []
  (let [active-page @(subscribe [:active-page])]
    [:<>
     [pages active-page]]))

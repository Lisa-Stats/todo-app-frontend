(ns todo.main
  (:require
   [re-frame.core :refer [dispatch-sync clear-subscription-cache!]]
   [reagent.dom :as rdom]
   [todo.events]
   [todo.router :as router]
   [todo.subs]
   [todo.views :as views]))

(defn ^:export init
  []
  (router/start!)
  (dispatch-sync [:initialize-db])
  (rdom/render [views/todo-app]
               (js/document.getElementById "app")))

(defn ^:dev/after-load start
  []
  (clear-subscription-cache!)
  (rdom/render [views/todo-app]
               (js/document.getElementById "app")))

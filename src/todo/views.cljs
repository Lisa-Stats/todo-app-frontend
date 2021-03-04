(ns todo.views
  (:require
   [reagent.core :as reagent]
   [re-frame.core :refer [dispatch subscribe]]
   [todo.router :refer [url-for]]
   [todo.subs :as subs]))

#_(defn counting-button [txt]
  (let [state (reagent/atom 0)] ;; button counter with local state
    (fn [txt]
      [:button.green
        {:on-click #(swap! state inc)}
        (str txt " " @state)])))

(defn home-page
  []
  (let [button-count (subscribe [::subs/button-info])] ;; button counter with global state
    (fn []
      [:div
       [:h2 "Button Counter"]
       #_[counting-button "click me"]
       [:button
        {:on-click (fn [e]
                     (.preventDefault e)
                     (dispatch [:increment-button]))}
        "click me " @button-count]
       [:div
        [:a
         {:href (url-for :todos)}
         "Click me"]]])))

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

(ns todo.views
  (:require
   [reagent.core :as reagent]
   [re-frame.core :refer [dispatch subscribe]]
   [todo.router :refer [url-for]]
   [todo.subs :as subs]))

(defn home-page
  []
  (let [button-count (subscribe [::subs/button-info])] ;; button counter with global state
    (fn []
      [:div
       [:h2 "Button Counter"]
       [:button
        {:on-click (fn [e]
                     (.preventDefault e)
                     (dispatch [:increment-button]))}
        "click me " @button-count]
       [:div
        [:a
         {:href (url-for :todos)}
         "Click me"]]])))

#_(defn registration-form [username]
  (let [local-username (reagent/atom {:username username})]
    (fn [username]
      [:div
       [:input {:type :text :name :local-username
                :value (:username @local-username)
                :on-change #(do (swap! local-username assoc :username (.. % -target -value))
                                (js/console.log %))}]
       [:input {:type :button
                :value "create username"
                :on-click #(dispatch [:new-username @local-username (-> % .-target .-value)])}]
       [:div
        [:h "local atom value: " @local-username]]])))

(defn exercise
  [num-default color-default]
  (let [info (reagent/atom {})]
    (fn [_ _]
      [:<>
       [:span
        {:class "bg-gray-200 mx-2 px-2 py-2"}
        [:input {:type :number
                 :value (:number @info)
                 :on-change #(swap! info assoc :number (.. % -target -value))}]]
       [:span
        {:class "bg-blue-200 mx-2 px-2 py-2"}
        [:input {:type :text
                 :value (:color @info)
                 :on-change #(swap! info assoc :color (.. % -target -value))}]]
       [:h2
        {:style {:color (get @info :color color-default)}}
        (get @info :number num-default)]])))

(defn todos-page
  []
  [:div
   [:h2 "Hello todos"]
   [exercise]
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

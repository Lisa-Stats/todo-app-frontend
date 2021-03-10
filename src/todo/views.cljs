(ns todo.views
  (:require
   [re-frame.core :refer [subscribe]]
   [todo.router :refer [url-for]]))

(defn sign-in []
  [:div.sign-in-container.flex.items-center
   [:div.sign-in-card
    [:div
     [:p.todos-title "TODOS"]
     [:h2.sign-in-account-title "Sign in to your account"]
     [:p {:class "mt-2 text-center text-sm text-gray-600"}]]
    [:form {:class "mt-8 space-y-6", :action "#", :method "POST"}
     [:input {:type "hidden", :name "remember", :value "true"}]
     [:div {:class "rounded-lg shadow-md -space-y-px"}
      [:div
       [:label {:for "username", :class "sr-only"} "username"]
       [:input {:id "text", :name "username", :type "text", :autocomplete "username", :class "appearance-none rounded-none relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 rounded-t-md focus:outline-none focus:border-blue-700 focus:z-10 sm:text-sm", :placeholder "Username"}]]
      [:div
       [:label {:for "password", :class "sr-only"} "Password"]
       [:input {:id "password", :name "password", :type "password", :autocomplete "current-password", :class "appearance-none rounded-none relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 rounded-b-md focus:outline-none focus:border-blue-700 focus:z-10 sm:text-sm" :placeholder "Password"}]]]
     [:div.flex.items-center {:class "justify-between"}
      [:div.flex.items-center
       [:input {:id "remember_me", :name "remember_me", :type "checkbox", :class "h-4 w-4 text-blue-600 focus:ring-blue-500 border-blue-300 rounded"}]
       [:label {:for "remember_me", :class "ml-2 text-sm font-medium text-blue-700"} "Remember me"]]
      [:div
       [:a {:href "#", :class "text-sm font-medium text-blue-700 hover:text-blue-800"} "Forgot your password?"]]]
     [:div
      [:button {:type "submit", :class "group relative w-full flex justify-center py-2 px-4 border border-transparent text-sm font-medium rounded-md text-gray-50 bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500"}
       [:span {:class "absolute left-0 inset-y-0 flex items-center pl-3"}
        [:svg {:class "h-5 w-5 text-indigo-500 group-hover:text-indigo-400", :xmlns "http://www.w3.org/2000/svg", :viewbox "0 0 20 20", :fill "currentColor", :aria-hidden "true"}
         [:path {:fill-rule "evenodd", :d "M5 9V7a5 5 0 0110 0v2a2 2 0 012 2v5a2 2 0 01-2 2H5a2 2 0 01-2-2v-5a2 2 0 012-2zm8-2v2H7V7a3 3 0 016 0z", :clip-rule "evenodd"}]]]"Sign in"]
      [:a.flex {:href "#", :class "justify-center text-sm font-medium text-blue-700 hover:text-blue-800"} "New user?"]]]]])

(defn home-page
  []
  [sign-in])

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

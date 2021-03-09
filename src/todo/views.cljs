(ns todo.views
  (:require
   [re-frame.core :refer [subscribe]]
   [todo.router :refer [url-for]]))

(defn sign-in []
  [:div {:class "min-h-screen flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8 bg-gray-400"}
   [:div {:class "max-w-lg rounded-md shadow-md w-full bg-gray-200 py-12 px-12 space-y-10 text-blue-900"}
    [:div
     [:p {:class "text-4xl text-center tracking-wide leading-3 text-gray-700"} "TODOS"]
     #_[:img {:class "mx-auto h-12 w-auto", :src "https://tailwindui.com/img/logos/workflow-mark-indigo-600.svg", :alt "Todo"}]
     [:h2 {:class "mt-6 text-center text-3xl font-extrabold text-gray-900"} "Sign in to your account"]
     [:p {:class "mt-2 text-center text-sm text-gray-600"}]]
    [:form {:class "mt-8 space-y-6", :action "#", :method "POST"}
     [:input {:type "hidden", :name "remember", :value "true"}]
     [:div {:class "rounded-lg shadow-md -space-y-px"}
      [:div
       [:label {:for "email-address", :class "sr-only"} "Email address"]
       [:input {:id "email-address", :name "email", :type "email", :autocomplete "email", :class "appearance-none rounded-none relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 rounded-t-md focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm", :placeholder "Username"}]]
      [:div
       [:label {:for "password", :class "sr-only"} "Password"]
       [:input {:id "password", :name "password", :type "password", :autocomplete "current-password", :class "appearance-none rounded-none relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 rounded-b-md focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm" :placeholder "Password"}]]]
     [:div {:class "flex items-center justify-between"}
      [:div {:class "flex items-center"}
       [:input {:id "remember_me", :name "remember_me", :type "checkbox", :class "h-4 w-4 text-indigo-600 focus:ring-indigo-500 border-gray-300 rounded"}]
       [:label {:for "remember_me", :class "ml-2 block text-sm text-gray-900"} "Remember me"]]
      [:div {:class "text-sm"}
       [:a {:href "#", :class "font-medium text-indigo-600 hover:text-indigo-500"} "Forgot your password?"]]]
     [:div
      [:button {:type "submit", :class "group relative w-full flex justify-center py-2 px-4 border border-transparent text-sm font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"}
       [:span {:class "absolute left-0 inset-y-0 flex items-center pl-3"}
        [:svg {:class "h-5 w-5 text-indigo-500 group-hover:text-indigo-400", :xmlns "http://www.w3.org/2000/svg", :viewbox "0 0 20 20", :fill "currentColor", :aria-hidden "true"}
         [:path {:fill-rule "evenodd", :d "M5 9V7a5 5 0 0110 0v2a2 2 0 012 2v5a2 2 0 01-2 2H5a2 2 0 01-2-2v-5a2 2 0 012-2zm8-2v2H7V7a3 3 0 016 0z", :clip-rule "evenodd"}]]]"Sign in"]]]]])

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

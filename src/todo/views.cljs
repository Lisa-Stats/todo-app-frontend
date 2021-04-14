(ns todo.views
  (:require
   [clojure.string :as str]
   [reagent.core :as r]
   [re-frame.core :refer [dispatch subscribe]]
   [todo.router :refer [url-for]]))

(defn login-component
  [_credentials]
  (fn [credentials]
    (let [username (:username @credentials)
          uuid     (:password @credentials)]
      (if (and (= uuid "9ae26d66-e86d-48fd-a63d-4965fbac51b0") (= username "lisa"))
        [:a {:href (url-for :todo)}
         [:button {:class "group relative w-full flex justify-center py-2 text-sm font-mediu rounded-md text-gray-50 bg-gradient-to-r from-blue-300 to-blue-500 hover:from-blue-400 hover:to-blue-600"}
          [:span {:class "absolute left-0 inset-y-0 flex items-center pl-3"}
           [:svg {:class "h-5 w-5"}
            [:path {:fill-rule "evenodd", :d "M5 9V7a5 5 0 0110 0v2a2 2 0 012 2v5a2 2 0 01-2 2H5a2 2 0 01-2-2v-5a2 2 0 012-2zm8-2v2H7V7a3 3 0 016 0z", :clip-rule "evenodd"}]]]"Sign in"]]
        [:button {:class "cursor-not-allowed group relative w-full flex justify-center py-2 text-sm font-mediu rounded-md text-gray-50 bg-gradient-to-r from-blue-300 to-blue-500 hover:from-blue-400 hover:to-blue-600"}
         [:span {:class "absolute left-0 inset-y-0 flex items-center pl-3"}
          [:svg {:class "h-5 w-5"}
           [:path {:fill-rule "evenodd", :d "M5 9V7a5 5 0 0110 0v2a2 2 0 012 2v5a2 2 0 01-2 2H5a2 2 0 01-2-2v-5a2 2 0 012-2zm8-2v2H7V7a3 3 0 016 0z", :clip-rule "evenodd"}]]]"Sign in"]))))

(defn form-component []
  (let [credentials (r/atom {:username ""
                             :password "9ae26d66-e86d-48fd-a63d-4965fbac51b0"})]
    (fn []
      [:div {:class "space-y-6"}
       [:div {:class "shadow-md"}
        [:div
         [:input {:id "text"
                  :type "text"
                  :name "username"
                  :value (:username @credentials)
                  :placeholder "Username"
                  :on-change #(swap! credentials assoc :username (.. % -target -value))
                  :class "mb-1 relative block rounded-md w-full px-3 py-2 placeholder-gray-500 text-gray-900 focus:outline-none focus:ring focus:border-blue-400 sm:text-sm"}]]
        [:div
         [:input {:id "password"
                  :type "password"
                  :name "password"
                  :value (:password @credentials)
                  :placeholder "Password"
                  :on-change #(swap! credentials assoc :password (.. % -target -value))
                  :class "relative block w-full px-3 py-2 rounded-md placeholder-gray-500 text-gray-900 focus:outline-none focus:ring focus:border-blue-400 sm:text-sm"}]]]
       [:div {:class "justify-between flex items-center"}
        [:div {:class "flex items-center"}
         [:input {:id "remember_me", :name "remember_me", :type "checkbox", :class "h-4 w-4 text-blue-600"}]
         [:label {:for "remember_me", :class "ml-2 text-sm font-medium text-blue-600"} "Remember me"]]
        [:div
         [:a {:href "#", :class "text-sm font-medium text-blue-600 hover:text-blue-800"} "Forgot your password?"]]]
       [:div
        [login-component credentials]
        [:a {:href "/register", :class "justify-center text-sm font-medium text-blue-600 hover:text-blue-800 flex"} "New user?"]]])))

(defn sign-in []
  [:div {:class "min-h-screen justify-center px-4 sm:px-6 lg:px-8 bg-gradient-to-r from-blue-200 to-blue-500 flex items-center"}
   [:div {:class "max-w-lg rounded-md shadow-md w-full bg-cgray-200 py-14 px-12 space-y-14 text-blue-900"}
    [:div
     [:p {:class "text-4xl text-center font-medium leading-3 text-gray-900"}"TODOS"]
     [:h2 {:class "mt-6 text-center text-3xl font-extrabold text-gray-900"}"Sign in to your account"]]
    [form-component]]])

(defn home-page []
  [sign-in])

(defn register-component []
  (let [credentials (r/atom {:username ""
                             :password ""
                             :email    ""})]
    (fn []
      [:form {:on-submit #(dispatch [:register credentials])
              :class "space-y-8"}
       [:div {:class "shadow-md"}
        [:div
         [:input {:id "text"
                  :type "text"
                  :name "username"
                  :value (:username @credentials)
                  :placeholder "Username"
                  :on-change #(swap! credentials assoc :username (.. % -target -value))
                  :class "mb-2 relative block rounded-md w-full px-3 py-2 placeholder-gray-500 text-gray-900 focus:outline-none focus:ring focus:border-blue-400 sm:text-sm"}]
         [:p (:username @credentials)]]
        [:div
         [:input {:type "password"
                  :value (:password @credentials)
                  :placeholder "Password"
                  :on-change #(swap! credentials assoc :password (.. % -target -value))
                  :class "mb-2 relative block w-full px-3 py-2 rounded-md placeholder-gray-500 text-gray-900 focus:outline-none focus:ring focus:border-blue-400 sm:text-sm"}]]
        [:div
         [:input {:type "email"
                  :value (:email @credentials)
                  :placeholder "Email"
                  :on-change #(swap! credentials assoc :username (.. % -target -value))
                  :class "mb-1 relative block rounded-md w-full px-3 py-2 placeholder-gray-500 text-gray-900 focus:outline-none focus:ring focus:border-blue-400 sm:text-sm"}]]]

       [:div
        [:button {:type "submit"
                  :class "group relative w-full flex justify-center py-2 text-sm font-mediu rounded-md text-gray-50 bg-gradient-to-r from-blue-300 to-blue-500 hover:from-blue-400 hover:to-blue-600"}
         [:span {:class "absolute left-0 inset-y-0 flex items-center pl-3"}
          [:svg {:class "h-5 w-5"}
           [:path {:fill-rule "evenodd", :d "M5 9V7a5 5 0 0110 0v2a2 2 0 012 2v5a2 2 0 01-2 2H5a2 2 0 01-2-2v-5a2 2 0 012-2zm8-2v2H7V7a3 3 0 016 0z", :clip-rule "evenodd"}]]]"Register"]]])))

(defn register-page []
  [:div {:class "min-h-screen justify-center px-4 sm:px-6 lg:px-8 bg-gradient-to-r from-blue-200 to-blue-500 flex items-center"}
   [:div {:class "max-w-lg rounded-md shadow-md w-full bg-cgray-200 py-14 px-12 space-y-14 text-blue-900"}
    [:div
     [:p {:class "text-4xl text-center font-medium leading-3 text-gray-900"}"TODOS"]
     [:h2 {:class "mt-6 text-center text-3xl font-extrabold text-gray-900"}"Create a new account"]]
    [register-component]]])

(defn update-component
  [_todo-info _todo-param _edit-param _editing?]
  (fn [todo-info todo-param-key edit-param editing?]
    (let [todo-param (todo-param-key todo-info)
          id (:todo/todo_id todo-info)]
      (if @editing?
        [:input {:class "table-cell py-2 border-r-2 border-b-2 border-blue-100 bg-blue-50 w-full"
                 :type :text
                 :auto-focus true
                 :placeholder todo-param
                 :value @edit-param
                 :on-change #(reset! edit-param (.. % -target -value))
                 :on-blur #(reset! editing? (not @editing?))
                 :on-key-up
                 (fn [e]
                   (when (= "Enter" (.-key e))
                     (.preventDefault e)
                     (dispatch [:update-todo id todo-param-key @edit-param])
                     (reset! edit-param "")
                     (reset! editing? (not @editing?))))}]
        [:div
         {:class "table-cell px-2 py-2 border-r-2 border-b-2 border-blue-100"
          :on-click (fn [e]
                      (.preventDefault e)
                      (reset! editing? (not @editing?)))}
         todo-param]))))

(defn todo-items
  [_todo-info]
  (let [name-editing? (r/atom false)
        body-editing? (r/atom false)
        edited-name   (r/atom "")
        edited-body   (r/atom "")]
    (fn [todo-info]
      (let [id (:todo/todo_id todo-info)]
        [:<>
         [:div  {:class "table-row"}
          [:div {:class "border-l-2 border-b-2 border-r-2 border-blue-100 table-cell pl-14"} [:input {:type :checkbox}]
           [:button {:class "bg-blue-50 ml-8 px-1 hover:bg-blue-100 table-cell"
                     :on-click #(dispatch [:delete-todo id])} "x"]]
          [update-component todo-info :todo/todo_name edited-name name-editing?]
          [update-component todo-info :todo/todo_body edited-body body-editing?]
          [:div {:class "text-left ml-2 table-cell px-2 py-2 border-b-2 border-blue-100"} (:todo/todo_category todo-info)]]]))))

(defn todo-list [todos]
  (fn [ _todos]
    [:div {:class "table-row-group bg-lblue-300"}
     (for [[id todo-info] todos]
       ^{:key id} [todo-items todo-info])]))

(defn todo-list-category [category todos]
  (fn [_category _todos]
    [:div {:class "table-row-group bg-lblue-300"}
     (for [[id todo-info] todos
           :when (= (:todo/todo_category todo-info) category)]
       ^{:key id} [todo-items todo-info])]))

(defn todo-fns []
  (let [add-name     (r/atom "")
        add-todo     (r/atom "")
        add-category (r/atom "")]
    (fn []
      [:div {:class " shadow-md rounded-lg py-3 bg-gradient-to-r from-lblue-300 to-purple-500 w-4/5 pl-2 flex ml-12 space-x-24 px-2"}
       [:div {:class "text-lg font-medium item-center ml-2"}"Add todo here ------->"
        [:div
         (if (or (str/blank? @add-name) (str/blank? @add-todo))
           [:button {:class " cursor-not-allowed mt-4 bg-blue-200 px-16 py-4 rounded-md shadow-md"} "Click to add"]
           [:button {:on-click #(do (dispatch [:add-todo @add-name @add-todo @add-category])
                                    (reset! add-name     "")
                                    (reset! add-todo     "")
                                    (.preventDefault %))
                     :class "mt-4 bg-indigo-500 px-16 py-4 rounded-md shadow-md"} "Click to add"])]]
       [:div
        [:h3 "Add name (Required)"]
        [:input {:class "border-4 border-blue-100"
                 :type :text
                 :placeholder "name"
                 :value @add-name
                 :on-change #(reset! add-name (.. % -target -value))}]
        [:h3 "Add todo (Required)"]
        [:input {:class "border-4 border-blue-100"
                 :type :text
                 :placeholder "todo"
                 :value @add-todo
                 :on-change #(reset! add-todo (.. % -target -value))}]]
       [:div
        [:h2 "Add category"]
        [:select {:on-change #(reset! add-category (.. % -target -value))
                  :class "bg-blue-100"}
         [:option {:value ""}
          "Please choose a category"]
         [:option {:value "Home"}
          "Home"]
         [:option {:value "Friends"}
          "Friends"]
         [:option {:value "Errands"}
          "Errands"]
         [:option {:value "Other"}
          "Other"]
         [:option {:value "None"}
          "None"]]]])))

(defn todos-page []
  (let [category (subscribe [:current-category])
        todos    (subscribe [:current-todos])]
    (fn []
      [:<>
       [:div.sidebar {:class "bg-gradient-to-b from-indigo-500 to-lblue-300"}
        [:h2 {:class "pt-5 ml-12 text-xl font-bold text-gray-50"} "Todos Categories"]
        [:div {:class "pt-4 ml-20 text-lg font-small block text-gray-50"}
         [:div.sbcat [:div {:on-click #(dispatch [:update-category "All"]) :class "cursor-pointer hover:text-gray-900"} "All"]]
         [:div.sbcat [:div {:on-click #(dispatch [:update-category "Home"]) :class "cursor-pointer hover:text-gray-900"} "Home"]]
         [:div.sbcat [:div {:on-click #(dispatch [:update-category "Friends"]) :class "cursor-pointer hover:text-gray-900"} "Friends"]]
         [:div.sbcat [:div {:on-click #(dispatch [:update-category "Errands"]) :class "cursor-pointer hover:text-gray-900"} "Errands"]]
         [:div.sbcat [:div {:on-click #(dispatch [:update-category "Other"]) :class "cursor-pointer hover:text-gray-900"} "Other"]]]]
       [:div {:class "space-y-2 ml-52 pl-8 mt-24"}
        [:div {:class "h-10 fixed inset-x-0 top-0 bg-gradient-to-r from-indigo-500 to-lblue-300"}
         [:div {:class "text-right pr-56"} "Click here to retrieve todos: "
          [:button {:class "px-4 py-2 bg-blue-200"
                    :on-click #(dispatch [:get-todos])}
           "Enter"]]
         [:div [:h1 {:class "mt-4 ml-60 text-xl font-semibold"}
                "Here are your todos"]]]
        [:div {:class "rounded-md shadow-md overflow-y-auto mx-6 w-100 table table-fixed w-fixed"}
         [:div {:class "table-row"}
          [:div.tabletitle {:class "bg-purple-500 table-cell w-40 text-left rounded-tl-md"} "Completed"]
          [:div.tabletitle {:class "bg-purple-500 table-cell w-64 text-center"} "Name"]
          [:div.tabletitle {:class "bg-purple-500 table-cell w-96 text-center"} "Todo"]
          [:div.tabletitle {:class "bg-purple-500 table-cell w-36 text-center rounded-tr-md"} "Category"]]
         (if (= @category "All")
           [todo-list @todos]
           [todo-list-category @category @todos])]
        [:footer {:class "text-xs ml-72"}
         [:p "Click to update | | | Click X to delete"]]]
       [:div {:class "ml-52 pl-8 pt-8"}
        [todo-fns]]])))

(defn pages
  [page-name]
  (case page-name
    :home     [home-page]
    :register [register-page]
    :todo     [todos-page]))

(defn todo-app
  []
  (let [active-page @(subscribe [:active-page])]
    [:<>
     [pages active-page]]))

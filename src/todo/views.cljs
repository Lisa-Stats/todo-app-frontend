(ns todo.views
  (:require
   [reagent.core :as r]
   [re-frame.core :refer [dispatch subscribe]]
   [todo.router :refer [url-for]]))

(defn sign-in []
  [:div {:class "min-h-screen justify-center px-4 sm:px-6 lg:px-8 bg-gradient-to-r from-blue-200 to-blue-500 flex items-center"}
   [:div {:class "max-w-lg rounded-md shadow-md w-full bg-cgray-200 py-14 px-12 space-y-14 text-blue-900"}
    [:div
     [:p {:class "text-4xl text-center font-medium leading-3 text-gray-900"}"TODOS"]
     [:h2 {:class "mt-6 text-center text-3xl font-extrabold text-gray-900"}"Sign in to your account"]]
    [:form {:class "mt-8 space-y-8", :action "#", :method "POST"}
     [:input {:type "hidden", :name "remember", :value "true"}]
     [:div {:class "rounded-lg shadow-md"}
      [:div
       [:label {:for "username", :class "sr-only"} "username"]
       [:input {:id "text", :name "username", :type "text", :autoComplete "username", :class "mb-px appearance-none rounded-sm relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 focus:outline-none focus:border-blue-400 focus:z-10 sm:text-sm", :placeholder "Username"}]]
      [:div
       [:label {:for "password", :class "sr-only"} "Password"]
       [:input {:id "password", :name "password", :type "password", :autoComplete "current-password", :class "appearance-none relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 rounded-sm focus:outline-none focus:border-blue-400 focus:z-10 sm:text-sm" :placeholder "Password"}]]]
     [:div {:class "justify-between flex items-center"}
      [:div {:class "flex items-center"}
       [:input {:id "remember_me", :name "remember_me", :type "checkbox", :class "h-4 w-4 text-blue-600 focus:ring-blue-400 border-blue-300 rounded"}]
       [:label {:for "remember_me", :class "ml-2 text-sm font-medium text-blue-600"} "Remember me"]]
      [:div
       [:a {:href "#", :class "text-sm font-medium text-blue-600 hover:text-blue-800"} "Forgot your password?"]]]
     [:div
      [:button {:type "submit", :class "group relative w-full flex justify-center py-2 px-4 border border-transparent text-sm font-medium rounded-md text-gray-50 bg-gradient-to-r from-blue-300 to-blue-500 hover:from-blue-400 hover:to-blue-600 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500"}
       [:span {:class "absolute left-0 inset-y-0 flex items-center pl-3"}
        [:svg {:class "h-5 w-5 text-indigo-500 group-hover:text-indigo-400", :xmlns "http://www.w3.org/2000/svg", :viewBox "0 0 20 20", :fill "currentColor", :aria-hidden "true"}
         [:path {:fill-rule "evenodd", :d "M5 9V7a5 5 0 0110 0v2a2 2 0 012 2v5a2 2 0 01-2 2H5a2 2 0 01-2-2v-5a2 2 0 012-2zm8-2v2H7V7a3 3 0 016 0z", :clip-rule "evenodd"}]]]"Sign in"]
      [:a {:href "#", :class "justify-center text-sm font-medium text-blue-600 hover:text-blue-800 flex"} "New user?"]]]]])

(defn home-page
  []
  [sign-in])

(defn input-component
  [_todo-info _todo-param _edit-param _editing?]
  (fn [todo-info todo-param-key edit-param editing?]
    (let [todo-param (todo-param-key todo-info)
          id (:todo/todo_id todo-info)]
      (if @editing?
        [:input {:class "table-cell px-2 py-2 border-r-2 border-b-2 border-blue-100 bg-blue-50"
                 :type :text
                 :placeholder todo-param
                 :value @edit-param
                 :on-change #(reset! edit-param (.. % -target -value))
                 :on-key-up
                 (fn [e]
                   (when (= "Enter" (.-key e))
                     (.preventDefault e)
                     (dispatch [:update-todo-param todo-param-key id])
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
          [:div {:class "border-l-2 border-b-2 border-r-2 border-blue-100 table-cell px-20"} [:input {:type :checkbox}]]
          [input-component todo-info :todo/todo_name edited-name name-editing?]
          [input-component todo-info :todo/todo_body edited-body body-editing?]
          [:div
           [:button {:class "bg-blue-50 px-1 hover:bg-blue-100 table-cell"
                     :on-click #(dispatch [:delete id])} "x"]]]]))))



(defn todo-list []
  (let [todos (subscribe [:current-todos])]
    (fn []
      [:div {:class "table-row-group"}
       (doall (for [[id todo-info] @todos]
               ^{:key id} [todo-items todo-info]))])))

(defn todo-fns []
  (let [add-name (r/atom "")
        add-todo (r/atom "")]
    (fn []
      [:<>
       [:div
        [:h2 "'Sign in' button: "
         [:button {:class "px-1 py-1 bg-blue-100"
                   :on-click #(dispatch [:get-todos])}
          "Get todos"]]]
       [:div
        [:h3 "Add todo name"]
        [:input {:class "border-4 border-blue-100"
                 :type :text
                 :value @add-name
                 :on-change #(reset! add-name (.. % -target -value))
                 :on-key-up
                 (fn [e]
                   (when (= "Enter" (.-key e))
                     (dispatch [:add-todo @add-name @add-todo])
                     (reset! add-name "")
                     (reset! add-todo "")
                     (.preventDefault e)))}]
        [:h3 "Add todo"]
        [:input {:class "border-4 border-blue-100"
                 :type :text
                 :value @add-todo
                 :on-change #(reset! add-todo (.. % -target -value))
                 :on-key-up
                 (fn [e]
                   (when (= "Enter" (.-key e))
                     (dispatch [:add-todo @add-name @add-todo])
                     (reset! add-name "")
                     (reset! add-todo "")
                     (.preventDefault e)))}]]]))
  )

(defn todos-page
  []
  (fn []
    [:<>
     [:div.sidebar
      [:h2 {:class "pt-6 ml-12 text-xl font-bold text-gray-50"} "Todos Categories"]
      [:div {:class "pt-4 ml-20 text-lg font-small block text-gray-50"}
       [:div.sbcat [:a {:href "#", :class "hover:text-gray-900"} "All"]]
       [:div.sbcat [:a {:href "#", :class "hover:text-gray-900"} "Home"]]
       [:div.sbcat [:a {:href "#", :class "hover:text-gray-900"} "Family"]]
       [:div.sbcat [:a {:href "#", :class "hover:text-gray-900"} "Friends"]]
       [:div.sbcat [:a {:href "#", :class "hover:text-gray-900"} "Lists"]]
       [:div.sbcat [:a {:href "#", :class "hover:text-gray-900"} "Errands"]]
       [:div.sbcat [:a {:href "#", :class "hover:text-gray-900"} "Other"]]]]
     [:div.main
      [:div [:h1.mtitle
             "Here are your todos"]]
      [:div {:class "rounded-md shadow-md overflow-y-auto mx-6 w-100 table table-fixed"}
       [:div {:class "table-row"}
        [:div.tabletitle {:class "table-cell w-40 text-center rounded-tl-md"} "Completed"]
        [:div.tabletitle {:class "table-cell w-60 text-center"} "Todo Name"]
        [:div.tabletitle {:class "table-cell w-96 text-center rounded-tr-md"} "Todo"]]
       [todo-list]]]
     [:div {:class "ml-52 pl-8 pt-8"}
      [todo-fns]]]))

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

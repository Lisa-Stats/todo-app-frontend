(ns todo.views
  (:require
   [reagent.core :as r]
   [re-frame.core :refer [dispatch subscribe]]
   [todo.router :refer [url-for]]
   [todo.subs :as subs]))

(defn sign-in []
  [:div {:class "min-h-screen justify-center py-12 px-4 sm:px-6 lg:px-8 bg-cgray-400 flex items-center"}
   [:div {:class "max-w-lg rounded-md shadow-md w-full bg-cgray-200 py-12 px-12 space-y-10 text-blue-900"}
    [:div
     [:p {:class "text-4xl text-center font-medium leading-3 text-gray-900"}"TODOS"]
     [:h2 {:class "mt-6 text-center text-3xl font-extrabold text-gray-900"}"Sign in to your account"]
     [:p {:class "mt-2 text-center text-sm text-gray-600"}]]
    [:form {:class "mt-8 space-y-6", :action "#", :method "POST"}
     [:input {:type "hidden", :name "remember", :value "true"}]
     [:div {:class "rounded-lg shadow-md -space-y-px"}
      [:div
       [:label {:for "username", :class "sr-only"} "username"]
       [:input {:id "text", :name "username", :type "text", :autoComplete "username", :class "appearance-none rounded-none relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 rounded-t-md focus:outline-none focus:border-blue-700 focus:z-10 sm:text-sm", :placeholder "Username"}]]
      [:div
       [:label {:for "password", :class "sr-only"} "Password"]
       [:input {:id "password", :name "password", :type "password", :autoComplete "current-password", :class "appearance-none rounded-none relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 rounded-b-md focus:outline-none focus:border-blue-700 focus:z-10 sm:text-sm" :placeholder "Password"}]]]
     [:div {:class "justify-between flex items-center"}
      [:div {:class "flex items-center"}
       [:input {:id "remember_me", :name "remember_me", :type "checkbox", :class "h-4 w-4 text-blue-600 focus:ring-blue-500 border-blue-300 rounded"}]
       [:label {:for "remember_me", :class "ml-2 text-sm font-medium text-blue-700"} "Remember me"]]
      [:div
       [:a {:href "#", :class "text-sm font-medium text-blue-700 hover:text-blue-800"} "Forgot your password?"]]]
     [:div
      [:button {:type "submit", :class "group relative w-full flex justify-center py-2 px-4 border border-transparent text-sm font-medium rounded-md text-gray-50 bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500"}
       [:span {:class "absolute left-0 inset-y-0 flex items-center pl-3"}
        [:svg {:class "h-5 w-5 text-indigo-500 group-hover:text-indigo-400", :xmlns "http://www.w3.org/2000/svg", :viewBox "0 0 20 20", :fill "currentColor", :aria-hidden "true"}
         [:path {:fill-rule "evenodd", :d "M5 9V7a5 5 0 0110 0v2a2 2 0 012 2v5a2 2 0 01-2 2H5a2 2 0 01-2-2v-5a2 2 0 012-2zm8-2v2H7V7a3 3 0 016 0z", :clip-rule "evenodd"}]]]"Sign in"]
      [:a {:href "#", :class "justify-center text-sm font-medium text-blue-700 hover:text-blue-800 flex"} "New user?"]]]]])

(defn home-page
  []
  [sign-in])

#_(comment
  [:div.todos-container
 {:class "ml-15"}
 ([:div.todo
   {:class "my-4"}
   (if @name-input-mode?
     [:div.todo-name-input
      [:input
       {:class "my-1"
        :placeholder "Change todo name"}]
      [:button
       {:class "ml-3 text-sm"
        :on-double-click #(reset! name-input-mode? (not @name-input-mode?))}
       "Confirm"]]
     [:div.todo-name
      {:class "text-xl my-1"}
      [:span name]
      [:button
       {:class "ml-3 text-sm"
        :on-click (fn [e]
                    (.preventDefault e)
                    (reset! name-input-mode? (not @name-input-mode?)))}
       "Pencil"]])
   [:div.todo-body
    body]])])

(defn app []
  (let [todos (subscribe [:current-todos])]
    (apply concat (for [todo @todos
                        :let [id (:id todo)
                              name (:name todo)
                              body (:body todo)
                              done (:done todo)]]
                    ^{:key id}
                    [[:thead
                      [:tr
                       [:th [:div.tabletitle {:class "w-40"} "Completed"]]
                       [:th [:div.tabletitle {:class "w-60"} "Todo Name"]]
                       [:th [:div.tabletitle {:class "w-96"}"Todo"]]]]
                     [:tbody
                      [:tr
                       [:div {:class "table-cell px-16 py-4"}
                        [:input {:type :checkbox :checked done
                                 :on-change #(dispatch [:toggle id])}]]
                       [:div {:class "text-md px-1"}[:td name]]
                       [:div {:class "text-md px-1"}][:td body]
                       [:div {:class "bg-blue-100 hover:bg-blue-200"} [:button {:on-click #(dispatch [:delete id])} "Delete"]]]]]))))

(defn todo-item []
  (let [edit? (r/atom false)]
    (fn [{:keys [id name body done]}]
      [:div
       [:input {:type :checkbox :checked done
                :on-change #(dispatch [:toggle id])}]
       [:label {:on-double-click #(reset! edit? true)} name]
       [:label {:on-double-click #(reset! edit? true)} body]
       [:button {:on-click #(dispatch [id :delete])}]]
      (if @edit?
        [:input {:type :text
                 :value (:name @edit?)
                 :on-change #(swap! edit? assoc :name (.. % -target -value))
                 :on-save #(do (dispatch [:save-name])
                               (reset! edit? false))}]
        (:name edit?)))))

(defn todos-page
  []
  (let [todos (subscribe [:current-todos])]
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
        [:div {:class "shadow-md overflow-y-auto mx-6 table table-fixed w-3/4"}
         [:div {:class "mx-8 border shadow-sm"}
          (app)]]]])))

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

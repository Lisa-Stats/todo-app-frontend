(ns todo.events
  (:require
   [ajax.core :as ajax]
   [day8.re-frame.http-fx]
   [re-frame.core :refer [path reg-event-db reg-event-fx]]
   [todo.db :refer [app-db]]))

(reg-event-fx
 :initialize-db
 (fn [_ _]
   {:db app-db}))

(reg-event-fx
 :set-active-page
 (fn [{:keys [db]} [_ {:keys [page]}]]
   (let [set-page (assoc db :active-page page)]
     (case page
       :home {:db set-page}
       :todos {:db set-page}))))

(defn- get-todos
  [url]
  {:method          :get
   :uri             url
   :format          (ajax/json-request-format)
   :response-format (ajax/json-response-format {:keywords? true})
   :on-success      [:save-todos]
   :on-failure      [:api-error]})

(reg-event-fx
 :get-todos
 (fn [{:keys [_db]} _]
   (let [url "http://localhost:8890/todo/ce485732-a12a-4587-96da-ab91ebbbc07a"]
     {:http-xhrio (get-todos url)})))

(defn map-todo-id [todo]
  (into (sorted-map) (map (juxt :todo/todo_id identity) todo)))

(reg-event-db
 :save-todos
 (fn [db [_ response]]
   (assoc-in db [:todos] (map-todo-id response))))

(reg-event-db
 :api-error
 (fn [db [_ response]]
   (assoc-in db [:error :todos] response)))

(def todo-interceptors (path :todos))

(defn add-next-id [todos]
  ((fnil inc 0) (last (keys todos))))

(reg-event-db
 :add-todo
 todo-interceptors
 (fn [todos [_ name body]]
   (let [id (add-next-id todos)]
     (assoc todos id {:todo/todo_id id :todo/todo_name name :todo/todo_body body}))))

(reg-event-db
 :delete
 todo-interceptors
 (fn [todos [_ id]]
   (dissoc todos id)))

(ns todo.events
  (:require
   [ajax.core :as ajax]
   [day8.re-frame.http-fx]
   [re-frame.core :refer [after inject-cofx path reg-event-db reg-event-fx]]
   [todo.db :refer [app-db todos->local-store]]))

(def ->local-store (after todos->local-store))

(def path-interceptor (path :todos))

(def todo-interceptors [(path :todos)
                        ->local-store])

(reg-event-fx
 :initialize-db
 [(inject-cofx :local-store-todos)]
 (fn [{:keys [_db local-store-todos]} _]
   {:db (assoc app-db :todos local-store-todos)}))

(reg-event-fx
 :set-active-page
 (fn [{:keys [db]} [_ {:keys [page]}]]
   (let [set-page (assoc db :active-page page)]
     (case page
       :home     {:db set-page}
       :todo     {:db set-page}))))

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
   (let [url "http://localhost:8890/todo/86427671-df71-4278-9b7a-dfd587416611
"]
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

(defn- add-todo
  [url name body category]
  {:method          :post
   :uri             url
   :params          {:todo-name name :todo-body body :todo-category category}
   :format          (ajax/json-request-format)
   :response-format (ajax/json-response-format {:keywords? true})
   :on-success      [:add-todo-to-app-db]
   :on-failure      [:api-error]})

(reg-event-fx
 :add-todo
 (fn [{:keys [_db]} [_ name body category]]
   (let [url "http://localhost:8890/todo/86427671-df71-4278-9b7a-dfd587416611"]
     {:http-xhrio (add-todo url name body category)})))

(reg-event-db
 :add-todo-to-app-db
 todo-interceptors
 (fn [todos [_ response]]
   (let [id (:todo/todo_id response)]
     (assoc todos id response))))

(reg-event-db
 :update-category
 (fn [db [_ todo-cat]]
   (assoc db :category todo-cat)))

(defn- delete-todo
  [url todo-id]
  {:method          :delete
   :uri             (str url todo-id)
   :format          (ajax/json-request-format)
   :response-format (ajax/json-response-format {:keywords? true})
   :on-success      [:delete-todo-from-app-db todo-id]
   :on-failure      [:api-error]})

(reg-event-fx
 :delete-todo
 (fn [{:keys [_db]} [_ todo-id]]
   (let [url "http://localhost:8890/todo/86427671-df71-4278-9b7a-dfd587416611/"]
     {:http-xhrio (delete-todo url todo-id)})))

(reg-event-db
 :delete-todo-from-app-db
 todo-interceptors
 (fn [todos [_ todo-id _response]]
   (dissoc todos todo-id)))

(defn- update-todo
  [url todo-id todo-param-key edit-param]
  {:method          :put
   :uri             (str url todo-id)
   :params          {todo-param-key edit-param}
   :format          (ajax/json-request-format)
   :response-format (ajax/json-response-format {:keywords? true})
   :on-success      [:update-todo-to-app-db todo-id todo-param-key edit-param]
   :on-failure      [:api-error]})

(reg-event-fx
 :update-todo
 (fn [{:keys [_db]} [_ todo-id todo-param-key edit-param]]
   (let [url "http://localhost:8890/todo/86427671-df71-4278-9b7a-dfd587416611/"]
     {:http-xhrio (update-todo url todo-id todo-param-key edit-param)})))

(reg-event-db
 :update-todo-to-app-db
 todo-interceptors
 (fn [todos [_ todo-id todo-param-key edit-param _response]]
   (assoc-in todos [todo-id todo-param-key] edit-param)))

(reg-event-db
 :toggle-done
 path-interceptor
 (fn [todos [_ todo-id]]
   (update-in todos [todo-id :done] not)))

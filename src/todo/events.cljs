(ns todo.events
  (:require
   [ajax.core :as ajax]
   [day8.re-frame.http-fx]
   [re-frame.core :refer [reg-event-db reg-event-fx]]
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

(reg-event-fx
 :increment-button-two
 (fn [{:keys [db]} _]
   {:db (update-in db [:button-info] inc)}))

(reg-event-db
 :increment-button
 (fn [db _]
   (update-in db [:button-info] inc)))

(reg-event-db
 :change-first-todo
 (fn [db _]
   (update-in db [:todos 0] (fn [todo-first]
                              (assoc todo-first :name "grocery")))))

(reg-event-fx
 :delete
 (fn [{:keys [db]} [_ {:keys [_id]}]]
   {:db (update-in db [:todos 2] (fn [id]
                                   (dissoc db :todos :id id)))}))

(defn- get-users
  [url]
  {:method          :get
   :uri             url
   :format          (ajax/json-request-format)
   :response-format (ajax/json-response-format {:keywords? true})
   :on-success      [:save-users]
   :on-failure      [:api-error]})

(reg-event-fx
 :get-users
 (fn [{:keys [_db]} _]
   (let [url "http://localhost:8890/users"]
     {:http-xhrio (get-users url)})))

(reg-event-db
 :save-users
 (fn [db [_ response]]
   (assoc-in db [:users] response)))

(reg-event-db
 :api-error
 (fn [db [_ response]]
   (assoc-in db [:error :users] response)))

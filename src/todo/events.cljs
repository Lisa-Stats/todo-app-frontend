(ns todo.events
  (:require
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
 :toggle
 (fn [{:keys [db]} [_ {:keys [id]}]]
   {:db (update-in db [id :done] not)}))

(reg-event-fx
 :save-name
 (fn [{:keys [db]} [_ {:keys [id name]}]]
   {:db (update-in db [id :name] name)}))

(reg-event-fx
 :delete
 (fn [{:keys [db]} [_ {:keys [id]}]]
   {:db (update-in db [:todos 2] (fn [id]
                                (dissoc db :todos :id id)))}))

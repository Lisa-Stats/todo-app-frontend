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

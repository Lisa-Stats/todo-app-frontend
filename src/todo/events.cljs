(ns todo.events
  (:require
   [re-frame.core :refer [reg-event-fx]]
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

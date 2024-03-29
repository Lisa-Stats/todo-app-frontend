(ns todo.subs
  (:require
   [re-frame.core :refer [reg-sub]]))

(reg-sub
 :active-page
 (fn [db _]
   (:active-page db)))

(reg-sub
 :current-users
 (fn [db _]
   (:users db)))

(reg-sub
 :current-todos
 (fn [db _]
   (:todos db)))

(reg-sub
 :current-category
 (fn [db _]
   (:category db)))

(reg-sub
 :total-todos
 :<- [:current-todos]
 (fn [todos _]
   (count todos)))

(reg-sub
 :completed
 :<- [:current-todos]
 (fn [todos _]
   (count (filter :done (vals todos)))))

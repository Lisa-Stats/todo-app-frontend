(ns todo.subs
  (:require
   [re-frame.core :refer [reg-sub]]))

(reg-sub
 :active-page
 (fn [db _]
   (:active-page db)))

(reg-sub
 ::user-info
 (fn [db _]
   (:user-info db)))
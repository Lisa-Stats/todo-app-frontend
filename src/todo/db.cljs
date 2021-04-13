(ns todo.db
  (:require [cljs.reader]
            [re-frame.core :as r]))

(def app-db
  {:active-page :home
   :category "All"
   :users ""})

(def ls-key "todos-reframe")

(defn todos->local-store
  [todos]
  (.setItem js/localStorage ls-key (str todos)))

(r/reg-cofx
 :local-store-todos
 (fn [cofx _]
   (assoc cofx :local-store-todos
          (into (sorted-map)
                (some->> (.getItem js/localStorage ls-key)
                         (cljs.reader/read-string))))))

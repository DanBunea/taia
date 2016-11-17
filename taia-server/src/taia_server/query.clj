
(ns taia-server.query
  (:require
    [taia-server.database]
    [taia-server.utils :as u]
    [korma.core :as k :refer [defentity select as-sql exec get-rel add-joins select* fields table has-many belongs-to with join]]
    [korma.sql.fns :as sfns]
    ))


(defn list-to-map [coll k]
  (->> coll
       (map (fn [el] [(k el) el]))
       (into {})))



(declare users items userstoitems userstousers markets)





(defentity items
  (has-many userstoitems {:fk :item_id}))
(defentity users
  (has-many userstousers {:fk :user_id})
  (has-many userstoitems  {:fk :user_id})
  )

(defentity userstoitems
   (table "UsersToItems")
  (belongs-to users {:fk :user_id})
  (belongs-to items {:fk :item_id}))
(defentity userstousers
  (table "UsersToUsers")
  (belongs-to users))
(defentity markets)



(def entities
  {:users users
   :userstoitems userstoitems
   :userstousers userstousers
   :items items
   })


(defn left-join [query entity related-entity]
  (join query :left related-entity
        (let [rel (get-rel entity related-entity)]
          (sfns/pred-= (:pk rel) (:fk rel)))))


(defn acc-query [acc path value]
  (let [related-entity (-> path drop-last last)
        entity (last path)
        property value]
    (-> acc
        ;;add fields
        (update :fields conj (str entity "." property))
        (update-in [:q] #(fields % (str (name entity) "." property)))
        ;;add joins
        ((fn add-join-if-needed [state]
           (if (and (not  (nil? related-entity))
                    (nil? (some #(= % [related-entity entity]) (:joins state))))

             (-> state
               (update :joins #(into #{} (conj %  [related-entity entity])))
               (update :q #(left-join % ((keyword related-entity) entities) ((keyword entity) entities))))
             state)))
        )))



(defn create-query [state]
  (-> state
  (assoc  :query (u/accumulate-map acc-query
                                        {:main-entity :users
                                         :q (select* users)
                                         } [] {:users (-> state :json :find)}))
  ))


(defn execute-query [state]
  (-> state
    (assoc :data (exec (get-in state [:query :q])))
      (update-in [:query :q] as-sql)
  ))




(defn transform-results [state]
  (-> state
      (update
        :data
        (fn transform-rows [rows]
          rows
          (u/transform (map #(into [] (vals %)) rows) {:users (get-in state [:json :find])})
          )
        )

      ))



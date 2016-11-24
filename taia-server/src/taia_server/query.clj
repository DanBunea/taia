
(ns taia-server.query
  (:require
    [taia-server.database]
    [taia-server.utils :as u]
    [korma.core :as k :refer [defentity where select as-sql exec get-rel add-joins select* fields table has-many belongs-to with join]]
    [korma.sql.fns :as sfns]
    [korma.sql.engine :as ke]

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


;;WHERE - expressions

(defn expression [agg & values]
  (apply (resolve (get ke/predicates (symbol agg))) values))

(defn expressions [wh]
  (reduce
    (fn [c [property value]]
      (if (not (nil? (some #{:and :or} [property])))
        (conj c (apply (partial expression (name property)) (expressions value)))

        ;;else
        (conj c (cond
                  (and (coll? value) (= 2 (count value)))
                  (expression (first value) property (last value))

                  :else
                  (expression "=" property value)
                  ))))
    []
    wh))

(defn map-to-expressions [value]
  (apply (partial expression "and") (expressions value)))

;;WHERE - expressions-end





(defn create-query [state]
  (-> state
      (assoc  :query (u/accumulate-map
                       acc-query
                       {
                         :q (select* ((keyword (:type state)) entities))
                         }
                       []
                       {:users (-> state :json :find)}))
      (update-in [:query :q] #(where % (map-to-expressions (-> state :json :where))))
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



(ns taia.model
  (:require
  [reagent.core :as r :refer [atom]]
  ))

(def data
  (let [x (.createDefaultTestData js/window)]
    (js->clj x :keywordize-keys true)))



(defonce model
  (r/atom
    {
      :lookups {:markets {}}

      :market {
                :id 1
                :name "apps"
                :relations {:following {}
                            :recommendations {}}}
      :user {
              :id 1
              }

      :current-item
      {
        :id 1
        :following
        {
          :11 {:id 11
               :name "Alina"}
          :12 {:id 12
               :name "Anca"}}
        :recommendations
        {
          :110 {:id 110
                :name "Flipboard"}
          }}

      :data data}))


(defn move [state from to]
  (-> state
      (assoc to (from state))
      (dissoc from)))

(defn copy-in [state from to]
  (-> state
      (assoc-in to (get-in state from))
      ))

(defn replace-id-with-item [item state relation]
  (update item
          relation
          #(reduce
             (fn [acc id]
                 (assoc acc (keyword id) (-> state
                              (get-in [:data (keyword id)])
                              (select-keys [:_id :name])
                              (move :_id :id)
                              ))
                 )
             {}
             %
             )))

(defn load-from-data [state id]
  (-> state
      :data
      id
      (select-keys  [:_id :name :recommendations :following])
      (replace-id-with-item state :following)
      (replace-id-with-item state :recommendations)
      (move :_id :id)
      )
  )



(defn jsmodel []
  (clj->js @model))

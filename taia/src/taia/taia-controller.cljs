(ns taia.controller
  (:require
    [reagent.core :as reagent :refer [atom]]
    [taia.model :as m :refer [model]]
  ))


(defn swapm! [x y]
  (swap! y (fn [xx] x)))



(defn load-user [id]
  )

(defn load-recommendation [id]
  )


(defn init []
  (-> @model
      (assoc :current-item (m/load-from-data @model :Dan))
      (swapm! model)))

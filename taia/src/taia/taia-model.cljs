(ns taia.model
  )

(def data
  (let [x (.createDefaultTestData js/window)]
    (js->clj x :keywordize-keys true)))



(defonce model (atom {:data data}))



(defn jsmodel []
  (clj->js model))

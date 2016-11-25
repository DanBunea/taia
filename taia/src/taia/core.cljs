(ns taia.core
  (:require
    [reagent.core :as reagent :refer [atom]]
    [taia.model :as m :refer [model]]
    [taia.views :as views]
    ))

(enable-console-print!)

(println "This text is printed from src/taia/core.cljs. Go ahead and edit it and see reloading in action.")

;; define your app data so that it doesn't get over-written on reload

(defonce app-state (atom {:text "Hello world!"}))

(defn application []
  [views/application-component @model])

(reagent/render-component [application]
                          (. js/document (getElementById "app")))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)

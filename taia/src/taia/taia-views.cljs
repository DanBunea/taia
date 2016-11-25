(ns taia.views
  (:require
    [reagent.core :as reagent :refer [atom]]
    [taia.model :as m :refer [model]])
  )


(defn app-component [app-name state]
  [:div.tapSmall.iPadApps
   {
     :id app-name
     :data (str app-name ",iPadApps")}

   [:div
    {:style {
              :border-radius "20px"
              :box-shadow "0px 4px 8px black"
              :position "absolute"
              :left "0px"
              :top "0px"
              :width "100px"
              :height "100px"
              :background-image "url(http://taiaserver.taia76.com/downloads/min/iPad306550020.png)"
              :background-position  "center center"
              :background-size "cover"}}]
   [:img#lnkDeleteAlina.deleteButton
    {:data "Alina,followingForiPadApps"
     :src "images/icon_delete.png"
     :style {:display "none"}}]

   [:div
    {:style {

              :position "absolute"
              :left "0px"
              :top "0px"
              :width "100%"
              :height "16px"
              :overflow "hidden"
              :padding "5px"
              :font-size "14px"
              :font-weight "bold"
              :color "rgba(102,102,102,1)"
              :text-align "center"
              :vertical-align "bottom"}}]

   ]

  )


(defn follower-component [user-name state]
  [:div.tapSmall.followingForiPadApps
   {
     :id user-name
     :data (str user-name ",followingForiPadApps")
     :style {
              :background-image "url('/images/Alina.jpg')"
              :background-position "center center" :background-size "cover"
              :box-shadow "0px 4px 8px black"
              }}

   [:img#lnkDeleteAlina.deleteButton
    {:data "Alina,followingForiPadApps"
     :src "images/icon_delete.png"
     :style {:display "none"}}]
   [:div.bottom ]
   [:div.bottomText "Alina Bunea"]


   ]
  )

(defn page-component [params state]
  [:div#relListfollowingForiPadApps1.relationPage
   [(:component params) "Alina" state]
   [(:component params) "Anca" state]
   ])




(defn relation-component [params state]
      [:div#followingForiPadApps1.relation.Related.fadeout.followingForiPadApps
       {
        :data "followingForiPadApps"
        :style {:width "360px" :height "460px"
                :transform (:transform params)
                :opacity 0.1875
                :transition-duration "800ms"}}
       [:div#relationTitlefollowingForiPadApps1.relationTitle
        {:data "followingForiPadApps"}
        (:title params)]
       [:div#relationTitle.help
        {
          :style {
                   :position "absolute"
                   :left "-20px"
                   :top "-40px"
                   }}
        "Tap title to edit"]
       [page-component {:component (:component params)}  state]
       ]
  )


(defn user-component [state]
     [:div#view.view
      {:style {
                :-webkit-transform "translate3d(-585px, -185px, 0px)"
                }}
      [:div#middlePad.middle.fadeout
       {:style {
                 :width "350px"
                 :height "455px"
                 :transform "translate3d(410px, -50px, 0px)"
                 :box-shadow "rgb(102, 102, 102) 0px 20px 40px"
                 :background-image "url(http://taiaserver.taia76.com/downloads/Dan.jpg?id=Fri Nov 25 2016 13:48:12 GMT+0200 (EET))"
                 :background-position "center center"
                 :background-size "cover"
                 :opacity 1
                 :background-color "black"
                 :padding "0px"
                 }}

       ]


      [relation-component
       {
         :transform "translate3d(900px, -80px, -500px)"
         :title "Favourite apps"
         :component app-component}
       state]
      [relation-component
       {
         :transform "translate3d(-85px, -80px, -400px)"
         :title "Friends2"
         :component follower-component}
       state]
      ])



(defn application-component [state]
  [:div#page.page.view
   [:div#origin.origin.view
    [:div#camera.camera.view
     {:style {
               :opacity 1
               :transform "scale(1) translateX(0px) translateY(0px) translateZ(-200px) rotateX(0deg) rotateZ(0deg)"
               :transition-duration "3s"
               :transition-property "opacity -webkit-transform"
               :transition-timing-function "ease-out"}}
      [user-component state]

     ]]
   "screen"]
  )

(ns taia.views
  (:require
    [reagent.core :as reagent :refer [atom]]
    [taia.model :as m :refer [model]])
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
     :src="images/icon_delete.png"
     :style {:display "none"}}]
   [:div.bottom ]
   [:div.bottomText "Alina Bunea"]


   ]
  )

(defn followers-page-component [state]
  [:div#relListfollowingForiPadApps1.relationPage
   [follower-component "Alina" state]
   [follower-component "Anca" state]
   ])


(defn followers-component [state]
      [:div#followingForiPadApps1.relation.Related.fadeout.followingForiPadApps
       {
        :data "followingForiPadApps"
        :style {:width "360px" :height "460px" :transform "translate3d(-85px, -80px, -400px)" :opacity 0.1875
                :transition-duration "800ms"}}
       [:div#relationTitlefollowingForiPadApps1.relationTitle
        {:data "followingForiPadApps"}
        "Friends"]
       [:div#relationTitle.help
        {
          :style {
                   :position "absolute"
                   :left "-20px"
                   :top "-40px"
                   }}
        "Tap title to edit"]
       [followers-page-component state]
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

      [:div#iPadApps2.relation.Related.fadeout.iPadApps
       {
        :data "iPadApps"
        :style {:width "360px" :height "460px" :transform "translate3d(900px, -80px, -500px)" :opacity 0.1875
                :transition-duration "800ms"}}
       [:div#relationTitleiPadApps1.relationTitle
        {:data "iPadApps"}
        "Favourite apps"]
       ]

      [followers-component state]


;; <div class="relation Related fadeout followingForiPadApps" id="followingForiPadApps1" data="followingForiPadApps" style="width: 360px; height: 460px; transform: translate3d(-85px, -80px, -400px); opacity: 1; transition-duration: 500ms;">   <div id="relationTitlefollowingForiPadApps1" data="followingForiPadApps" class="relationTitle" onclick="controller.edit('followingForiPadApps');return false;">    Friends   </div>

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

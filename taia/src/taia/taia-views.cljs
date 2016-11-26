(ns taia.views
  (:require
    [reagent.core :as r :refer [atom]]
    [taia.model :as m :refer [model]])
  )

;;fns
(defn get-camera []
  (.getElementById js/document "camera"))

(defn move-camera
  ([x y]
   (move-camera x y 1))
  ([x y zoom]
  (let [camera (get-camera)
        style (.-style camera)
        ]
    (set! (.-transform style)(str "scale(" zoom ") translateX("  0  "px) translateY(" 0 "px) translateZ("  200 "px) rotateX(" x "deg) rotateY(" y "deg)"))
    )))

;;components
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
              :vertical-align "bottom"}}
    app-name
    ]

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
   [:div.bottomText user-name]


   ]
  )

(defn page-component [params index page state]
  [:div.relationPage
   {:id (str "relListfollowingForiPadApps" index)
    }
   (for [related-item page]
     ^{:key (str (:id related-item) "_" (:type related-item) "_" (:name related-item))}
     [(:component params) (:name related-item) state])
   ])




(defn relation-page-component [params page state]
  [:div#followingForiPadApps1.relation.Related.fadeout.followingForiPadApps
   {
     :data "followingForiPadApps"
     :style {:width "360px" :height "460px"
             :transform (str
                          "translate3d(" (:transform-x params) "px,"
                          " " (:transform-y params) "px,"
                          " " (:transform-z params) "px)"
                          )
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
   [:div.relationPage
    {:id (str "relListfollowingForiPadApps" (:index params))
     }
    (for [related-item page]
      ^{:key (str (:id related-item) "_" (:type related-item) "_" (:name related-item))}
      [(:component params) (:name related-item) state])
    ]
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

   (let [relations (-> state :market :relations keys)]
     (for [relation-name relations]
       (let [relation (get-in state [:current-item relation-name])
             pages (partition-all 9 (-> relation vals))
             params (merge
                      {:title (:name relation)
                       :transform-x -85
                       :transform-y -80
                       :transform-z -400}
                      (condp = relation-name
                        :followers  {
                                      :component follower-component}

                        :recommendations  {
                                            :transform-x 900 ;;"translate3d(900px, -80px, -500px)"
                                            :transform-z -500
                                            :component app-component}
                        ))
             ]
         (map-indexed
           (fn [index page]
             ^{:key (str index (:title params) (name relation-name))}
             [relation-page-component
              (-> params
                  (assoc :transform-z (+ (* -100 index)(:transform-z params)))
                  (assoc :index index)
                  )
              page
              state])
           pages)

         )))])





(defn application-component [state]
  (r/create-class {
                    :reagent-render (fn [state]

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
                    :component-did-mount (fn [this] )})
  )






;; //CAMERA

;; ///when the camera is dragged

;; TaiaView.prototype.screenDrag = function (dx, dy) {



;;     //iPad
;;     this.model.camera.angleY = dx  / this.model.camera.pixelsPerDeg;
;;     this.model.camera.angleX = -dy  /this.model.camera.pixelsPerDeg;

;;     //iPhone

;;     // this.model.camera.x = dx;

;;     // this.model.camera.y = dy;

;; 	this.moveCamera(this.model);

;; }



;; ///when zooming in

;; TaiaView.prototype.screenZoom = function (dx, dy) {
;;     this.model.camera.scale = dx;
;;     this.moveCamera(controller.taiaModel);

;; }



;; ///move the camera

;; TaiaView.prototype.moveCamera = function (model) {
;;     this.jQcamera.css("opacity", "" + model.camera.opacity)
;;     .css("-webkit-transform", "scale("+model.camera.scale+") translateX(" + model.camera.x + "px) translateY(" + model.camera.y + "px) translateZ(" + model.camera.z + "px) rotateX(" + model.camera.angleX + "deg) rotateY(" + model.camera.angleY + "deg)")
;;     //.css("-webkit-transition-duration", "730ms");
;;     .css("-webkit-transition-duration", "450ms");

;; }



;; TaiaView.prototype.animateCameraBack = function (){

;;     var camera = document.getElementById("camera");
;;     camera.style.webkitTransitionProperty = "opacity,-webkit-transform";
;;     camera.style.webkitTransitionDuration = "3s";
;;     this.jQcamera.css("opacity", "1")
;; 	.css("-webkit-transform", "scale(1) translateX(0px) translateY(0px) translateZ(-200px) rotateX(0deg) rotateZ(0deg)")
;;     .css("-webkit-transition-timing-function", "ease-out");


;;     this.model.camera.reset();

;; }



;; TaiaView.prototype.stopCameraAnimations = function (){
;;     var camera = document.getElementById("camera");
;;     camera.style.webkitTransitionProperty = "none";
;;     camera.style.webkitTransitionDuration = "0s";
;; }

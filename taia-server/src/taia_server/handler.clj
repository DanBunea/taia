(ns taia-server.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [ring.middleware.json :as json]
            [ring.util.response :refer [response]]
            [taia-server.query :as q :refer [users items userstoitems userstousers markets map-to-expressions]]
            [taia-server.utils :as u]
            [clojure.walk :refer [keywordize-keys]]
            [korma.core :as k :refer [defentity where where* get-rel exec as-sql select select* join* fields table has-many belongs-to with join]]
            [korma.sql.fns :as kf]
             [com.rpl.specter]
            ))

(defn json-req [request]
  (-> request
      :json-params
      keywordize-keys))




;; (def base (-> (select* users)
;;               (fields :id :name)
;;               ))
;; (def wh {
;;           :id ["=" 10]
;;           :name "dan"
;;           :or {
;;                 :name ["=" "a"]
;;                 :id 11
;;                 :and {
;;                          :name "b"
;;                          :id "a"}
;;                 }
;;           }
;;   )


;; (def a (-> base
;;            (where (map-to-expressions wh))
;; ;;            (where (expression
;; ;;                     "or"
;; ;;                       (expression "=" :id 10)
;; ;;                       (expression "=" :id 11)
;; ;;                       (expression "=" :name "dan")
;; ;;                       ))

;;            (as-sql)
;;                   ))

;; /api/1/query/users
;; {
;; 	"find":[
;; 		"id",
;; 		"name",
;; 		{"userstoitems":["item_id","market_id",{"items":["id","name","image"]}]},
;; 		{"userstousers":["follower_id"]}
;; 		],
;; "where":{

;;           "name":"dan",
;;           "or":{
;;                 "name": ["=", "a"],
;;                 "id":11,
;;                  "and": {
;;                          "name":"b" ,
;;                          "id":"a"}
;;                 }
;;           }
;; }



(defroutes app-routes
  (POST "/api/1/query/:type"
        request
        (-> {}
            (assoc :type (-> request :params :type))
            (assoc :json (json-req request))
            q/create-query
            q/execute-query
            q/transform-results
;;             (assoc :test a)
;;               response
                          (#(response (select-keys % [:data] )))
              ))
  (POST "/echo"
        request
        (response (json-req request)))
  (route/not-found "Not Found"))

;; (def app
;;   (wrap-defaults app-routes site-defaults))

(def app
  (-> (handler/api app-routes)
      (json/wrap-json-params {:keywords? true :bigdecimals? true})
      (json/wrap-json-response)))

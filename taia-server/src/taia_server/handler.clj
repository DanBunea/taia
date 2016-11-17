(ns taia-server.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [ring.middleware.json :as json]
            [ring.util.response :refer [response]]
            [taia-server.query :as q :refer [users items userstoitems userstousers markets]]
            [taia-server.utils :as u]
            [clojure.walk :refer [keywordize-keys]]
            [korma.core :as k :refer [defentity get-rel exec as-sql select select* join* fields table has-many belongs-to with join]]
            [com.rpl.specter]
            ))

(defn json-req [request]
  (-> request
      :json-params
      keywordize-keys))







(defroutes app-routes
  (POST "/api/1/query/:type"
        request
        (-> {}
            (assoc :type (-> request :params :type))
            (assoc :json (json-req request))
            q/create-query
            q/execute-query
            q/transform-results
            response
            ;;             (#(response (select-keys [:data] %)))
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

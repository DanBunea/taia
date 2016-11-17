(ns taia-server.database
  (:require [korma.db :as korma]))

(def db-connection-info (korma/mysql
  {:classname "com.mysql.jdbc.Driver"
   :subprotocol "mysql"
   :user "root"
   :subname "//localhost:3306/taia"}))

; set up korma
(korma/defdb db db-connection-info)

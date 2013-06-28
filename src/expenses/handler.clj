(ns expenses.handler
  (:use compojure.core
        cheshire.core
        ring.util.request
        [org.httpkit.server :only [run-server]] 
        ring.util.response
        ring.middleware.json)
  (:require [compojure.handler :as handler]
            [clojure.java.jdbc :as sql]
            [compojure.route :as route]))

(def db {:classname "com.mysql.jdbc.Driver"
         :subprotocol "mysql"
         :subname "//localhost:3306/expenses"
         :user "expenses"
         :password "expenses"})

(defn create "doc-string" [expense]
  (prn expense)
  (sql/with-connection
    db
    (let [exp (assoc expense :id nil :date nil)]
      (sql/insert-record :expenses exp))))



(defroutes app-routes
           (GET "/" [] "Hello World")
           (GET "/expenses" [] 
                (sql/with-connection 
                  db
                  (sql/with-query-results rows
                                          ["select * from expenses"]
                                          (generate-string rows))))
           (POST "/expenses" request (create (parse-string (body-string request) true))) 
           (route/resources "/")
           (route/not-found "Not Found"))
(defn -main [& args]
  (run-server app-routes {:port 8080}))

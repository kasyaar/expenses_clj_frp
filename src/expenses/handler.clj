(ns expenses.handler
  (:gen-class)
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

(defn get-expense [id]
  (sql/with-connection 
    db
    (sql/with-query-results 
      results
      ["select * from expenses where id = ?" id]
      (cond
        (empty? results) {:status 404}
        :else (response (generate-string (first results)))))))
(defn create-new-expense "doc-string" [expense]
  (sql/with-connection
    db
    (let [exp (assoc expense :id nil :date nil)]
      (let [id (:generated_key (sql/insert-record :expenses exp))]
        (get-expense id)))))



(defn delete-expense [id]
    (sql/with-connection db
      (sql/delete-rows :expenses ["id=?" id]))
    {:status 204})

(defn update-expense [id expense]
        (sql/with-connection db
          (let [exp (assoc expense :id id :date nil)]
            (sql/update-values :expenses ["id=?" id] exp)))
        (get-expense id))

(defroutes app-routes
           ; (GET "/" [] "Hello World")
           (context "/expenses" []
                    (defroutes expenses-routes
                               (GET "/" [] (sql/with-connection 
                                             db
                                             (sql/with-query-results rows
                                                                     ["select * from expenses"]
                                                                     (generate-string rows))))
                               (POST "/" request (create-new-expense (parse-string (body-string request) true)))
                               (context "/:id" [id] (defroutes expense-routes
                                                               (GET "/" [] (get-expense id))
                                                               (DELETE "/" [] (delete-expense id))
                                                               (PUT "/" request (update-expense id (parse-string (body-string request) true)))))))
           (route/files "/")
           (route/not-found "Not Found"))

(defn -main [& args]
  (run-server app-routes {:port 8080}))

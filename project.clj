(defproject expenses "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [http-kit "2.0.0-RC4"]
                 [cheshire, "5.2.0"]
                 [org.clojure/java.jdbc "0.3.0-alpha4"]
                 [ring "1.2.0-RC1"]
                 [ring/ring-json "0.2.0"]
                 [mysql/mysql-connector-java "5.1.6"]
                 [compojure "1.1.5"]]
  :main expenses.handler
  :aot [expenses.handler]
  :plugins [[lein-ring "0.8.5"]]
  )

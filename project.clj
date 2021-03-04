(defproject clobs "0.1.0-SNAPSHOT"
  :description "Clobs: Clojure Bookmarks Service"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [ring "1.7.1"]
                 [compojure "1.6.1"]
                 [ring/ring-json "0.5.0"]
                 [ring-cors "0.1.13"]
                 [seancorfield/next.jdbc "1.1.547"]
                 [mysql/mysql-connector-java "5.1.44"]
                 [buddy/buddy-auth "2.2.0"]
                 [buddy/buddy-hashers "1.4.0"]
                 [enlive              "1.1.6"]
                 [http-kit "2.5.3"]
                 [org.clojure/core.match "1.0.0"]
                 [com.draines/postal "2.0.3"]]
  :repl-options {:init-ns clobs.core}
  :profiles {:uberjar {:aot :all}}
  :main clobs.core)

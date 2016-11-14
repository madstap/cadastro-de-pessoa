(defproject cadastro-de-pessoa "0.3.1-SNAPSHOT"
  :description "Some utils for working with cpfs and cnpjs."
  :url "http://github.com/madstap/cadastro-de-pessoa"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.293"]]
  :profiles
  {:dev {:dependencies [[com.cemerick/piggieback "0.2.1"]
                        [org.clojure/tools.nrepl "0.2.10"]]
         :repl-options {:nrepl-middleware
                        [cemerick.piggieback/wrap-cljs-repl]}}}
  :plugins [[lein-cljsbuild "1.1.4"]]
  :cljsbuild {:builds [{:source-paths ["src"]
                        :compiler {:output-to "resources/main.js"}}]})

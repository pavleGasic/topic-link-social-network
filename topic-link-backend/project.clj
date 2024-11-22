(defproject topic-link-backend "0.1.0-SNAPSHOT"
  :description "A social networking platform for topic-centric discussions and multimedia sharing"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [ring/ring-defaults "0.5.0"]
                 [ring/ring-jetty-adapter "1.12.2"]
                 [com.taoensso/timbre "6.0.4"]
                 [metosin/ring-http-response "0.9.4"]
                 [metosin/reitit "0.7.0"]
                 [metosin/reitit-http "0.7.0"]
                 [metosin/muuntaja "0.6.10"]
                 [ring-cors/ring-cors "0.1.13"]
                 [buddy/buddy-core "1.11.423"]
                 [buddy/buddy-auth "3.0.323"]
                 [buddy/buddy-sign "3.5.351"]
                 [com.novemberain/monger "3.6.0"]
                 [buddy/buddy-hashers "2.0.167"]]
  :main ^:skip-aot topic-link-backend.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})

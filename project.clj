(defproject opentype-clj "0.1.0"
  :description "Clojure (JVM) wrapper for opentype.js"
  :url "https://github.com/ivarref/opentype.clj"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/core.async "0.3.442"]
                 [org.mozilla/rhino "1.7.7.1"]
                 [base64-clj "0.1.1"]
                 [venantius/pyro "0.1.2"]
                 [byte-streams "0.2.4-alpha4"]]
  :profiles {:dev {:resource-paths ["test-resources"]}})

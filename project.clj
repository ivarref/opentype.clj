(defproject opentype-clj "0.0.6-SNAPSHOT"
  :description "Clojure (JVM) wrapper for opentype.js"
  :url "https://github.com/ivarref/opentype.clj"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/core.async "0.4.474"]
                 [org.mozilla/rhino "1.7.9"]
                 [base64-clj "0.1.1"]
                 [org.apache.xmlgraphics/batik-rasterizer "1.9.1" :scope "test"]
                 [org.apache.xmlgraphics/batik-codec "1.9.1" :scope "test"]]
  :profiles {:dev {:resource-paths ["test-resources"]}})

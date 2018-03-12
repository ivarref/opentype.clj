(ns opentype-clj.bootstrap
  (:require [clojure.java.io :as io]
            [opentype-clj.utils :refer [filepath->stream]])
  (:import [java.io File]
           [org.mozilla.javascript Context]))

(defn- with-resource->temp-file [resource f]
  "Writes `resource` to a temporary file.
  Calls f with absolute path to the temporary file.
  After f completes, the temporary file is deleted.
  Throws exception if the resource is not found."
  (let [tempfile (File/createTempFile "opentype-clj-temp-" ".js")]
    (try
      (if-let [is (filepath->stream resource)]
        (do (try
              (io/copy is tempfile)
              (finally
                (.close is)))
            (f (.getAbsolutePath tempfile)))
        (throw (ex-info "Resource not found" {:resource resource})))
      (finally
        (.delete tempfile)))))

(defn rhino []
  (let [context (doto (Context/enter)
                  (.setLanguageVersion Context/VERSION_ES6))
        scope (.initStandardObjects context)
        eval-str (fn [s] (.evaluateString context scope s "<cmd>" 1 nil))]
    (eval-str (slurp (filepath->stream "jvm-npm.js")))
    (with-resource->temp-file "opentype.js" #(eval-str (str "var opentype = require('" % "')")))
    (with-resource->temp-file "base64-arraybuffer.js" #(eval-str (str "var b64 = require('" % "')")))
    (eval-str "function parseFont(payload) { return opentype.parse(b64.decode(payload)); }")
    {:context   context
     :scope     scope
     :parsefont (.get scope "parseFont")}))
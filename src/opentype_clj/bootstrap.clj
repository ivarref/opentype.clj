(ns opentype-clj.bootstrap
  (:require [clojure.java.io :as io]
            [opentype-clj.thread :refer [same-thread]]
            [opentype-clj.utils :refer [filepath->stream]]
            [base64-clj.core :as base64]
            [byte-streams :as bs])
  (:import [java.io File]
           [org.mozilla.javascript Context NativeObject ScriptableObject InterpretedFunction]))

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

(defn- rhino-fn []
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

(defonce ^:private rhino (same-thread rhino-fn))

(defn- ^InterpretedFunction get-fn
  [^ScriptableObject obj ^String fn-name]
  (NativeObject/getProperty obj fn-name))

(defn call [obj ^String fn-name args]
  (.call (get-fn obj fn-name)
         (:context rhino)
         (:scope rhino)
         obj
         (object-array args)))

(defn load-font
  "Loads the font given by `filepath`.
  `filepath` may be either a classpath resource or a file on disk.

  Returns the font, or nil if `filepath` was not found."
  [filepath]
  (same-thread
    (fn []
      (when-let [stream (filepath->stream filepath)]
        (let [font-b64 (-> stream
                           (bs/to-byte-array)
                           (base64/encode-bytes)
                           (String.))
              ^ScriptableObject font (.call (:parsefont rhino) (:context rhino) (:scope rhino) (:scope rhino) (object-array [font-b64]))
              names (NativeObject/getProperty font "names")
              fullnames (vals (get names "fullName"))]
          ;(println "font-properties:" (vec (sort (NativeObject/getPropertyIds font))))
          {:name         (first (sort fullnames))
           :units-per-em (NativeObject/getProperty font "unitsPerEm")
           :ascender     (NativeObject/getProperty font "ascender")
           :descender    (NativeObject/getProperty font "descender")
           :font-obj     (fn [] font)})))))

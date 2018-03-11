(ns opentype-clj.core
  (:require [base64-clj.core :as base64]
            [opentype-clj.thread :refer [same-thread]]
            [byte-streams :as bs]
            [clojure.java.io :as io])
  (:import [org.mozilla.javascript Context NativeObject]
           [java.io BufferedInputStream]
           [clojure.lang RT]))

(defn- filepath->stream
  "Returns a buffered stream of filepath, either using classpath or file from disk.
  Returns nil if filepath cannot be found."
  [filepath]
  (some-> (or (.getResourceAsStream (RT/baseLoader) filepath)
              (let [file (io/file filepath)]
                (when (.exists file)
                  (io/input-stream file))))
          (BufferedInputStream.)))

(defonce ^:private rhino
         (same-thread
           (fn []
             (let [context (doto (Context/enter)
                             (.setLanguageVersion Context/VERSION_ES6))
                   scope (.initStandardObjects context)
                   eval-str (fn [s] (.evaluateString context scope s "<cmd>" 1 nil))]
               (eval-str (slurp (filepath->stream "jvm-npm.js")))
               (eval-str "var opentype = require('./resources/opentype.js')") ; TODO how to deal with this when classpath resource?
               (eval-str "var b64 = require('./resources/base64-arraybuffer.js')")
               (eval-str "function parseFont(payload) { return opentype.parse(b64.decode(payload)); }")
               {:context   context
                :scope     scope
                :parsefont (.get scope "parseFont")}))))

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
              font (.call (:parsefont rhino) (:context rhino) (:scope rhino) (:scope rhino) (object-array [font-b64]))
              names (NativeObject/getProperty font "names")
              fullnames (vals (get names "fullName"))]
          ;(println "font-properties:" (vec (sort (NativeObject/getPropertyIds font))))
          {:name       (first (sort fullnames))
           :unitsPerEm (NativeObject/getProperty font "unitsPerEm")
           :ascender   (NativeObject/getProperty font "ascender")
           :descender  (NativeObject/getProperty font "descender")
           :font-obj   (fn [] font)})))))

(defn get-path
  "Get path of `text` for `font` at `x`, `y` (baseline) with font `size`."
  [{:keys [font-obj]} text x y size]
  (assert (fn? font-obj) "Missing font")
  (same-thread
    #(.call (NativeObject/getProperty (font-obj) "getPath")
            (:context rhino)
            (:scope rhino)
            (font-obj)
            (object-array [text x y size]))))

(defn- sample-font []
  (load-font "fonts/Roboto-Black.ttf"))

(defn sample-get-path []
  (-> (sample-font)
      (get-path "Hello, World!" 0 150 72)))

(defn- demo []
  (spit "demo.svg" (str "<svg width=\"400\" height=\"400\" xmlns=\"http://www.w3.org/2000/svg\">\n"
                        "<path transform=\"translate(0, 0)\" fill=\"black\" stroke=\"black\" d=\""
                        (get-path (load-font "fonts/Roboto-Black.ttf")
                                  "Hello, World!"
                                  0
                                  150
                                  72)
                        "\" />\n"
                        "</svg>")))
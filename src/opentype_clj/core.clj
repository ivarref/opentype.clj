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
          (with-meta {:fullname (first fullnames)
                      :filepath filepath}
                     {::font font}))))))

(defn get-path
  "Get path of `text` for `font` at `x`, `y` (baseline) with font `size`."
  [font text x y size]
  (let [font (::font (meta font))]
    (assert (some? font) "Missing font")
    (same-thread
      (fn []
        (let [get-path (NativeObject/getProperty font "getPath")
              path (.call get-path (:context rhino) (:scope rhino) font (object-array [text x y size]))
              to-path-data-fn (NativeObject/getProperty path "toPathData")
              path-data (.call to-path-data-fn (:context rhino) (:scope rhino) path (object-array []))]
          path-data)))))

(defn- demo []
  (get-path (load-font "fonts/Roboto-Black.ttf")
            "Hello World"
            0
            150
            72))
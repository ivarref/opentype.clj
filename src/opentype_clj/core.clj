(ns opentype-clj.core
  (:require [base64-clj.core :as base64]
            [opentype-clj.thread :refer [same-thread]]
            [byte-streams :as bs]
            [clojure.java.io :as io])
  (:import [org.mozilla.javascript Context NativeObject InterpretedFunction ScriptableObject]
           [java.io BufferedInputStream File]
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

(defonce ^:private rhino
         (same-thread
           (fn []
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
              ^ScriptableObject font (.call (:parsefont rhino) (:context rhino) (:scope rhino) (:scope rhino) (object-array [font-b64]))
              names (NativeObject/getProperty font "names")
              fullnames (vals (get names "fullName"))]
          ;(println "font-properties:" (vec (sort (NativeObject/getPropertyIds font))))
          {:name       (first (sort fullnames))
           :unitsPerEm (NativeObject/getProperty font "unitsPerEm")
           :ascender   (NativeObject/getProperty font "ascender")
           :descender  (NativeObject/getProperty font "descender")
           :font-obj   (fn [] font)})))))

(defn- ^InterpretedFunction get-fn
  [^ScriptableObject obj ^String fn-name]
  (NativeObject/getProperty obj fn-name))

(defn call [obj fn-name args]
  (.call (get-fn obj fn-name)
         (:context rhino)
         (:scope rhino)
         obj
         (object-array args)))

(defn get-path
  "Get path of `text` for `font` at `x`, `y` (baseline) with font `size`."
  [{:keys [font-obj]} text x y size]
  (assert (fn? font-obj) "Missing font")
  (same-thread #(call (font-obj) "getPath" [text x y size])))

(defn- glyph->clj
  [font ^ScriptableObject glyph]
  {:name         (NativeObject/getProperty glyph "name")
   :font         font
   :unicode      (NativeObject/getProperty glyph "unicode")
   :unicodes     (vec (NativeObject/getProperty glyph "unicodes"))
   :index        (int (NativeObject/getProperty glyph "index"))
   :advanceWidth (NativeObject/getProperty glyph "advanceWidth")
   :xMin         (NativeObject/getProperty glyph "xMin")
   :yMin         (NativeObject/getProperty glyph "yMin")
   :xMax         (NativeObject/getProperty glyph "xMax")
   :yMax         (NativeObject/getProperty glyph "yMax")
   :path         (NativeObject/getProperty glyph "path")})

(defn string->glyphs
  [{:keys [font-obj] :as font} s]
  (assert (fn? font-obj) "Missing font")
  (same-thread
    #(let [glyphs (call (font-obj) "stringToGlyphs" [s])]
       (mapv (partial glyph->clj font) (seq glyphs)))))

(defn char->glyph
  [font s]
  (first (string->glyphs font s)))

(defn get-advance-width
  "Returns the advance width of a text.

  This is something different than Path.getBoundingBox() as for example a suffixed whitespace increases the advancewidth
  but not the bounding box or an overhanging letter like a calligraphic 'f' might have a quite larger bounding box than
  its advance width.

  This corresponds to canvas2dContext.measureText(text).width

  fontSize: Size of the text in pixels.

  options: Not implemented."
  [{:keys [font-obj]} text font-size]
  (assert (fn? font-obj) "Missing font")
  (same-thread #(call (font-obj) "getAdvanceWidth" [text font-size])))

(defn- sample-font []
  (load-font "fonts/Roboto-Black.ttf"))

(defn sample-get-path []
  (-> (sample-font)
      (get-path "Hello, World!" 0 150 72)))

(defn- demo []
  (spit "demo.svg" (str "<svg width=\"400\" height=\"400\" xmlns=\"http://www.w3.org/2000/svg\">\n"
                        "<path transform=\"translate(0, 0)\" fill=\"black\" stroke=\"black\" d=\""
                        (get-path (sample-font)
                                  "Hello, World!"
                                  0
                                  150
                                  72)
                        "\" />\n"
                        "</svg>")))
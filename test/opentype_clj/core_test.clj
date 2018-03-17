(ns opentype-clj.core-test
  (:require [clojure.test :refer :all]
            [opentype-clj.core :refer :all])
  (:import (org.apache.batik.transcoder.image PNGTranscoder)
           (org.apache.batik.transcoder TranscoderInput TranscoderOutput)
           (java.io StringReader BufferedOutputStream FileOutputStream)
           (java.awt Color)))

(defn write-png [^String s ^String output-filename]
  (let [t (doto (PNGTranscoder.)
            (.addTranscodingHint PNGTranscoder/KEY_PIXEL_TO_MM (Float. 0.2645833)) ; 96 dpi
            (.addTranscodingHint PNGTranscoder/KEY_BACKGROUND_COLOR Color/white))]
    (with-open [input (StringReader. s)
                output (BufferedOutputStream. (FileOutputStream. output-filename))]
      (.transcode t (TranscoderInput. input) (TranscoderOutput. output)))))

(deftest core-test
  (let [svg (str "<svg width='450' height='150' xmlns='http://www.w3.org/2000/svg'>\n"
                 "<path fill='black' stroke='none' d='" (text->path-data "Roboto Black" "Hello, World!" 10 100 72) "' />\n"
                 "</svg>\n")]
    (spit "demo.svg" svg)
    (write-png svg "demo.png")))

(deftest bounding-box-test
  (let [txt (text "Roboto Black" "Hello, World!" 10 100 72)]
    (spit "bounding-box.svg" (str "<svg width='450' height='150' xmlns='http://www.w3.org/2000/svg'>\n"
                                  "<path fill='black' stroke='none' d='" (:path-data txt) "' />\n"
                                  "<path fill='none' stroke='red' d='" (:bounding-box-path-data txt) "' />\n"
                                  "</svg>\n")))
  (write-png (slurp "bounding-box.svg") "bounding-box.png"))
(ns opentype-clj.utils
  (:require [clojure.java.io :as io])
  (:import [clojure.lang RT]
           [java.io BufferedInputStream]))

(defn filepath->stream
  "Returns a buffered stream of filepath, either using classpath or file from disk.
  Returns nil if filepath cannot be found."
  [filepath]
  (when filepath
    (some-> (or (.getResourceAsStream (RT/baseLoader) filepath)
                (let [file (io/file filepath)]
                  (when (.exists file)
                    (io/input-stream file))))
            (BufferedInputStream.))))
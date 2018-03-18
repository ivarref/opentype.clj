(ns opentype-clj.thread
  (:import (java.util.concurrent Executors ExecutorService)))

(defonce ^:private ^ExecutorService worker (Executors/newSingleThreadExecutor))

(defn same-thread [^Callable f]
  "Executes `f` inside a single, fixed thread."
  (.get (.submit worker f)))
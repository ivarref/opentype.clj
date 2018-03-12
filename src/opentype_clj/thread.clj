(ns opentype-clj.thread
  (:require [clojure.core.async :as async]))

(defonce ^:private input (async/chan))
(defonce ^:private exit (async/chan))
(defonce ^:private output (async/chan))
(defonce ^:private exception (async/chan))

(defn- js-loop-body []
  (let [f (async/<!! input)]
    (try
      (let [v (f)]
        (async/>!! output (if (nil? v) ::nil v)))
      (catch Exception e
        (println "error in js-loop ..." (.getMessage e))
        (async/>!! exception e)))))

(defonce ^:private js-looper
         (async/thread
           (while true
             (js-loop-body))))

(defn same-thread [f]
  "Executes `f` inside a single, fixed thread."
  (async/>!! input f)
  (let [[result ch] (async/alts!! [output exception])]
    (cond (= ch exception) (throw result)
          (= ::nil result) nil
          :else result)))

(ns opentype-clj.font-cache
  (:require [opentype-clj.wrapper :as wrapper]
            [opentype-clj.utils :as utils]
            [clojure.string :as str]))

(defn- suffixes
  [font-name]
  (map #(str font-name %) [".ttf" ".woff" ".otf"]))

(defn font-name->candidates
  "Returns the candidate resource names for font name"
  [font-name]
  (->> (flatten [font-name
                 (suffixes font-name)
                 (suffixes (str "fonts/" font-name))
                 (when (str/includes? font-name " ") (font-name->candidates (str/replace font-name " " "-")))])
       (remove nil?)))

(defn font-name->resource-name
  "Returns the resource name for font-name, or nil if not found"
  [font-name]
  (reduce (fn [o candidate] (or o (when-let [stream (utils/filepath->stream candidate)]
                                    (.close stream)
                                    candidate)))
          nil
          (font-name->candidates font-name)))

(def font-name->font
  "Returns the font for font-name, or nil if the font cannot be found.

  The font will be stored in the font-cache if found, avoiding expensive font loading."
  (let [font-cache (atom {})]
    (fn [font-name]
      (if-let [font (get @font-cache font-name)]
        font
        (if-let [font (wrapper/load-font (font-name->resource-name font-name))]
          (do (swap! font-cache (fn [old] (assoc old font-name font)))
              font)
          (do (println "Warning, font" (str "'" font-name "'") "not found")
              nil))))))
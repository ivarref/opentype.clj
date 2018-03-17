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

(defn cached-font-name->font
  "Returns font by font-name from cache, or nil if not found.

  If font-name is not found in the cache, it will be attempted loaded and the cache will be updated.

  This function is only meant to be used internally (and by tests)."
  [cache font-name]
  (assert (string? font-name) "font-name must be string")
  (assert (not-empty font-name) "font-name must be not-empty")
  (if-let [font (get @cache font-name)]
    (with-meta font {:cached true})
    (if-let [font (wrapper/load-font (font-name->resource-name font-name))]
      (do (swap! cache (fn [old] (assoc old font-name font)))
          (with-meta font {:cached false}))
      (do (println "Warning: Font" (str "'" font-name "'") "not found")
          nil))))

(def font-name->font
  "Returns the font for font-name, or nil if the font cannot be found."
  (let [cache (atom {})]
    (fn [font-name] (cached-font-name->font cache font-name))))

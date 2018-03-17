(ns opentype-clj.core
  (:require [opentype-clj.wrapper :as wrapper]
            [opentype-clj.font-cache :as font-cache]
            [clojure.string :as str]))

(defn font-name->font
  "Returns the font for font-name, or nil if the font cannot be found."
  [font-name]
  (font-cache/font-name->font font-name))

(defn- font-name->font-or-throw
  [font-name]
  (if-let [font (font-name->font font-name)]
    font
    (throw (ex-info "Could not find font-name" {:font-name font-name}))))

(defn text
  ([font-name s] (text font-name s 0 0 72 2))
  ([font-name s x] (text font-name s x 0 72 2))
  ([font-name s x y] (text font-name s x y 72 2))
  ([font-name s x y font-size] (text font-name s x y font-size 2))
  ([font-name s x y font-size decimals]
   (let [font (font-name->font-or-throw font-name)
         path (wrapper/get-path font s x y font-size)]
     {:path-data    (wrapper/path->path-data path decimals)
      :bounding-box (wrapper/path->bounding-box path)})))

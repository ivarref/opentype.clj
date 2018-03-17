(ns opentype-clj.core
  (:require [opentype-clj.wrapper :as wrapper]
            [opentype-clj.font-cache :as font-cache]))

(defn font
  "Returns the font for font-name, or nil if the font cannot be found."
  [font-name]
  (font-cache/font-name->font font-name))

(defn- font-name->font-or-throw
  [font-name]
  (if-let [font-obj (font font-name)]
    font-obj
    (throw (ex-info "Could not find font-name" {:font-name font-name}))))

(defn text
  ([font-name txt] (text font-name txt 0 0 72 2))
  ([font-name txt x] (text font-name txt x 0 72 2))
  ([font-name txt x y] (text font-name txt x y 72 2))
  ([font-name txt x y font-size] (text font-name txt x y font-size 2))
  ([font-name txt x y font-size decimals]
   (let [font (font-name->font-or-throw font-name)
         path (wrapper/get-path font txt x y font-size)]
     {:path-data    (wrapper/path->path-data path decimals)
      :bounding-box (wrapper/path->bounding-box path)})))

(defn text->path-data
  ([font-name txt] (text->path-data font-name txt 0 0 72 2))
  ([font-name txt x] (text->path-data font-name txt x 0 72 2))
  ([font-name txt x y] (text->path-data font-name txt x y 72 2))
  ([font-name txt x y font-size] (text->path-data font-name txt x y font-size 2))
  ([font-name txt x y font-size decimals] (:path-data (text font-name txt x y font-size decimals))))
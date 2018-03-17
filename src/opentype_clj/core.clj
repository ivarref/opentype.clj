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
   (assert (int? decimals) "decimals must be of type int")
   (assert (or (zero? decimals) (pos-int? decimals)) "decimals must be greater or equal to zero")
   (let [font (font-name->font-or-throw font-name)
         path (wrapper/get-path font txt x y font-size)
         bounding-box (wrapper/path->bounding-box path)
         fmt (fn [num] (format (str "%." decimals "f ") num))]
     {:path-data    (wrapper/path->path-data path decimals)
      :bounding-box bounding-box
      :bounding-box-path-data (str "M" (fmt (:x1 bounding-box)) (fmt (:y1 bounding-box))
                                   "H" (fmt (:x2 bounding-box))
                                   "V" (fmt (:y2 bounding-box))
                                   "H" (fmt (:x1 bounding-box))
                                   "Z")})))

(defn text->path-data
  ([font-name txt] (text->path-data font-name txt 0 0 72 2))
  ([font-name txt x] (text->path-data font-name txt x 0 72 2))
  ([font-name txt x y] (text->path-data font-name txt x y 72 2))
  ([font-name txt x y font-size] (text->path-data font-name txt x y font-size 2))
  ([font-name txt x y font-size decimals] (:path-data (text font-name txt x y font-size decimals))))

(defn text->bounding-box
  ([font-name txt] (text->bounding-box font-name txt 0 0 72 2))
  ([font-name txt x] (text->bounding-box font-name txt x 0 72 2))
  ([font-name txt x y] (text->bounding-box font-name txt x y 72 2))
  ([font-name txt x y font-size] (text->bounding-box font-name txt x y font-size 2))
  ([font-name txt x y font-size decimals] (:bounding-box (text font-name txt x y font-size decimals))))

(defn text->bounding-box-path-data
  ([font-name txt] (text->bounding-box-path-data font-name txt 0 0 72 2))
  ([font-name txt x] (text->bounding-box-path-data font-name txt x 0 72 2))
  ([font-name txt x y] (text->bounding-box-path-data font-name txt x y 72 2))
  ([font-name txt x y font-size] (text->bounding-box-path-data font-name txt x y font-size 2))
  ([font-name txt x y font-size decimals] (:bounding-box-path-data (text font-name txt x y font-size decimals))))
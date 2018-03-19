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
  "Returns a map containing path-data, bounding-box and advance-width.

  Parameters:
  font-name: The font to use.
  txt: The text to use.
  x: Horizontal position of the beginning of the text. Default: 0.
  y: Vertical position of the baseline of the text. Default: 0.
  font-size: Size of font. Default: 72.
  decimals: Numbers of decimals to use in path data. Default: 2."
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
         advance-width (wrapper/get-advance-width font txt font-size)]
     {:path-data     (wrapper/path->path-data path decimals)
      :bounding-box  bounding-box
      :advance-width advance-width})))

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

(defn bounding-box->path-data
  ([bounding-box] (bounding-box->path-data bounding-box 2))
  ([{:keys [x1 x2 y1 y2]} decimals]
   (assert (number? x1) ":x1 must be number")
   (assert (number? x2) ":x2 must be number")
   (assert (number? y1) ":y1 must be number")
   (assert (number? y2) ":y2 must be number")
   (assert (int? decimals) "decimals must be int")
   (assert (or (zero? decimals) (pos-int? decimals)) "decimals must be greater or equal to zero")
   (let [fmt (fn [num] (format (str "%." decimals "f ") num))]
     (str "M" (fmt x1) (fmt y1)
          "H" (fmt x2)
          "V" (fmt y2)
          "H" (fmt x1)
          "Z"))))

(ns opentype-clj.core
  (:require [opentype-clj.wrapper :as wrapper]
            [opentype-clj.font-cache :as font-cache]
            [clojure.string :as str]))

(defn font-name->font
  "Returns the font for font-name, or nil if the font cannot be found."
  [font-name]
  (font-cache/font-name->font font-name))
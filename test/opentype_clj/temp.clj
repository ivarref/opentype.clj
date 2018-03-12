(ns opentype-clj.temp
  (:require [clojure.test :refer :all]))

;(defn- sample-font []
;  (load-font "fonts/Roboto-Black.ttf"))
;
;(defn sample-glyph [x]
;  (char->glyph (sample-font) x))
;
;(defn- sample-bounding-box []
;  (-> (sample-font)
;      (get-path "Hello, World!" 0 150 72)
;      (path->bounding-box)))
;
;(defn- sample-path []
;  (-> (sample-font)
;      (get-path "Hello, World!" 0 150 72)))
;
;(defn- sample-kerning-value []
;  (let [font (sample-font)]
;    (get-kerning-value font (char->glyph font "T") (char->glyph font "a"))))
;
;(defn- demo []
;  (spit "demo.svg" (str "<svg width=\"400\" height=\"400\" xmlns=\"http://www.w3.org/2000/svg\">\n"
;                        "<path fill=\"black\" stroke=\"none\" d=\""
;                        (-> (sample-font)
;                            (get-path "Hello, World!" 0 150 72)
;                            (path->path-data))
;                        "\" />\n"
;                        "</svg>")))
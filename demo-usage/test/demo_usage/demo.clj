(ns demo-usage.demo
  (:require [clojure.test :refer :all]
            [opentype-clj.core :refer :all]))

(deftest core-test
  (let [svg (str "<svg width='450' height='150' xmlns='http://www.w3.org/2000/svg'>\n"
                 "<path fill='black' stroke='none' d='" (text->path-data "Roboto Regular" "Hello, World!" 10 100 72) "' />\n"
                 "</svg>\n")]
    (spit "demo.svg" svg)))
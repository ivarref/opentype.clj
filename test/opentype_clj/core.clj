(ns opentype-clj.core
  (:require [clojure.test :refer :all]
            [opentype-clj.core :refer :all]))

(deftest core-test
  (spit "demo2.svg" (str "<svg width='500' height='300' xmlns='http://www.w3.org/2000/svg'>\n"
                        "<path fill='black' stroke='none' d='" (text->path-data "Roboto Black" "Hello, World!" 0 150 72) "' />\n"
                        "</svg>\n")))
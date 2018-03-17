(ns opentype-clj.font-cache-test
  (:require [clojure.test :refer :all]
            [opentype-clj.font-cache :refer :all]))

(deftest font-name->candidates-test
  (is (some #{"fonts/Roboto-Black.ttf"} (font-name->candidate-resources "Roboto Black"))))

(deftest font-cache-test
  (let [cache (atom {})]
    (is (some? (cached-font-name->font (atom {}) "Roboto Black")))
    (binding [*print-warning* false]
      (is (nil? (cached-font-name->font (atom {}) "Roboto Blackxxxx"))))
    (is (false? (-> (cached-font-name->font cache "Roboto Black") (meta) :cached)))
    (is (true? (-> (cached-font-name->font cache "Roboto Black") (meta) :cached)))))

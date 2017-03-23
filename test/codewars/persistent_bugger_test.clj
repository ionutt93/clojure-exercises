(ns codewars.persistent-bugger-test
  (:require [clojure.test :refer :all]
            [codewars.persistent-bugger :refer :all]))

(deftest initial-tests
  (testing "Initial Tests"
    (is (= (persistence 39) 3))
    (is (= (persistence 4) 0))
    (is (= (persistence 25) 2))
    (is (= (persistence 999) 4))))
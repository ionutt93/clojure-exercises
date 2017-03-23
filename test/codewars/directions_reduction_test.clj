(ns codewars.directions-reduction-test
  (:require [clojure.test :refer :all]
            [codewars.directions-reduction :refer :all]))

(deftest a-test1
  (testing "Test 1"
    (def ur ["NORTH", "SOUTH", "SOUTH", "EAST", "WEST", "NORTH", "WEST"])
    (def vr ["WEST"])
    (is (= (dirReduc ur) vr))))
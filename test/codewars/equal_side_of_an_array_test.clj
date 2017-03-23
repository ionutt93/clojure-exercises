(ns codewars.equal-side-of-an-array-test
  (:require [clojure.test :refer :all]
            [codewars.equal-side-of-an-array :refer :all]))

(defn test-assert [act exp]
  (is (= act exp)))

(deftest a-test1
  (testing "find-even-index"
    (println "Fixed Tests find-even-index")
    (test-assert(find-even-index [1,2,3,4,3,2,1]), 3); 3
    (test-assert(find-even-index [1,100,50,-51,1,1]), 1); 1
    (test-assert(find-even-index [1,2,3,4,5,6]), -1); -1
    (test-assert(find-even-index [20,10,30,10,10,15,35]), 3))); 3

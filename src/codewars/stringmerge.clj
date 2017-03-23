(ns codewars.stringmerge
  (:require [clojure.string :as s]))

(defn is-merge
  "Returns whether a string is a merge of two other strings"
  [s p1 p2]
  (loop [[s1 & ss] s
         [x1 & xs1 :as p1] p1
         [x2 & xs2 :as p2] p2]
    (cond
      (nil? s1) (and (empty? p1)
                     (empty? p2))
      (= s1 x1 x2) (or (is-merge ss xs1 p2)
                       (is-merge ss p1 xs2))
      (= s1 x1) (recur ss xs1 p2)
      (= s1 x2) (recur ss p1 xs2)
      :else false)))



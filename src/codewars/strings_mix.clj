;Description:
;
;Given two strings s1 and s2, we want to visualize how different the two strings are. We will only take into account
; the lowercase letters (a to z). First let us count the frequency of each lowercase letters in s1 and s2.
;
;s1 = "A aaaa bb c"
;
;s2 = "& aaa bbb c d"
;
;s1 has 4 'a', 2 'b', 1 'c'
;
;s2 has 3 'a', 3 'b', 1 'c', 1 'd'
;
;So the maximum for 'a' in s1 and s2 is 4 from s1; the maximum for 'b' is 3 from s2. In the following we will not
; consider letters when the maximum of their occurrences is less than or equal to 1.
;
;We can resume the differences between s1 and s2 in the following string: "1:aaaa/2:bbb" where 1 in 1:aaaa stands
; for string s1 and aaaa because the maximum for a is 4. In the same manner 2:bbb stands for string s2 and bbb
; because the maximum for b is 3.
;
;The task is to produce a string in which each lowercase letters of s1 or s2 appears as many times as its maximum if
; this maximum is strictly greater than 1; these letters will be prefixed by the number of the string where they
; appear with their maximum value and :. If the maximum is in s1 as well as in s2 the prefix is =:.
;
;In the result, substrings (a substring is for example 2:nnnnn or 1:hhh; it contains the prefix) will be in decreasing
; order of their length and when they have the same length sorted in ascending lexicographic order (letters and digits
; - more precisely sorted by codepoint); the different groups will be separated by '/'. See examples and "Example Tests".
;
;                             Hopefully other examples can make this clearer.
;
;                             s1 = "my&friend&Paul has heavy hats! &"
;                             s2 = "my friend John has many many friends &"
;                             mix(s1, s2) --> "2:nnnnn/1:aaaa/1:hhh/2:mmm/2:yyy/2:dd/2:ff/2:ii/2:rr/=:ee/=:ss"
;
;                             s1 = "mmmmm m nnnnn y&friend&Paul has heavy hats! &"
;                             s2 = "my frie n d Joh n has ma n y ma n y frie n ds n&"
;                             mix(s1, s2) --> "1:mmmmmm/=:nnnnnn/1:aaaa/1:hhh/2:yyy/2:dd/2:ff/2:ii/2:rr/=:ee/=:ss"
;
;                             s1="Are the kids at home? aaaaa fffff"
;                             s2="Yes they are here! aaaaa fffff"
;                             mix(s1, s2) --> "=:aaaaaa/2:eeeee/=:fffff/1:tt/2:rr/=:hh")

(ns codewars.strings-mix
  (:require [clojure.string :as s]))

(def lwrc-alpha #{\a \b \c \d \e \f \g \h \i \j \k \l \m \n \o \p \q \r \s \t \u \v \w \x \y \z})

(defn- filter-non-lwrcase
  [strg]
  (filter #(contains? lwrc-alpha %1)
           strg))

(defn- parse-string
  [strg]
  (frequencies (filter-non-lwrcase strg)))


(defn build-max-freq
  [freq1 freq2]
  (let [all-keys (into #{} (concat (keys freq1) (keys freq2)))
        max-freq (map (fn [key]
                        (let [val1 (get freq1 key 0)
                              val2 (get freq2 key 0)]
                          (cond
                            (> val1 val2) {key {:val val1 :s "1"}}
                            (< val1 val2) {key {:val val2 :s "2"}}
                            :else {key {:val val1 :s "="}})))
                      all-keys)]
    (into {} max-freq)))


(defn merge-freq-maps
  [freq-map-array]
  (reduce (fn [result entry]
            (let [entry-key (:count entry)
                  entry-val (:entry entry)]
              (assoc result entry-key
                            (conj (get result entry-key [])
                                  entry-val))))
          {}
          freq-map-array))

(defn build-max-freq-v2
  [freq1 freq2]
  (let [all-keys (into #{} (concat (keys freq1) (keys freq2)))
        max-freq (map (fn [key]
                        (let [val1 (get freq1 key 0)
                              val2 (get freq2 key 0)]
                          (cond
                            (> val1 val2) {:count val1 :entry {:char key :s "1"}}
                            (< val1 val2) {:count val2 :entry {:char key :s "2"}}
                            :else {:count val1 :entry {:char key :s "="}})))
                      all-keys)]
    (into {} (filter #(> (key %) 1)
                     (merge-freq-maps max-freq)))))


(defn special-string
  [max-freq-map]
  (s/join "/" (map (fn [entry]
                     (let [number (key entry)
                           values (val entry)]
                       (map (fn [e]
                              (str (:s e) ":"
                                   (s/join (repeat number (:char e)))))
                            values)))
                   max-freq-map)))


(defn special-string-v2
  [max-freq-map]
  (let [result (flatten (map (fn [entry]
                               (let [times (key entry)
                                     values (val entry)
                                     strgs (map (fn [{c :char
                                                      owner :s}]
                                                  (str owner ":" (s/join (repeat times c))))
                                                values)]
                                 (into [] strgs)))
                             max-freq-map))]
    (s/join "/" (sort result))))

(defn to-spec-strg
  [owner letter times]
  (str owner ":" (s/join (repeat times letter))))

(defn ->strings-in-order
  [times array-of-maps]
  (sort (map #(to-spec-strg (:s %) (:char %) times)
              array-of-maps)))

(defn process
  [max-freq-map]
  (let [sorted-max-freq-map (into (sorted-map-by >)
                                  max-freq-map)]
    (s/join "/" (mapcat #(->strings-in-order (first %)
                                             (last %))
                        sorted-max-freq-map))))

(defn mix
  [s1 s2]
  (process (build-max-freq-v2 (parse-string s1)
                              (parse-string s2))))



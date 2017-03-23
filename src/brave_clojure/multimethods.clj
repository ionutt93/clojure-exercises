(ns brave-clojure.multimethods)

;(defmulti full-moon-behaviour (fn [were-creature] (:were-type were-creature)))
;(defmethod full-moon-behaviour :wolf
;  [were-creature]
;  (str (:name were-creature) " will howl and murder"))
;(defmethod full-moon-behaviour :simmons
;  [were-creature]
;  (str (:name were-creature) " will encourage people to sweat to the 80s"))

(defprotocol Psychodynamics
  "Plumb the inner depths of your data types"
  (thoughts [x] "The data type's innermost thoughts")
  (feelings-about [x] [x y] "Feelings about self or other"))

(extend-type java.lang.String
  Psychodynamics
  (thoughts [x] (str x " thinks, 'Truly, the character defines the data type'"))
  (feelings-about
    ([x] (str x " is longing for a simpler way of life"))
    ([x y] (str x " is envious of " y "'s simpler way of life"))))

(defprotocol WereCreature
  (full-moon-behaviour [x]))

(defrecord Werewolf [name title]
  WereCreature
  (full-moon-behaviour [x]
    (str name " will howl and murder")))

(println (full-moon-behaviour (map->Werewolf {:name "Lucian"
                                              :title "CEO"})))
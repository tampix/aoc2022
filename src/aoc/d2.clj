(ns aoc.d2
  (:require [aoc.util :as util]))

(def rules
  {"A X" [(+ 1 3) (+ 3 0)]
   "A Y" [(+ 2 6) (+ 1 3)]
   "A Z" [(+ 3 0) (+ 2 6)]
   "B X" [(+ 1 0) (+ 1 0)]
   "B Y" [(+ 2 3) (+ 2 3)]
   "B Z" [(+ 3 6) (+ 3 6)]
   "C X" [(+ 1 6) (+ 2 0)]
   "C Y" [(+ 2 0) (+ 3 3)]
   "C Z" [(+ 3 3) (+ 1 6)]})

(defn day2
  []
  (let [input (util/read-input-lines 2)]
    ;; part1
    (-> (comp (map rules)
              (map first))
        (transduce + input)
        println)
    ;; part2
    (-> (comp (map rules)
              (map second))
        (transduce + input)
        println)))

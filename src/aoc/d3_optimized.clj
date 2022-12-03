(ns aoc.d3-optimized
  (:require [aoc.util :as util]
            [clojure.string :as str]
            [clojure.set :as set]))

(def scores
  (mapv #(cond
           (< % 26)    (+ % 27)
           (< 31 % 58) (- % 31)
           :else       0)
        (range 64)))

(defn score
  [mask]
  (scores (- 64 (Long/numberOfLeadingZeros (dec mask)))))

(defn c->bit
  [c]
  (- (long c) (long \A)))

(defn v->bitmap
  [v]
  (reduce #(bit-or %1 (bit-shift-left 1 %2)) 0 v))

(defn find-duplicate
  [xs]
  (reduce bit-and -1 (eduction (map v->bitmap) xs)))

(defn split-half
  [v]
  (let [end (count v)
        half (quot end 2)]
    [(subvec v 0 half) (subvec v half end)]))

(defn day3
  []
  (let [rucksacks (into []
                        (map (partial mapv c->bit))
                        (util/read-input-lines 3))
        xf (comp (map find-duplicate)
                 (map score))]
    ;; part 1
    (-> (comp (map split-half) xf)
        (transduce + rucksacks)
        println)
    ;; part 2
    (-> (comp (partition-all 3) xf)
        (transduce + rucksacks)
        println)))

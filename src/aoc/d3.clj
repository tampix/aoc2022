(ns aoc.d3
  (:require [aoc.util :as util]
            [clojure.string :as str]
            [clojure.set :as set]))

(def scores
  (mapv #(cond
           (Character/isLowerCase %) (- % (dec (long \a)))
           (Character/isUpperCase %) (- % (- (long \A) 27))
           :else 0)
        (range 128)))

(defn find-duplicate
  [xs]
  (->> (eduction (map set) xs)
       (reduce set/intersection)
       first))

(defn day3
  []
  (let [rucksacks (into []
                        (map (partial mapv long))
                        (util/read-input-lines 3))
        xf (comp (map find-duplicate)
                 (map scores))]
    ;; part 1
    (-> (comp (map #(split-at (quot (count %) 2) %)) xf)
        (transduce + rucksacks)
        println)
    ;; part 2
    (-> (comp (partition-all 3) xf)
        (transduce + rucksacks)
        println)))

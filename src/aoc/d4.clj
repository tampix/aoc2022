(ns aoc.d4
  (:require [aoc.util :as util]
            [clojure.string :as str]))

(defn range-contains
  [[a b c d]]
  (or (<= a c d b)
      (<= c a b d)))

(defn range-overlaps
  [[a b c d]]
  (and (<= a d)
       (<= c b)))

(defn day4
  []
  (let [input (into []
                    (comp (map parse-long)
                          (partition-all 4)
                          (map vec))
                    (str/split (util/read-input 4) #"[-,\n]"))]
    ;; part 1
    (->> input
         (filter range-contains)
         count
         println)
    ;; part 2
    (->> input
         (filter range-overlaps)
         count
         println)))

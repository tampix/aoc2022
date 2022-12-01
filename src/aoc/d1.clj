(ns aoc.d1
  (:require [clojure.string :as str]
            [aoc.util :as util]))

(defn day1
  []
  (let [input    (util/read-input 1)
        calories (->> (str/split input #"\n\n")
                      (map #(->> (str/split-lines %)
                                 (map parse-long)
                                 (reduce +))))]
    ;; part1
    (println (reduce max calories))
    ;; part2
    (->> calories
         (sort #(compare %2 %1))
         (take 3)
         (reduce +)
         println)))

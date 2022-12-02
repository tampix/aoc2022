(ns aoc.d1
  (:require [clojure.string :as str]
            [aoc.util :as util]))

(defn day1
  []
  (let [calories (->> (str/split (util/read-input 1) #"\n\n")
                      (map #(transduce (map parse-long)
                                       +
                                       (str/split-lines %))))]
    ;; part1
    (println (reduce max calories))
    ;; part2
    (-> (comp (util/xf-sort #(compare %2 %1))
              (take 3))
        (transduce + calories)
        println)))

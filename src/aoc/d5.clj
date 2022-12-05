(ns aoc.d5
  (:require [aoc.util :as util]
            [clojure.string :as str]))

(defn- parse-stacks
  [input]
  (into (sorted-map)
        (comp (filter #(Character/isDigit (first %)))
              (map (juxt #(- (long (first %)) (long \0))
                         #(into []
                                (take-while (complement #{\space}))
                                (rest %)))))
        (apply map (comp reverse vector) input)))

(defn- parse-steps
  [input]
  (mapv #(apply hash-map
                (interleave [:move :from :to]
                            (map parse-long (re-seq #"\d+" %))))
        input))

(defn- apply-step
  [stacks {:keys [move from to]}]
  (reduce (fn [res _]
            (let [sfrom (res from)
                  sto   (res to)]
              (if (seq sfrom)
                  (-> res
                      (assoc to (conj sto (peek sfrom)))
                      (assoc from (pop sfrom)))
                  (reduced res))))
          stacks
          (range move)))

(defn- decompose-step
  [{:keys [move to from]}]
  [{:move move :from from :to -1}
   {:move move :from -1 :to to}])

(defn day5
  []
  (let [input  (->> (util/read-input-lines 5)
                    (split-with seq))
        stacks (parse-stacks (input 0))
        steps  (parse-steps (drop 1 (input 1)))]
    ;; part 1
    (->> steps
         (reduce apply-step stacks)
         vals
         (map peek)
         (apply str)
         println)
    ;; part 2
    (->> (eduction (mapcat decompose-step) steps)
         (reduce apply-step stacks)
         vals
         (map peek)
         (apply str)
         println)))

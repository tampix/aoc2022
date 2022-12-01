(ns aoc.util
  (:require [clojure.string :as str]))

(defn read-input
  [day]
  (->> (str "d" day ".txt")
       clojure.java.io/resource
       slurp))

(defn read-input-lines
  [day]
  (str/split-lines (read-input day)))

(defn parse-long
  [s]
  (Long/parseLong s))

(defn read-input-ints
  [day]
  (map parse-long (read-input-lines day)))

(defn find-sum
  "Return any pair of elements from `coll` where the sum of those equals
  `sum`."
  [coll sum]
  (let [elts (set coll)]
    (some #(when-let [complement (elts (- sum %))]
             [% complement])
          coll)))

(defn juxt-reduce
  "Perform the juxtaposition of `fs` as a single reduce on `coll`.

  This is equivalent to (mapv #(reduce % coll) fs)

  (juxt-reduce [min max] (range 100)) => [0 99]
  (juxt-reduce [+ -] [55 -55] (range 10)) => [100 -100])
  (juxt-reduce [+ *] nil) => [0 1]"
  ([fs coll]
   (if-let [[x & xs] (seq coll)]
     (juxt-reduce fs (repeat (count fs) x) xs)
     (mapv #(apply % []) fs)))
  ([fs acc coll]
   (reduce (fn [acc cur] (mapv #(%1 %2 cur) fs acc)) acc coll)))

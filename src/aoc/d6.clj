(ns aoc.d6
  (:require [aoc.util :as util]
            [clojure.string :as str]))

(defn bag-conj
  [bag k]
  (update bag k (fnil inc 0)))

(defn bag-disj
  [bag k]
  (case (bag k)
    nil   bag
    1     (dissoc bag k)
    (update bag k dec)))

(defn find-marker-end
  [^bytes bs len]
  (loop [i   0
         bag {}]
    (if (= len (count bag))
      i
      (recur (inc i)
             (-> bag
                 (bag-conj (aget bs i))
                 (bag-disj (get bs (- i len))))))))

(defn day6
  []
  (let [input (util/read-input-bytes 6)]
    ;; part 1
    (println (find-marker-end input 4))
    ;; part 2
    (println (find-marker-end input 14))))

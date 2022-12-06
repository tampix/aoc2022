(ns aoc.util
  (:require [clojure.string :as str]
            [clojure.java.io :as io])
  (:import java.io.ByteArrayOutputStream
           java.net.URL))

(defn- ^URL input
  [day]
  (io/resource (str "d" day ".txt")))

(defn ^String read-input
  [day]
  (slurp (input day)))

(defn read-input-lines
  [day]
  (str/split-lines (read-input day)))

(defn read-input-ints
  [day]
  (map parse-long (read-input-lines day)))

(defn ^bytes read-input-bytes
  [day]
  (with-open [in  (io/input-stream (input day))
              out (ByteArrayOutputStream.)]
    (io/copy in out)
    (.toByteArray out)))

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

(ns aoc.d7
  (:require [aoc.util :as util]
            [clojure.string :as str]))

(defn- cd
  [state dir]
  (case dir
    "/"  (assoc state ::path '("/"))
    ".." (update state ::path next)
    (update state ::path (partial cons dir))))

(defn- add-file
  [state size]
  (loop [state state
         path  (::path state)]
    (if (seq path)
      (recur (update state (str/join path) #(+ (or % 0) size))
             (next path))
      state)))

(defn parse-line
  ([]
   {})
  ([state]
   (dissoc state ::path))
  ([state line]
   (let [last-space (str/last-index-of line \space)]
     (if (str/starts-with? line "$ cd")
       (->> (inc last-space)
            (subs line)
            (cd state))
       (->> (subs line 0 last-space)
            parse-long
            (add-file state))))))

(defn day7
  []
  (let [fs (-> (remove #(or (str/starts-with? % "$ ls")
                            (str/starts-with? % "dir")))
               (transduce parse-line (util/read-input-lines 7)))]
    ;; part 1
    (-> (remove (partial < 100000))
        (transduce + (vals fs))
        println)
    ;; part 2
    (let [root   (fs "/")
          target (- root (- 70000000 30000000))]
      (-> (remove (partial > target))
          (transduce min root (vals fs))
          println))))

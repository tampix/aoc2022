(ns aoc.core
  (:require [criterium.core :as crit]
            [clojure.string :as str])
  (:gen-class))

(defn- get-day-fn
  [day]
  (try
    (let [ns-sym (symbol (str "aoc.d" day))]
      (when-not (find-ns ns-sym)
        (require ns-sym))
      (ns-resolve ns-sym (symbol (str "day" day))))
    (catch Exception _
      nil)))

(defn- execute-day
  [day-fn]
  (time (day-fn)))

(defn- benchmark-day
  [day-fn]
  (let [lines (->> (day-fn)
                   with-out-str
                   (crit/quick-bench)
                   with-out-str
                   str/split-lines)]
    (doseq [line lines]
      (if (some #(str/includes? line %) ["mean" "std-deviation"])
        (println line)))))

(defn -main
  [& args]
  (let [op-fn (if (contains? (set args) "-b")
                benchmark-day
                execute-day)]
    (doseq [day (range 1 26)]
      (println "Day" day ":")
      (if-let [day-fn (get-day-fn day)]
        (op-fn day-fn)
        (println "Not implemented yet."))
      (println))))

(comment
  (-main)

  )

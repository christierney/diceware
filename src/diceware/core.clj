(ns diceware.core
  (:require [clojure.string :as str])
  (:gen-class))

(defn -main
  "Generates a random passphrase of 5 (or `arg`) words."
  [& args]
  (let [rnd (java.security.SecureRandom.)
        wrds (str/split-lines (slurp "diceware8k.txt"))]
    (dotimes [n 5]
      (println (wrds (.nextInt rnd 8192))))))

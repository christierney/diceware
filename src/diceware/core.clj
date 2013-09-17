(ns diceware.core
  (:require [clojure.string :as string])
  (:use [clojure.tools.cli :only [cli]])
  (:import (java.security SecureRandom))
  (:gen-class))

(def special-chars ["!" "@" "#" "$" "%" "^"
                     "&" "*" "(" ")" "-" "="
                     "+" "[" "]" "{" "}" "\\"
                     "|" "`" ";" ":" "'" "\""
                     "<" ">" "/" "?" "." ","
                     "~" "_"])

(def rnd (SecureRandom.))

(defn- load-wordlist []
  (string/split-lines (slurp "diceware8k.txt")))

(defn- pick-words [n]
  "Generates a vector of n random words."
  (let [words (load-wordlist)]
    (vec (for [_ (range n)]
      (words (.nextInt rnd 8192))))))

(defn- swap-char [s n c]
  "Returns String s with the nth character replaced with c."
  (str (subs s 0 n)
       c
       (subs s (inc n))))

(defn- insert-something [words things]
  "Randomly chooses a word and a character in that word and replaces it with a
  random element from things."
  (let [n (.nextInt rnd (count words))
        word (words n)
        m (.nextInt rnd (count word))
        c (nth things (.nextInt rnd (count things)))]
    (assoc words n (swap-char word m c))))

(defn- insert-special [words]
  "Randomly chooses a word and a character in that word and replaces it with a
  random special character."
  (insert-something words special-chars))

(defn- insert-digit [words]
  "Randomly chooses a word and a character in that word and replaces it with a
  random digit."
  (insert-something words (range 10)))

(defn -main [& args]
  (let [[options args banner]
        (cli args
             "Generates a random passphrase using the diceware wordlist."
             ["-h" "--help" "Show this help message" :flag true :default false]
             ["-w" "--words" "Number of words in the passphrase" :default 5 :parse-fn #(Integer. %)]
             ["-s" "--special-char" "Require a special character" :flag true :default false]
             ["-d" "--digit" "Require a digit" :flag true :default false]
             )]
    (when (:help options)
      (println banner)
      (System/exit 0))
    (println
      (string/join " "
        (let [words (pick-words (:words options))]
          (cond-> words
            (:special-char options) insert-special
            (:digit options) insert-digit))))))

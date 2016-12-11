(ns cadastro-de-pessoa.cpf
  (:refer-clojure :exclude [format])
  (:require
   [cadastro-de-pessoa.shared :as shared]))

(def length 11)

(def control-digits
  (partial shared/control-digits (range 10 1 -1) (range 11 1 -1)))

(def repeated
  "A set of cpfs with repeated digits that are
  considered valid by the algorithm, but normally shouldn't count as valid."
  (conj (set (for [i (range 10)]
               (repeat length i))) [0 1 2 3 4 5 6 7 8 9 0]))

(defn valid?
  "Takes a string, seq of digits or a cpf. Returns true if valid, else false.
  A cpf
  Does not validate formatting."
  ([cpf]
   (let [cpf (shared/parse cpf)
         [digits control] (shared/split-control cpf)]
     (and (= length (count cpf))
          (not (repeated cpf))
          (= control (control-digits digits))))))

(def regex #"^[0-9]{3}\.[0-9]{3}\.[0-9]{3}-[0-9]{2}$")

(defn formatted?
  "Is the cpf formatted correctly?"
  [cpf]
  (boolean
   (and (string? cpf) (re-find regex cpf))))

(defn format
  "Returns a string of the correctly formatted cpf"
  [cpf]
  (shared/format length {3 ".", 6 ".", 9 "-"} cpf))

(def digits shared/digits)

(defn gen
  "Generates a random valid cpf"
  []
  (let [digits (shared/rand-digits (- length 2))]
    (format (concat digits (control-digits digits)))))

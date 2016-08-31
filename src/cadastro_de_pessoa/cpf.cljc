(ns cadastro-de-pessoa.cpf
  (:refer-clojure :exclude [format])
  (:require [cadastro-de-pessoa.shared :as shared :refer [digits #?(:cljs Digits)]])
  #?(:clj (:import [cadastro_de_pessoa.shared Digits])))

(def length 11)

(def control-digits
  (partial shared/control-digits (range 10 1 -1) (range 11 1 -1)))

(def repeated
  "A set of cpfs with repeated digits that are
  considered valid by the algorithm, but normally shouldn't count as valid."
  (conj (set (for [i (range 10)]
               (repeat length i))) [0 1 2 3 4 5 6 7 8 9 0]))

(declare cpf?)

(defn valid?
  "Takes a string, seq of digits or a cpf. Returns true if valid, else false.
  A cpf
  Does not validate formatting."
  ([cpf]
   (if (cpf? cpf)
     true
     (let [cpf (shared/parse cpf)
           [digits control] (shared/split-control cpf)]
       (and (= length (count cpf))
            (not (repeated cpf))
            (= control (control-digits digits)))))))

(defn formatted?
  "Is the cpf formatted correctly?"
  [cpf]
  (boolean
   (re-find #"^[0-9]{3}\.[0-9]{3}\.[0-9]{3}-[0-9]{2}$" cpf)))

(defn format
  "Returns a string of the correctly formatted cpf"
  [cpf]
  (shared/format length {3 ".", 6 ".", 9 "-"} cpf))

(defrecord CPF [cpf]
  Object
  (toString [this] cpf)
  Digits
  (digits [this]
    (digits cpf)))

(def cpf? (partial instance? CPF))

(defn cpf
  "Coerce to a cpf, takes a seq of digits or a string.
  Will throw in the cpf is invalid."
  [cpf]
  {:pre [(valid? cpf)]}
  (->> cpf shared/parse format ->CPF))

;; new-cpf just wraps cpf,
;; the indirection is to avoid name clash with function params inside this file.
(defn- new-cpf
  [x] (cpf x))

(defn cpf-reader [cpf]
  {:pre [(string? cpf) (formatted? cpf)]}
  (new-cpf cpf))

(defn cpf-str [cpf]
  (str "#br/cpf \"" (:cpf cpf) "\""))

(defmethod print-method CPF [cpf ^java.io.Writer w]
  (.write w (cpf-str cpf)))

(defmethod print-dup CPF [cpf w] (print-method cpf w))

(defn gen
  "Generates a random valid cpf"
  []
  (let [digits (shared/rand-digits (- length 2))]
    (new-cpf (concat digits (control-digits digits)))))


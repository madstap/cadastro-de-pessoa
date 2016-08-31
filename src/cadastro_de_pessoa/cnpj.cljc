(ns cadastro-de-pessoa.cnpj
  (:refer-clojure :exclude [format])
  (:require [cadastro-de-pessoa.shared :as shared :refer [digits #?(:cljs Digits)]])
  #?(:clj (:import [cadastro_de_pessoa.shared Digits])))

(def length 14)

(def ^:private mask1   [5 4 3 2 9 8 7 6 5 4 3 2])
(def ^:private mask2 [6 5 4 3 2 9 8 7 6 5 4 3 2])

(def control-digits (partial shared/control-digits mask1 mask2))

(def repeated
  "A set of cnpjs with repeated digits that are
  considered valid by the algorithm, but normally shouldn't count as valid."
  (set (for [i (range 10)
             :let [xs (repeat (- length 2) i)]]
         (concat xs (control-digits xs)))))

(declare cnpj?)

(defn valid?
  "Takes a string, seq of digits or a cnpj. Returns true if valid, else false.
  Does not validate formatting."
  ([cnpj]
   (if (cnpj? cnpj)
     true
     (let [cnpj (shared/parse cnpj)
           [digits control] (shared/split-control cnpj)]
       (and (= length (count cnpj))
            (not (repeated cnpj))
            (= control (control-digits digits)))))))

(defn formatted?
  "Is the cnpj formatted correctly?"
  [cnpj]
  (boolean
   (re-find #"^[0-9]{2}\.[0-9]{3}\.[0-9]{3}/[0-9]{4}-[0-9]{2}$" cnpj)))

(defn format
  "Returns a string of the correctly formatted cnpj"
  [cnpj]
  (shared/format length {2 ".", 5 ".", 8 "/", 12 "-"} cnpj))

(defrecord CNPJ [cnpj]
  Object
  (toString [this] cnpj)
  Digits
  (digits [this]
    (digits cnpj)))

(def cnpj? (partial instance? CNPJ))

(defn cnpj
  "Coerce to a cnpj. Takes a string or seq of digits and coerces to a cnpj.
  Will throw if the cnpj is invalid."
  [cnpj]
  {:pre [(valid? cnpj)]}
  (-> cnpj shared/parse format ->CNPJ))

;; new-cnpj just wraps cnpj,
;; the indirection is to avoid name clash with function params inside this file.
(defn- new-cnpj
  [x] (cnpj x))

(defn cnpj-reader [cnpj]
  {:pre [(string? cnpj) (formatted? cnpj)]}
  (new-cnpj cnpj))

(defn cnpj-str [cnpj]
  (str "#br/cnpj \"" cnpj "\""))

(defmethod print-method CNPJ [cnpj ^java.io.Writer w]
  (.write w (cnpj-str cnpj)))

(defmethod print-dup CNPJ [cnpj w] (print-method cnpj w))

(defn gen
  "Generates a random valid cnpj.
  An integer argument can be given to choose headquarters or a branch.
  (Matriz ou filial)
  In a cnpj xx.xxx.xxx/0001-xx, 0001 is the branch number,
  in this case headquarters."
  ([]
   (let [digs (shared/rand-digits (- length 2))]
     (new-cnpj (concat digs (control-digits digs)))))
  ([branch]
   {:pre [(< 0 branch 10e3) (integer? branch)]}
   (let [digs (shared/rand-digits (- length 2 4))
         branch-digs (shared/left-pad 4 0 (shared/digits branch))
         digs' (concat digs branch-digs)]
     (new-cnpj (concat digs' (control-digits digs'))))))

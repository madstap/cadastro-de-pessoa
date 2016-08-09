(ns cadastro-de-pessoa.cnpj
  (:refer-clojure :exclude [format])
  (:require [cadastro-de-pessoa.shared :as shared :refer [digits]])
  (:import [cadastro_de_pessoa.shared Digits]))


(def ^:dynamic *repeated-digits-valid?* false)

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

(defn valid?
  "Takes a string or seq of digits. Returns true if valid, else false.
  Does not validate formatting.
  Depends on the dynamic var *repeated-digits-valid?* (default false)."
  ([cnpj]
   (let [cnpj (shared/parse cnpj)
         [digits control] (shared/split-control cnpj)]
     (and (= length (count cnpj))
          (or *repeated-digits-valid?* (not (repeated cnpj)))
          (= control (control-digits digits))))))

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

(defn new-cnpj
  "Coerce to a cnpj"
  [cnpj]
  {:pre [(valid? cnpj)]}
  (-> cnpj shared/parse format ->CNPJ))

(defn cnpj-reader [cnpj]
  {:pre [(string? cnpj) (formatted? cnpj)]}
  (new-cnpj cnpj))

(defn cnpj-str [cnpj]
  (str "#br/cnpj \"" cnpj "\""))

(defmethod print-method CNPJ [cnpj ^java.io.Writer w]
  (.write w (cnpj-str cnpj)))

(defmethod print-dup CNPJ [cnpj] (print-method cnpj))

(defn gen
  "Generates a random valid cnpj.
  An integer argument can be given to choose headquarters or a branch.
  (Matriz ou filial)
  In a cnpj xx.xxx.xxx/0001-xx, 0001 is the branch number,
  in this case headquarters."
  ([]
   (new-cnpj (shared/generate-valid valid? #(shared/rand-digits length))))
  ([branch]
   {:pre [(< 0 branch 10e3) (integer? branch)]}
   (let [digits (shared/left-pad 4 0 (shared/digits branch))]
     (new-cnpj (shared/generate-valid valid?
                                      #(concat (shared/rand-digits 8)
                                               digits
                                               (shared/rand-digits 2)))))))



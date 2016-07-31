(ns cadastro-de-pessoa.cpf
  (:refer-clojure :exclude [format])
  (:require [cadastro-de-pessoa.shared :as shared]))

(def length 11)

(def repeated
  "A set of cpfs with repeated digits that are
  considered valid by the algorithm, but normally shouldn't count as valid."
  (conj (set (for [i (range 10)]
               (repeat length i))) [0 1 2 3 4 5 6 7 8 9 0]))

(defn valid?
  "Takes a string or seq of digits. Returns true if valid, else false"
  ([cpf] (valid? cpf {}))
  ([cpf {:keys [accept-repeated?] :or {:accept-repeated? false} :as opts}]
   (let [cpf (shared/parse cpf)
         [digits control] (shared/split-control cpf)]
     (and (= length (count cpf))
          (or accept-repeated? (not (repeated cpf)))
          (= control (shared/control-digits (range 10 1 -1)
                                            (range 11 1 -1) digits))))))

(defn formatted?
  "Is the cpf formatted correctly?"
  [cpf]
  (boolean
   (re-find #"^[0-9]{3}\.[0-9]{3}\.[0-9]{3}-[0-9]{2}$" cpf)))

(defn format
  "Returns a string of the correctly formatted cpf"
  [cpf]
  (shared/format length {3 ".", 6 ".", 9 "-"} cpf))

(defn gen
  "Generates a random valid cpf"
  []
  (format (shared/invoke-until-true! valid? #(shared/rand-digits length))))

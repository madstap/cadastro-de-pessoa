(ns cadastro-de-pessoa.cnpj
  (:refer-clojure :exclude [format])
  (:require [cadastro-de-pessoa.shared :as shared]))

(def length 14)

;; Seems like the only edge case with cnpj is repeated 0s
(def ^:private invalid-cnpjs
  #{(repeat length 0)})

(defn valid?
  "Takes a cnpj as a string or seq of digits. Only cares about digits if string.
  Returns true if valid, else false."
  [cnpj]
  (let [{:keys [digits control full]} (shared/parse cnpj)
        mask1   [5 4 3 2 9 8 7 6 5 4 3 2]
        mask2 [6 5 4 3 2 9 8 7 6 5 4 3 2]]
    (and (= length (count full))
         (not (invalid-cnpjs full))
         (= control (shared/control-digits mask1 mask2 digits)))))

(defn formatted?
  "Is the cnpj formatted correctly?"
  [cnpj]
  (boolean
   (re-find #"^[0-9]{2}\.[0-9]{3}\.[0-9]{3}/[0-9]{4}-[0-9]{2}$" cnpj)))

(defn format
  "Returns a string of the correctly formatted cnpj"
  [cnpj]
  (let [[a b c d e f g h i j k l m n :as full] (:full (shared/parse cnpj))]
    (assert (= length (count full)))
    (str a b "." c d e "." f g h "/" i j k l "-" m n)))

(defn random
  "Returns a random valid cnpj."
  ([]
   (format (shared/invoke-until-true! valid? #(shared/rand-digits length))))
  ([n]
   {:pre [(< -1 n 10e3) (== n (int n))]}
   (let [pad-digits (fn [digits]
                      (concat (repeat (- 4 (count digits)) 0) digits))
         digits (pad-digits (shared/str->digits (str (int (float n)))))]
     (format (shared/invoke-until-true! valid?
                                        #(concat (shared/rand-digits 8)
                                                 digits
                                                 (shared/rand-digits 2)))))))

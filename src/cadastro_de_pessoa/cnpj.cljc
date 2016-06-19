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
  (let [cnpj (shared/parse cnpj)
        [digits control] (shared/split-control cnpj)
        mask1   [5 4 3 2 9 8 7 6 5 4 3 2]
        mask2 [6 5 4 3 2 9 8 7 6 5 4 3 2]]
    (and (= length (count cnpj))
         (not (invalid-cnpjs cnpj))
         (= control (shared/control-digits mask1 mask2 digits)))))

(defn formatted?
  "Is the cnpj formatted correctly?"
  [cnpj]
  (boolean
   (re-find #"^[0-9]{2}\.[0-9]{3}\.[0-9]{3}/[0-9]{4}-[0-9]{2}$" cnpj)))

(defn format
  "Returns a string of the correctly formatted cnpj"
  [cnpj]
  (apply str (shared/insert-indexed {2 ".", 5 ".", 8 "/", 12 "-"}
                                    (take length (shared/parse cnpj)))))

(defn gen
  "Generates a random valid cnpj.
  An integer argument can be given to choose headquarters or a branch. (Matriz ou filial)
  In a cnpj xx.xxx.xxx/0001-xx, 0001 is the branch number, in this case headquarters."
  ([]
   (format (shared/invoke-until-true! valid? #(shared/rand-digits length))))
  ([branch]
   {:pre [(< -1 branch 10e3) (integer? branch)]}
   (let [pad-digits (fn [digits]
                      (concat (repeat (- 4 (count digits)) 0) digits))
         digits (pad-digits (shared/digits branch))]
     (format (shared/invoke-until-true! valid?
                                        #(concat (shared/rand-digits 8)
                                                 digits
                                                 (shared/rand-digits 2)))))))

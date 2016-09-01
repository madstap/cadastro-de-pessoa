(ns cadastro-de-pessoa.shared
  (:refer-clojure :exclude [format]))

;;; Utils

(defn left-pad [n pad xs]
  (concat (repeat (- n (count xs)) pad) xs))

(defn insert-indexed
  "Takes a map of indices to things
  and inserts the things at those indices of coll."
  ([index->x coll]
   (reduce-kv (fn [acc i x]
                (if-let [item (index->x i)]
                  (conj acc item x)
                  (conj acc x)))
              []
              (vec coll))))

;;; Parsing helpers

(defn digits "Returns a seq of the digits of x"
  [x]
  (mapv #?(:cljs js/parseInt
           :clj #(Integer/parseInt %)) (re-seq #"[0-9]" (str x))))

(defn parse
  [code]
  {:pre [(or (string? code) (sequential? code))]}
  (if (string? code) (digits code) code))

(defn split-control
  "Returns a tuple of [code control-digits],
  where control-digits are the last 2 digits."
  [coll]
  (split-at (- (count coll) 2) coll))

(defn format
  [length index->x code]
  (->> (parse code)
       (take length)
       (insert-indexed index->x)
       (apply str)))


;;; Random helpers

(defn generate-valid
  "Keeps invoking (f) until (pred (f)) is true, then returns that value."
  [pred f]
  (let [x (f)]
    (if (pred x) x (recur pred f))))

(defn rand-digits
  "Returns a seq of n random digits."
  [n]
  (repeatedly n #(rand-int 10)))


;;; The formula

(defn- control-digit
  [mask digits]
  (let [sum (apply + (map * mask digits))
        x (- 11 (rem sum 11))]
    (if (> x 9) 0 x)))

(defn control-digits
  [mask1 mask2 digits]
  (let [c1 (control-digit mask1 digits)
        c2 (control-digit mask2 (conj (vec digits) c1))]
    [c1 c2]))

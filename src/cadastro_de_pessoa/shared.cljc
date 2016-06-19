(ns cadastro-de-pessoa.shared)

;;; Utils

(defn insert-indexed
  "Takes a map of indices to things and inserts the things at those indices of coll."
  ([index->x coll]
   (reduce-kv (fn [acc i x]
                (if-let [item (index->x i)]
                  (conj acc item x)
                  (conj acc x)))
              []
              (vec coll))))

;;; Parsing helpers

(defn str->digits
  "Takes a string and returns a vector of the digits it contains, ignoring other characters"
  [s]
  (mapv #?(:clj #(Integer/parseInt %), :cljs js/parseInt) (re-seq #"[0-9]" (str s))))

(defn parse
  [code]
  {:pre [(or (string? code) (sequential? code))]}
  (if (string? code) (str->digits code) code))

(defn split-control
  "Returns a tuple of [code control-digits],
  where control-digits are the last 2 digits."
  [coll]
  (split-at (- (count coll) 2) coll))

;;; Random helpers

(defn invoke-until-true!
  "Keeps invoking (f) until (pred (f)) is true, then returns that value.
  The ! means beware of infinite loops,
  there has to be a reasonable chance of (pred (f)) being true."
  [pred f]
  (loop []
    (let [x (f)]
      (if (pred x) x (recur)))))

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

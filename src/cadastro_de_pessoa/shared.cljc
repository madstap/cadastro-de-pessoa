(ns cadastro-de-pessoa.shared)

;;; Parsing helpers

(defn str->digits
  "Takes a string and returns a seq of the digits it contains,
  ignores other characters"
  [s]
  (map #?(:clj #(Integer/parseInt %), :cljs js/parseInt) (re-seq #"[0-9]" (str s))))

(defn parse
  "Parses a cpf or cnpj string. Ignores characters that are not digits.
  Also accepts a seq of digits.
  Returns a map of :digits, :control and :full."
  [code]
  (let [full (if (string? code) (str->digits code) code)
        [digits control] (split-at (- (count full) 2) full)]
    {:digits digits :control control :full full}))

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

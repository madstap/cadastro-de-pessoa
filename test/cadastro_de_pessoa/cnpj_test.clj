(ns cadastro-de-pessoa.cnpj-test
  (:require [cadastro-de-pessoa.cnpj :as cnpj]
            [clojure.test :refer :all]))

(deftest validation-test
  (testing "valid-cnpjs"
    (are [cnpj] (cnpj/valid? cnpj)
      "36.564.023/0001-76"
      "46.303.783/0001-83"
      "48.741.030/0001-85"
      "76.887.562/0001-60"
      [4 6 3 0 3 7 8 3 0 0 0 1 8 3]))

  (testing "invalid cnpjs"
    (are [cnpj] (not (cnpj/valid? cnpj))
      "36.564.025/0001-76"
      "46.303.783/0001-33"
      "48.741.230/0001-85"
      "75.887.562/0001-60"
      [4 5 3 0 3 7 8 3 0 0 0 1 8 3])))

(deftest formatted?-test
  (testing "formatted right"
    (are [cnpj] (cnpj/formatted? cnpj)
      "48.741.230/0001-85"
      "75.887.562/0001-60"
      "46.303.783/0001-83"
      "48.741.030/0001-85"))

  (testing "formatted wrong"
    (are [cnpj] (not (cnpj/formatted? cnpj))
      "48.7412.30/0001-85"
      "75.887.562x0001-60"
      "46.30.3783/0001-83"
      "48.741.030/000185")))

(deftest format-test
  (is (= (cnpj/format (concat (range 10) (range 4))) "01.234.567/8901-23"))
  (is (= (cnpj/format "75-887-562x0001xxx60") "75.887.562/0001-60")))

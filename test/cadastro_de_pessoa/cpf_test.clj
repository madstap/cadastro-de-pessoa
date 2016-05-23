(ns cadastro-de-pessoa.cpf-test
  (:require [cadastro-de-pessoa.cpf :as cpf]
            [clojure.test :refer :all]))

(deftest cpf-test
  (testing "valid-cpfs"
    (are [cpf] (cpf/valid? cpf)
      "437.647.838-50"
      "801.483.391-07"
      "725.403.322-50"
      "270.604.973-15"
      [4 3 7 6 4 7 8 3 8 5 0]))

  (testing "invalid cpfs"
    (are [cpf] (not (cpf/valid? cpf))
      "437.647.835-50"
      "437.674.838-50"
      "347.647.838-50"
      "270.604.973-25"
      "270.604.873-15"
      "666.666.666-66"
      [4 3 7 6 4 7 8 8 3 5 0])))

(deftest formatted?-test
  (testing "formatted right"
    (are [cpf] (cpf/formatted? cpf)
      "270.604.973-25"
      "270.604.873-15"
      "270.604.873-15"
      "666.666.666-66"))

  (testing "formatted wrong"
    (are [cpf] (not (cpf/formatted? cpf))
      "270.604.973x25"
      "27.060.4873-15"
      "270.604.87csd3-15"
      "66666666666")))

(deftest format-test
  (testing "formats right"
    (is (= (cpf/format (concat (range 10) [0])) "012.345.678-90"))
    (is (= (cpf/format "27.060.4873-15") "270.604.873-15")))

  (testing "trying to format an incorrect number of digits throws an error"
    (are [xs] (thrown? AssertionError (cpf/format xs))
      (range 20)
      "123.123.123-12123"
      "123.123.123")))

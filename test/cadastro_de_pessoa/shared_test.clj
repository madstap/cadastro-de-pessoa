(ns cadastro-de-pessoa.shared-test
  (:require [clojure.test :refer :all]
            [cadastro-de-pessoa.shared :as shared]))

(deftest digits-test
  (testing "parse coll of digits from formatted cpf or cnpj"
    (is (= (shared/digits "270.604.973-25") [2 7 0 6 0 4 9 7 3 2 5]))
    (is (= (shared/digits "725.403.322-50") [7 2 5 4 0 3 3 2 2 5 0]))
    (is (= (shared/digits "61.631.891/8191-27") [6 1 6 3 1 8 9 1 8 1 9 1 2 7]))
    (is (= (shared/digits "27.755.593/7711-59") [2 7 7 5 5 5 9 3 7 7 1 1 5 9])))

  (testing "parse coll of digits from a badly formatted cpf or cnpj"
    (is (= (shared/digits "   270.604.973-25") [2 7 0 6 0 4 9 7 3 2 5]))
    (is (= (shared/digits "d 725.403.322-50a c") [7 2 5 4 0 3 3 2 2 5 0]))
    (is (= (shared/digits "61.631.891**/8(191-27") [6 1 6 3 1 8 9 1 8 1 9 1 2 7]))
    (is (= (shared/digits " 27.755.593/7711-59     ") [2 7 7 5 5 5 9 3 7 7 1 1 5 9]))))

(deftest digits-string-test
  (testing "parse string of digits from formatted cpf or cnpj"
    (is (= (shared/digits-string "270.604.973-25") "27060497325"))
    (is (= (shared/digits-string "725.403.322-50") "72540332250"))
    (is (= (shared/digits-string "61.631.891/8191-27") "61631891819127"))
    (is (= (shared/digits-string "27.755.593/7711-59") "27755593771159")))

  (testing "parse a string of digits from a badly formatted cpf or cnpj"
    (is (= (shared/digits-string "a 270.604.973-25 c.") "27060497325"))
    (is (= (shared/digits-string "   725.403.322-50") "72540332250"))
    (is (= (shared/digits-string "61.631.8*9.1/8191-27") "61631891819127"))
    (is (= (shared/digits-string "27.755.593/7711-59   ") "27755593771159"))))
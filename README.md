# cadastro-de-pessoa

> There you are, your own number on your very own door. And behind that door,
> your very own office! Welcome to the team, DZ-015

> - Mr. Warrenn (Brazil 1985)

An implementation of the validation formula for
cpf (Cadastro de Pessoa Física) and cnpj (Cadastro Nacional de Pessoa Jurídica),
the unique codes for people and companies in Brazil.

# Usage

[![Clojars Project] (https://img.shields.io/clojars/v/cadastro-de-pessoa.svg)]
(https://clojars.org/cadastro-de-pessoa)

```clojure
(ns example.core
  (:require [cadastro-de-pessoa.cpf  :as cpf]
            [cadastro-de-pessoa.cnpj :as cnpj]))
```

Validates using the formula

```clojure
(cpf/valid? [2 2 1 8 3 5 1 3 1 5 2])
(cnpj/valid? "27.865.757/0001-02")
```

Checks formatting

```clojure
(cpf/formatted? "221.83.51.31.52") ;=> false
(cnpj/formatted? "27.865.757/0001-02") ;=> true
```

Formats a cpf or cnpj correctly

```clojure
(cpf/format [2 2 1 8 3 5 1 3 1 5 2]) ;=> "221.835.131-52"
(cnpj/format "27865xxx757xxx000xxx102") ;=> "27.865.757/0001-02"
```

Generates a random, valid cpf or cnpj

```clojure
(cpf/gen) ;=> "343.696.318-66"

(cnpj/gen)   ;=> "02.583.753/5448-95"
(cnpj/gen 1) ;=> "24.275.606/0001-06"

```

```clojure
cpf/length  ;=> 11
cnpj/length ;=> 14

```

## License

Copyright © 2016 Aleksander Madland Stapnes

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

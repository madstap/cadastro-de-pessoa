# cadastro-de-pessoa

> There you are, your own number on your very own door. And behind that door,
> your very own office! Welcome to the team, DZ-015

> - Mr. Warrenn (Brazil 1985)

An implementation of the validation formula for
cpf (Cadastro de Pessoa Física) and cnpj (Cadastro Nacional de Pessoa Jurídica),
the unique codes for people and companies in Brazil.

# Usage

The API consists of 6 functions in these two namespaces.

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

```clojure
cpf/length  ;=> 11
cnpj/length ;=> 14

```

## License

Copyright © 2016 Aleksander Madland Stapnes

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

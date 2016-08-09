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

cadastro-de-pessoa defines the records CPF and CNPJ with corresponding:

* constructors (`cpf/new-cpf` and `cnpj/new-cnpj`)
* and literals (`#br/cpf "112.915.787-30"` and `#br/cnpj "63.883.794/0001-07"`)

The constructors will throw an exception when
given an invalid cpf or cnpj as input.

### API

Validate using the formula

```clojure
(cpf/valid? [2 2 1 8 3 5 1 3 1 5 2])
(cnpj/valid? "27.865.757/0001-02")
```

Check type

```clojure
(cpf/cpf? #br/cpf "112.915.787-30") ;=> true
(cnpj/cnpj? #br/cpf "112.915.787-30") ;=> false
```

Check formatting

```clojure
(cpf/formatted? "221.83.51.31.52") ;=> false
(cnpj/formatted? "27.865.757/0001-02") ;=> true
```

Format a cpf or cnpj correctly

```clojure
(cpf/format [2 2 1 8 3 5 1 3 1 5 2]) ;=> "221.835.131-52"
(cnpj/format "27865xxx757xxx000xxx102") ;=> "27.865.757/0001-02"
```

Generate a random, valid cpf or cnpj

```clojure
(cpf/gen) ;=> #br/cpf "343.696.318-66"

(cnpj/gen)   ;=> #br/cnpj "02.583.753/5448-95"
(cnpj/gen 1) ;=> #br/cnpj "24.275.606/0001-06"

```

```clojure
cpf/length  ;=> 11
cnpj/length ;=> 14

```

## License

Copyright © 2016 Aleksander Madland Stapnes

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

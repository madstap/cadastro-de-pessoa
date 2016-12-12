# cadastro-de-pessoa

> There you are, your own number on your very own door. And behind that door,
> your very own office! Welcome to the team, DZ-015

> - Mr. Warrenn (Brazil 1985)

An implementation of the validation formula for
cpf (Cadastro de Pessoa Física) and cnpj (Cadastro Nacional de Pessoa Jurídica),
the unique codes for people and companies in Brazil.

# Status

0.4.0+ should be stable and work with clj and cljs.

When clojure 1.9.0 is released I'll release version 1.0.0 with specs for the public API and [never break it](https://www.youtube.com/watch?v=oyLBGkS5ICk&t=3s).

# Usage

#### Dependency

Requires clojure 1.7.0 because of reader conditionals.

In clojurescript it works with 1.9.293, for lower versions ¯\\\_(ツ)_/¯.

`[cadastro-de-pessoa "0.4.0"]`

#### Namespace declaration

```clojure
(ns example.core
  (:require
   [cadastro-de-pessoa.cpf  :as cpf]
   [cadastro-de-pessoa.cnpj :as cnpj]))
```

### API

Functions that take a cpf/cnpj accepts either a sequence of digits or a string, while functions that return a cpf/cnpf return it as a formatted string.

Validate using the formula. In the case of a string, characters that are not numbers are ignored.

```clojure
(cpf/valid? [2 2 1 8 3 5 1 3 1 5 2])
(cnpj/valid? "27.865.757/0001-02")
```

Check formatting

```clojure
(cpf/formatted? "221.83.51.31.52") ;=> false
(cnpj/formatted? "27.865.757/0001-02") ;=> true
```

Format a cpf or cnpj correctly. Extra digits are ignored.

```clojure
(cpf/format [2 2 1 8 3 5 1 3 1 5 2]) ;=> "221.835.131-52"
(cnpj/format "27865xxx757xxx000xxx102") ;=> "27.865.757/0001-02"
```

Generate a random, valid cpf or cnpj

```clojure
(cpf/gen) ;=> "343.696.318-66"

(cnpj/gen)   ;=> "02.583.753/5448-95"

;; Can also take a branch number.
(cnpj/gen 1) ;=> "24.275.606/0001-06"

```

Constants

```clojure
;; The length of the codes
cpf/length  ;=> 11
cnpj/length ;=> 14

;; regexen that match the properly formatted string of the codes
cpf/regex => #"..."
cnpj/regex => #"..."

```

## License

Copyright © 2016 Aleksander Madland Stapnes

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

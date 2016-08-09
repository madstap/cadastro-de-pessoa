# Change Log
All notable changes to this project will be documented in this file. This change log follows the conventions of [keepachangelog.com](http://keepachangelog.com/).

## [Unreleased]

## [0.3.0] - 2016-08-??
### Breaking changes
- Add types CPF and CNPJ with tagged literals. Include data_readers.cljc.
- gen now returns literal.

### Changes
- Add dynamic var repeated-digits-valid? to control the behavior of cnpj/- and cpf/valid?.

## [0.2.0] - 2016-06-19
### Breaking changes
- Rename cpf/random and cnpj/random to cpf/gen and cnpj/gen

### Changes
- change behavior of cpf/- and cnpj/format
It no longer throws on wrong number of digits, just formats what's there if there's too few digits, and truncates if there's too many.

- depend on clojure 1.7.0 instead of 1.8.0

## [0.1.3] - 2016-06-16
### Features
- cpf/random and cnpj/random

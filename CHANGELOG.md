# Change Log
All notable changes to this project will be documented in this file. This change log follows the conventions of [keepachangelog.com](http://keepachangelog.com/).

## [Unreleased]

## [0.4.0] - 2016-12-11
### Breaking changes
- Remove all type stuff and literals and all that. Simplify and follow the unix philosophy. This is just a couple of helper functions.

## [0.3.0] - 2016-08-??
### Breaking changes
- Add types CPF and CNPJ with tagged literals. Include data_readers.cljc.
- gen now returns literal.

### Changes
- valid? can take a literal.

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

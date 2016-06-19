# Change Log
All notable changes to this project will be documented in this file. This change log follows the conventions of [keepachangelog.com](http://keepachangelog.com/).

## [Unreleased]
### Changedd
- change behavior of cpf/- and cnpj/format
It no longer throws on wrong number of digits, just formats what's there if there's too few digits, and truncates if there's too many.

## [0.1.3] - 2016-06-16
### Added
- cpf/random and cnpj/random

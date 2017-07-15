#!/bin/sh
echo RUN CORRECT INPUT TEST
for file in `ls correct_input/`; do echo ++++++++++++++++++++++++ ; echo ${file} ; echo ++++++++++++++++++++++++ ; equation_solver correct_input/${file}; done
echo RUN BAD INPUT TEST
for file in `ls bad_input/`; do echo ++++++++++++++++++++++++ ; echo ${file} ; echo ++++++++++++++++++++++++ ; equation_solver bad_input/${file}; done
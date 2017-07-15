Contents of the directory "java"
1. Main Java Program :: MainClass.java 
2. MakeFile for make utility
3. Test input for bad (bad_input) and correct input (correct_input)

Compile and install
- Run "make" command, it will compile and install the program with the name equation_solver

To run the program, do the following
- equation_solver <input_file>

Alternative way of running the program
java MainClass <input_file>

Clean the install
- Run the "make clean" command

Test Examples
We have two types:
1) Failure Cases : In directory "bad_input"
2) Successfull Cases : In directory "correct_input"

To execute:: make exec-test

Compatibility:: JDK >= 1.7

Assumptions:
1. Any blank line in file will be skipped
2. Repeating occurance of LHS variable will override previous occurance
3. All the variables are case sensitive

# Project Part 3: Interpreter Assignment

* Author: Brady Driebergen
* Class: CS354 Section 2
* Semester: Fall 2024

## Overview

This program extends on the first two part of this project. It can now evaluate the
program we give it and output the result of an operation. It can also store variables.

## Reflection

This part of the interpreter assignment looked more difficult than what it ended up
being. In a nutshell, all we had to do was add more grammar nodes, add evaluations to
all the nodes, and make a few changes to Parser.java and Lexer.java. It was pretty
daunting at first, but I felt like I quickly caught on. This was a fun part of the
project, and it was cool to see our progress up to this point. Actually being able
to evaluate our custom code is super cool!

The part I struggled with the most was figuring out how to do the compute methods 
for Mullop and Addop. On the surface, they seemed really simple. However, It took me
some time to realize how our tree operated compare to how I was reading it. Our tree
evaluates from right to left while our brains evaluate from left to right. I ended
up realizing this when I couldn't get my subtraction/division tests to pass. I solved
this issue by flipping the values in compute so the left most value executed first
and continued along. I assume my fix worked because all my tests pass with flying
colors.

## Compiling and Using

There are two ways of running this program. The first is to run Interpreter.java. When
ran, you are prompted to input a program to evaluate. To return a value, use the keyword
'wr' followed by the operation you want to evaluate and return. You can also run the 
EvaluationTest.java to test multiple programs at once.

## Results

After running the EvaluationTest.java and Interpreter.java files, both return the
expected outputs and pass all the tests.

## Sources used

https://www.digitalocean.com/community/tutorials/java-convert-string-to-double

https://github.com/BoiseState/CS354-resources/tree/master/andre/projects/ia/ia3

*Didn't really use any sources besides these :/
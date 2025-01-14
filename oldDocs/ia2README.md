# Project #: Interpreter Assignment Part 2

* Author: Brady Driebergen
* Class: CS354 Section 2
* Semester: Fall 2024

## Overview

This program is a continuation of the previous part. This program extends
our lexer to parse through a program, ensuring that there is no syntax
errors and interpreting the code. The parser does this by forming a 'syntax
tree' and checking all the expressions.

## Reflection

This project was easier than I thought. When I started reading the instructions,
I became concerned with how complicated this part of the project was going to be.
After taking my time reading through, I started to understand what was asked of me.
I quickly got to work and found myself completing this assignment with relative
ease. One thing that helped me was my understanding of parse trees. With all the
practice we've had in class, I feel I was able to easily picture how the tree
should look and translate it into code.

I'm trying to think of what part challenged me in this project, but I can't come up
with anything. Everything was self-explanatory and the references I needed were in
code. I feel like most of this project was copying and pasting with changing some 
values here and there. The hardest part was handing the ```'( expr )'``` but after
thinking about it for a bit, I realized that we're just checking for both
parenthesis and the expression in the middle. Honestly, the hardest part of this
project was remembering how to spell parenthesis. Fun and easy-going project to 
complete. I imagine that the complexity is going to ramp up in the coming weeks. 

## Compiling and Using

This program lacks a Main method, so running it isn't possible at the moment. However,
we can test the correctness of this program by running ParserTest.java. This test file
contains 19 tests for the program to run through. This can be done through an IDE that
supports testing.

## Results

When ParserTest.java is run, it results in 19/19 tests passing.

## Sources used

https://math.nist.gov/~BMiller/LaTeXML/manual/math/details/roles.html

*Honestly, I didn't need to use any sources for this part of the project.
This source was only for clarification and intrigue. 
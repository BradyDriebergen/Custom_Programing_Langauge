# Interpreter Assignment

* Author: Brady Driebergen
* Class: CS354 Section 002
* Semester: Fall 2024

## Overview

This program replicates a lexical analyzer (AKA scanner) part of an interpreter. This 
scanner takes in programing language text as a string and seperates it by tokens. Tokens
can consist of operators (+, -, =), IDs ("x", "variable"), numbers (1, 3.4, 506), etc.
The tokens will be used later for logic and operations.

## Reflection

### Part 1: Lexical Analysis

This project was a great start to this interpreter assignment. I didn't feel as if it
was too difficult and felt like I understood everything I was doing. I started out by
figuring out how to scan operations, then numbers, then comments. I felt comments were
the most difficult part of the project. While I didn't struggle too much with them, it
was wierd trying to wrap your head around how to handle comments. I ended up going with
the traditional route of using '//' and '/* */' for comments. I thought about making
them unique but I prefered keeping with industry standard.

One thing that I struggled with in this project is my finished product. While everything
works, I'm not happy with how my code looks. This is the most clear when looking at how
I handle comments in Lexer.java. For block comments, I end up calling advance() three times
in a single if statemment. I know their is a prettier way of doing this and I plan to seek
out assistance for making this look better. Other than that, I feel great with my work and 
I've tested my program thouroughly.

## Results

As a result of this program not containing a Main method, all tests must be done through the
LexerTest.java file. When ran with junit, all 18 tests pass.

### Part 1: Lexical Analysis

## Sources used

https://code.visualstudio.com/docs/java/java-testing

----------
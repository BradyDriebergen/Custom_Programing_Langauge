package syntax;

import java.util.*;

/**
 * Lexical Analyzer for CS354 programming language
 */
public class Lexer {

    private String program;      // source program being interpreted
    private int position;        // index of next char in program


    private Set<String> whitespace = new HashSet<>();
    private Set<String> letters = new HashSet<>();
    private Set<String> keywords = new HashSet<>();
    private Set<String> operators = new HashSet<>();
    private Set<String> digits = new HashSet<>();

    /**
     * Creates a new lexical analyzer
     *
     * @param program - the program text to scan
     */
    public Lexer(String program) {
        this.program = program;
        position = 0;
        initWhitespace(whitespace);
        initLetters(letters);
        initKeywords(keywords);
        initOperators(operators);
        initDigits(digits);
    }

    /**
     * Initializes the keywords
     * @param keywords2 Set to be initialized
     */
    private void initKeywords(Set<String> keywords2) {
        keywords2.add("wr");
        keywords2.add("rd");
        keywords2.add("if");
        keywords2.add("then");
        keywords2.add("else");
        keywords2.add("while");
        keywords2.add("do");
        keywords2.add("begin");
        keywords2.add("end");
        keywords2.add("var");
    }

    /**
     * Initializes the letters
     * @param s Set to be initialized
     */
    private void initLetters(Set<String> s) {
        fill(s, 'A', 'Z');
        fill(s, 'a', 'z');
    }

    private void fill(Set<String> s, char lo, char hi) {
        for (char c = lo; c <= hi; c++) {
            s.add(c + "");
        }
    }

    /**
     * Initializes the whitespace characters
     * @param s Set to be initialized
     */
    private void initWhitespace(Set<String> s) {
        s.add(" ");
        s.add("\n");
        s.add("\t");
    }

    /**
     * Initializes the operations
     * @param s Set to be initialized
     */
    private void initOperators(Set<String> s) {
        s.add("+");
        s.add("-");
        s.add("*");
        s.add("/");
        s.add(";");
        s.add("=");
        s.add("(");
        s.add(")");
        s.add("<");
        s.add("<=");
        s.add(">");
        s.add(">=");
        s.add("<>");
        s.add("==");
    }

    /**
     * Initializes the digits
     * @param s Set to be initialized
     */
    private void initDigits(Set<String> s) {
        for (int i = 0; i < 10; i++) {
            s.add(i + "");
        }
        s.add(".");
    }

    /**
     * Advances the position by 1
     */
    private void advance() {
        this.position++;
    }

    /**
     * Looks at the character a the current position
     * @return character as a string at the current position
     */
    private String peek() {
        if (hasChar()) {
            return program.charAt(position) + "";
        } else {
            return null;
        }
    }

    /**
     * Finds the next keyword ID
     * @return A token of the next keyword
     */
    private Token nextKwID() {

        int old = this.position;
        advance();

        // Keeps moving position until the next character is not a digit or letter
        while (hasChar() && (letters.contains(peek()) || digits.contains(peek()))) {
            advance();
        }

        String lexeme = program.substring(old, position);

        if (keywords.contains(lexeme))
            return new Token(lexeme, lexeme);
        else
            return new Token("id", lexeme);
    }

    private Token nextOp() {
        Token token = new Token(peek());
        advance();
        if(hasChar() && (peek().equals("=") || peek().equals(">") || peek().equals("<"))) {
            token = new Token(token.getLexeme() + peek());
            advance();
        }
        return token;
    }

    /**
     * Finds and returns a token containing the next number
     * @return Token containing next number
     */
    private Token nextNum() {
        boolean containsPeriod = (peek().equals("."));
        int old = this.position;
        advance();
        
        while(hasChar() && digits.contains(peek())) {
            if(peek().equals(".") && !containsPeriod) {
                containsPeriod = true;
            } else if (peek().equals(".") && containsPeriod) {
                break;
            }
            advance();
        }

        String digitString = program.substring(old, position);

        return new Token("num", digitString);
    }

    /**
     * Ignores the encountered comment whether an inline or 
     * block comment.
     */
    private void ignoreComment() {
        advance();

        if (peek().equals("/")) {
            while(hasChar() && !peek().equals("\n")) {
                advance();
            }
        } else {
            // Advances past the current '*'
            advance();
            while(hasChar() && (!peek().equals("*") || program.charAt(position + 1) != '/')) {
                advance();
            }
            // Advances twice to move past '*/'
            advance();
            advance();
        }
    }

    /**
     * Helper method that checks if a comment is found.
     * @return true if a comment is found, false otherwise
     */
    private boolean checkForComment() {
        if (peek().equals("/") && position < program.length() - 1) {
            if (program.charAt(position + 1) == '/' || program.charAt(position + 1) == '*') {
                return true;
            }
        }
        return false;
    }


    /**
     * Determines the kind of the next token (e.g., "id") and calls the
     * appropriate method to scan the token's lexeme (e.g., "foo").
     *
     * @return the scanned token.
     */
    public Token next() {

        while (hasChar() && whitespace.contains(peek())) {
            advance();
        }

        if (!hasChar()) {
            return new Token("EOF");
        } else {
            if (checkForComment()) {
                ignoreComment();
                return next();
            } else if(letters.contains(peek())) {
                return nextKwID();
            } else if (operators.contains(peek())) {
                return nextOp();
            } else if (digits.contains(peek())) {
                return nextNum();
            } else {
                System.err.println("illegal character at position "+ position);
                position++;
                return next();
            }
        }
    }

    /**
     * Determines if the current position of the lexer is in the bounds of the
     * program
     * @return true if there are more characters in program
     */
    public boolean hasChar() {
        return position < program.length();
    }

    /**
     * Getter for position of the lexer in the program
     * @return index of the current position of the scanner
     */
    public int getPosition() {
        return position;
    }
}
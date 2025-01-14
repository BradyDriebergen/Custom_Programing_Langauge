package syntax;

import java.util.Scanner;

import node.*;
import node.Fact_Nodes.Fact;
import node.Fact_Nodes.FactId;
import node.Fact_Nodes.FactNeg;
import node.Fact_Nodes.FactNum;
import node.Fact_Nodes.FactParser;
import node.Stmt_Nodes.Stmt;
import node.Stmt_Nodes.StmtA;
import node.Stmt_Nodes.StmtD;
import node.Stmt_Nodes.StmtRd;
import node.Stmt_Nodes.StmtWr;
import node.Stmt_Nodes.Stmt_BE;
import node.Stmt_Nodes.Stmt_IT;
import node.Stmt_Nodes.Stmt_ITE;
import node.Stmt_Nodes.Stmt_WD;

/**
 * A recursive descent parser
 */
public class Parser {

    private Lexer lexer;
    private Token lookahead;
    private Scanner read;

    /**
     * Parse an input program and return a Node that is the root of the
     * resulting parse tree.
     *
     * @param program String to be scanned and parsed
     * @return the Root Node of a parse tree
     * @throws SyntaxException - If there is a syntax error
     */
    public Node parse(String program) throws SyntaxException {
        lexer = new Lexer(program);
        lookahead = lexer.next(); //"prime the pump"
        read = new Scanner(System.in);
        Block block = parseBlock();
        match("EOF");
        return block;
    }

    /**
     * This method parses a block. A block is either a statement
     * followed by a semicolon and another block, or just a statement.
     * @return Block containing a statement and another block
     * @throws SyntaxException if there is a syntax error
     */
    private Block parseBlock() throws SyntaxException{

        Stmt stmt = parseStmt();
        Token current = lookahead;

        if(current.equalType(new Token(";"))) {
            match(";");
            return new Block(stmt, new Token(";"), parseBlock());
        } else {
            return new Block(stmt);
        }
    }

    /**
     * This method parses a statement. A statement is either an
     * assignment or a write statement.
     * @return Stmt containing an assignment or write statement
     * @throws SyntaxException if there is a syntax error
     */
    private Stmt parseStmt() throws SyntaxException{
        Assn assn = parseAssn();
        Decl decl = parseDecl();

        if (decl != null) {
            return new StmtD(decl);
        } 
        if (assn != null) {
            return new StmtA(assn);
        }

        Boolexpr boolexpr = null;
        Stmt stmt = null;

        Token current = lookahead;

        if (current.equalType("wr")) { // Checks for write expression
            match("wr");
            return new StmtWr(new Token("wr"), parseExpr());

        } else if (current.equalType("rd")) { // Checks for read expression
            match("rd");
            Token id = lookahead;
            match("id");
            return new StmtRd(lexer.getPosition(), new Token("rd"), id, read);

        } else if (current.equalType("if")) { // Checks for if-then statement
            match("if");
            boolexpr = parseBoolexpr();
            match("then");
            stmt = parseStmt();

            if (lookahead.equalType("else")) { // Checks for if-then-else statement
                match("else");
                Stmt stmt2 = parseStmt();
                return new Stmt_ITE(boolexpr, stmt, stmt2);
            } else {
                return new Stmt_IT(boolexpr, stmt);
            }
        } else if (current.equalType("while")) { // Checks for while-do statement
            match("while");
            boolexpr = parseBoolexpr();
            match("do");
            stmt = parseStmt();
            return new Stmt_WD(boolexpr, stmt);

        } else if (current.equalType("begin")) { // Checks for begin-end block statement
            match("begin");
            Block block = parseBlock();
            match("end");
            return new Stmt_BE(block);

        }
        
        return null;
    }

    /**
     * This method parses an assignment. An assignment is an id
     * followed by an equal sign and an expression. This creates
     * variables for us to use. If the current token is not an id,
     * then the statement does not have an assignment node.
     * @return Assn containing an id, equal sign, and an expression
     * @throws SyntaxException if there is a syntax error
     */
    private Assn parseAssn() throws SyntaxException {

        Token current = lookahead;

        if (current.equalType(new Token("id"))) {
            Token id = current;
            match("id");
            match("=");
            return new Assn(lexer.getPosition(), id, new Token("="), parseExpr());
        }

        return null;
    }

    //TODO
    private Decl parseDecl() throws SyntaxException {

        Token current = lookahead;

        if (current.equalType(new Token("var"))) {
            match("var");
            Token id = lookahead;
            match("id");
            if (lookahead.equalType(new Token("="))) {
                match("=");
                return new Decl(lexer.getPosition(), id, parseExpr());
            } else {
                return new Decl(lexer.getPosition(), id);
            }
        }

        return null;
    }

    /**
     * This method parses an expression. If the expression contains a
     * addop (+ or -), parse the expression to a term, addop, and an
     * expr. Otherwise, return the expression as a term.
     *
     * @return Expression depending on parsing parameters.
     * @throws SyntaxException if there is a syntax error
     */
    private Expr parseExpr() throws SyntaxException {

        Term term = parseTerm();
        Addop addop = parseAddop();

        if (addop == null) {
            return new Expr(term);
        } else {
            Expr expr=parseExpr();
            expr.append(new Expr(term,addop,null));
            return expr;
        }
    }

    /**
     * This method parses a term. If the expression contains a
     * mulop (* or /), parse the expression to a fact, mulop, and
     * a term. Otherwise, return the expression as a fact.
     * 
     * @return Term depending on parsing parameters
     * @throws SyntaxException if there's a syntax error
     */
    private Term parseTerm() throws SyntaxException {

        Fact fact = parseFact();
        Mulop mulop = parseMulop();

        if (mulop == null) {
            return new Term(fact);
        } else {
            Term term=parseTerm();
            term.append(new Term(fact,mulop,null));
            return term;
        }
    }

    /**
     * This method parses an Fact. The value of fact is dependent
     * on whether the current token is an id, num, or '('. If the
     * current token is none of these, throw a syntax error.
     * 
     * @return Fact containing a id, num, or '( expr )'
     * @throws SyntaxException if there's a syntax error
     */
    private Fact parseFact() throws SyntaxException {

        Token current = lookahead;

        if (current.equalType(new Token("id"))) {
            match("id");
            return new FactId(lexer.getPosition(), current);
        } else if (current.equalType(new Token("num"))) {
            match("num");
            return new FactNum(lexer.getPosition(), current);
        } else if (current.equalType(new Token("-"))) {
            match("-");
            return new FactNeg(new Token("-"), parseFact());
        } else {
            match("(");
            Expr expr = parseExpr();
            match(")");
            return new FactParser(new Token("("), expr, new Token(")"));
        }
    }

    /**
     * Parses a Boolexpr nonterminal and returns it.
     * @return a Node that represents a boolexpr
     * @throws SyntaxException if an invalid terminal is discovered
     */
    private Boolexpr parseBoolexpr() throws SyntaxException {
        Expr expr1 = parseExpr();
        Relop relop = parseRelop();
        Expr expr2 = parseExpr();

        return new Boolexpr(expr1, relop, expr2);
    }

    /**
     * Parses an Addop nonterminal and returns it.
     * @return a Node that represent an addop
     * @throws SyntaxException if an invalid terminal is discovered
     */
    private Addop parseAddop() throws SyntaxException {
        Token addop = lookahead;
        if (addop.equalType("+")) {
            match("+");
            return new Addop(lexer.getPosition(), addop);

        } else if (addop.equalType("-")) {
            match("-");
            return new Addop(lexer.getPosition(), addop);

        } else {
            return null;
        }
    }

    /**
     * Parses a Mulop nonterminal and returns it.
     * @return a Node that represents an addop
     * @throws SyntaxException if an invalid terminal is discovered
     */
    private Mulop parseMulop() throws SyntaxException {
        Token mulop = lookahead;
        if (mulop.equalType("*")) {
            match("*");
            return new Mulop(lexer.getPosition(), mulop);

        } else if (mulop.equalType("/")) {
            match("/");
            return new Mulop(lexer.getPosition(), mulop);

        } else {
            return null;
        }
    }

    /**
     * Parses a Relop nonterminal and returns it.
     * @return a Node that represents a relop
     * @throws SyntaxException if an invalid terminal is discovered
     */
    private Relop parseRelop() throws SyntaxException {

        Token relop = lookahead;

        if (relop.equalType(new Token("<="))) {
            match("<=");
            return new Relop(lexer.getPosition(), relop);
        } else if (relop.equalType(">=")) {
            match(">=");
            return new Relop(lexer.getPosition(), relop);
        } else if (relop.equalType("<>")) {
            match("<>");
            return new Relop(lexer.getPosition(), relop);
        } else if (relop.equalType("==")) {
            match("==");
            return new Relop(lexer.getPosition(), relop);
        } else if (relop.equalType("<")) {
            match("<");
            return new Relop(lexer.getPosition(), relop);
        } else if (relop.equalType(">")) {
            match(">");
            return new Relop(lexer.getPosition(), relop);
        } else {
            return null;
        }
    }

    /**
     * Checks if the value at the current position matches s.
     * @param s String to be compared
     * @throws SyntaxException if string doesn't match value at the current position.
     */
    private void match(String s) throws SyntaxException {
        if (lookahead.equalType(s)) {
            lookahead = lexer.next();
        } else {
            throw new SyntaxException(lexer.getPosition(), new Token(s), lookahead);
        }
    }
}

package syntax;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for syntax.Lexer
 *
 * Uses Junit5.
 */
public class LexerTest {

    /**
     * Simply creates a 'program' that has only one token. When scanned,
     * the test checks to see that the current token is of the correct type.
     *
     * Try more than one Token in a different test case!
     * @throws SyntaxException - This suppresses the need for a try/catch block.
     */
    @Test
    public void test() throws SyntaxException{

        String prg = "4";
        Lexer lexer = new Lexer(prg);
        assertEquals(new Token("num", "4"), lexer.next());

        assertEquals(new Token("EOF", "EOF"), lexer.next());
    }


    /**
     * Tests that the lexer can recognize an identifier
     *
     * @throws SyntaxException
     */
    @Test
    public void testOneIdentifier() throws SyntaxException{

        String prg = "x";
        Lexer lexer = new Lexer(prg);
        assertEquals(new Token("id", "x"), lexer.next());

        assertEquals(new Token("EOF", "EOF"), lexer.next());
    }


    /**
     * Tests that the lexer can recognize an operator (the semicolon)
     * @throws SyntaxException
     */
    @Test
    public void testOneOperator() throws SyntaxException {

        String prg = ";";
        Lexer lexer = new Lexer(prg);

        assertEquals(new Token(";", ";"), lexer.next());

        assertEquals(new Token("EOF", "EOF"), lexer.next());
    }

    @Test
    public void testIllegalCharacter() throws SyntaxException {

        String prg = "!";
        Lexer lexer = new Lexer(prg);

        assertEquals(new Token("EOF", "EOF"), lexer.next());
    }

    @Test
    public void testWrKeyword() throws SyntaxException {

        String prg = "wr";
        Lexer lexer = new Lexer(prg);

        assertEquals(new Token("wr", "wr"), lexer.next());

        assertEquals(new Token("EOF", "EOF"), lexer.next());
    }
    
    @Test
    public void testIdentifierWithNumber() throws SyntaxException {

        String prg = "hi8Friends";
        Lexer lexer = new Lexer(prg);

        assertEquals(new Token("id", "hi8Friends"), lexer.next());

        assertEquals(new Token("EOF", "EOF"), lexer.next());
    }

    @Test
    public void testNumIdentifierWithOperationsAndNoWhitespace() throws SyntaxException {
        
        String prg = "sum=hi8Friends+18";
        Lexer lexer = new Lexer(prg);

        assertEquals(new Token("id", "sum"), lexer.next());
        assertEquals(new Token("=", "="), lexer.next());
        assertEquals(new Token("id", "hi8Friends"), lexer.next());
        assertEquals(new Token("+", "+"), lexer.next());
        assertEquals(new Token("num", "18"), lexer.next());

        assertEquals(new Token("EOF", "EOF"), lexer.next());
    }

    @Test
    public void testStatementWithMultipleOperators() throws SyntaxException {

        String prg = "sum = 5 + 4;";
        Lexer lexer = new Lexer(prg);

        assertEquals(new Token("id", "sum"), lexer.next());
        assertEquals(new Token("=", "="), lexer.next());
        assertEquals(new Token("num", "5"), lexer.next());
        assertEquals(new Token("+", "+"), lexer.next());
        assertEquals(new Token("num", "4"), lexer.next());
        assertEquals(new Token(";", ";"), lexer.next());

        assertEquals(new Token("EOF", "EOF"), lexer.next());
    }

    @Test
    public void testMultipleStatementsSeperatedByNewLine() throws SyntaxException {

        String prg = "sum = 5 * 4;\nsum = sum - 1;";
        Lexer lexer = new Lexer(prg);

        assertEquals(new Token("id", "sum"), lexer.next());
        assertEquals(new Token("=", "="), lexer.next());
        assertEquals(new Token("num", "5"), lexer.next());
        assertEquals(new Token("*", "*"), lexer.next());
        assertEquals(new Token("num", "4"), lexer.next());
        assertEquals(new Token(";", ";"), lexer.next());
        
        assertEquals(new Token("id", "sum"), lexer.next());
        assertEquals(new Token("=", "="), lexer.next());
        assertEquals(new Token("id", "sum"), lexer.next());
        assertEquals(new Token("-", "-"), lexer.next());
        assertEquals(new Token("num", "1"), lexer.next());
        assertEquals(new Token(";", ";"), lexer.next());

        assertEquals(new Token("EOF", "EOF"), lexer.next());
    }

    @Test
    public void testStatementWithParenthasizes() throws SyntaxException{

        String prg = "sum = (420 + 69) / 15.0;";
        Lexer lexer = new Lexer(prg);

        assertEquals(new Token("id", "sum"), lexer.next());
        assertEquals(new Token("=", "="), lexer.next());

        assertEquals(new Token("(", "("), lexer.next());
        assertEquals(new Token("num", "420"), lexer.next());
        assertEquals(new Token("+", "+"), lexer.next());
        assertEquals(new Token("num", "69"), lexer.next());
        assertEquals(new Token(")", ")"), lexer.next());

        assertEquals(new Token("/", "/"), lexer.next());
        assertEquals(new Token("num", "15.0"), lexer.next());
        assertEquals(new Token(";", ";"), lexer.next());

        assertEquals(new Token("EOF", "EOF"), lexer.next());
    }

    @Test
    public void testDigitwithOnePoint() throws SyntaxException {

        String prg = "2.0";
        Lexer lexer = new Lexer(prg);

        assertEquals(new Token("num", "2.0"), lexer.next());

        assertEquals(new Token("EOF", "EOF"), lexer.next());
    }

    @Test
    public void testDigitWithTwoPoints() throws SyntaxException {

        String prg = "2.0.3";
        Lexer lexer = new Lexer(prg);

        assertEquals(new Token("num", "2.0"), lexer.next());
        assertEquals(new Token("num", ".3"), lexer.next());

        assertEquals(new Token("EOF", "EOF"), lexer.next());
    }

    @Test
    public void testDigitWithTwoPointsAndNoLeadingDigit() throws SyntaxException {

        String prg = ".0.3";
        Lexer lexer = new Lexer(prg);

        assertEquals(new Token("num", ".0"), lexer.next());
        assertEquals(new Token("num", ".3"), lexer.next());

        assertEquals(new Token("EOF", "EOF"), lexer.next());
    }

    @Test
    public void testDigitWithTwoPointsAndNoTrailingDigit() throws SyntaxException {

        String prg = "2..3";
        Lexer lexer = new Lexer(prg);

        assertEquals(new Token("num", "2."), lexer.next());
        assertEquals(new Token("num", ".3"), lexer.next());

        assertEquals(new Token("EOF", "EOF"), lexer.next());
    }

    @Test
    public void testInlineComment() throws SyntaxException {

        String prg = "x; // This is an ID\n" +
                    "y; // This is another ID";
        Lexer lexer = new Lexer(prg);

        assertEquals(new Token("id", "x"), lexer.next());
        assertEquals(new Token(";", ";"), lexer.next());
        assertEquals(new Token("id", "y"), lexer.next());
        assertEquals(new Token(";", ";"), lexer.next());

        assertEquals(new Token("EOF", "EOF"), lexer.next());
    }

    @Test
    public void testBlockComment() throws SyntaxException {

        String prg = "x;\n" + 
                    "/* \n" +
                    "This is a block comment \n" +
                    "*/ \n" +
                    "y;";
        Lexer lexer = new Lexer(prg);

        assertEquals(new Token("id", "x"), lexer.next());
        assertEquals(new Token(";", ";"), lexer.next());
        assertEquals(new Token("id", "y"), lexer.next());
        assertEquals(new Token(";", ";"), lexer.next());

        assertEquals(new Token("EOF", "EOF"), lexer.next());
    }

    @Test
    public void testBlockCommentWithCommentOperationsInside() throws SyntaxException {

        String prg = "x;\n" + 
                    "/* \n" +
                    " / * / // ** / \n" +
                    "*/ \n" +
                    "y;";
        Lexer lexer = new Lexer(prg);

        assertEquals(new Token("id", "x"), lexer.next());
        assertEquals(new Token(";", ";"), lexer.next());
        assertEquals(new Token("id", "y"), lexer.next());
        assertEquals(new Token(";", ";"), lexer.next());

        assertEquals(new Token("EOF", "EOF"), lexer.next());
    }
}

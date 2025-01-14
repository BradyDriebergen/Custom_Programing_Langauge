package eval;

import node.*;
import syntax.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * System tests for the semantic actions of the programming language.
 */
class EvaluationTest {

    private Parser parser;
    private Environment env;


    @BeforeEach
    void setUp() {

        parser = new Parser();
        env = new Environment();
    }

    @Test
    void testAddition() throws SyntaxException, EvalException{
        String prg = "wr 2 + 1";

        double evaluation = parser.parse(prg).eval(env);
        assertEquals(3, evaluation);
    }

    @Test
    void testSubtraction() throws SyntaxException, EvalException{
        String prg = "wr 2 - 1";

        double evaluation = parser.parse(prg).eval(env);
        assertEquals(1, evaluation);
    }

    @Test
    void testMultiplication() throws SyntaxException, EvalException{
        String prg = "wr 3 * 2";

        double evaluation = parser.parse(prg).eval(env);
        assertEquals(6, evaluation);
    }

    @Test
    void testDivision() throws SyntaxException, EvalException{
        String prg = "wr 4 / 2";

        double evaluation = parser.parse(prg).eval(env);
        assertEquals(2, evaluation);
    }

    @Test
    void testNegativeNum() throws SyntaxException, EvalException{
        String prg = "wr -1 + 2";

        double evaluation = parser.parse(prg).eval(env);
        assertEquals(1, evaluation);
    }

    @Test
    void testNegativeNum2() throws SyntaxException, EvalException{
        String prg = "wr 1 + -2";

        double evaluation = parser.parse(prg).eval(env);
        assertEquals(-1, evaluation);
    }

    @Test
    void testDoubleNegative() throws SyntaxException, EvalException{
        String prg = "wr --2";

        double evaluation = parser.parse(prg).eval(env);
        assertEquals(2, evaluation);
    }

    @Test
    void testAssignment() throws SyntaxException, EvalException{
        String prg = "var x = 1; wr x";

        double evaluation = parser.parse(prg).eval(env);
        assertEquals(1, evaluation);
    }

    @Test 
    void testBlankAssignment() throws SyntaxException, EvalException{
        String prg = "var x; wr x";

        double evaluation = parser.parse(prg).eval(env);
        assertEquals(0, evaluation);
    }

    @Test
    void testTwoAssignments() throws SyntaxException, EvalException{
        String prg = "var x = 1; x = 2; wr x";

        double evaluation = parser.parse(prg).eval(env);
        assertEquals(2, evaluation);
    }

    @Test
    void testAssignmentWithNegativeNum() throws SyntaxException, EvalException{
        String prg = "var x = -1; wr x";

        double evaluation = parser.parse(prg).eval(env);
        assertEquals(-1, evaluation);
    }

    @Test
    void testAssignmentAndAddop() throws SyntaxException, EvalException{
        String prg = "var x = 1; wr x + 1";

        double evaluation = parser.parse(prg).eval(env);
        assertEquals(2, evaluation);
    }

    @Test
    void testAssignmentWithAddop() throws SyntaxException, EvalException{
        String prg = "var x = 1 + 2; wr x";

        double evaluation = parser.parse(prg).eval(env);
        assertEquals(3, evaluation);
    }

    @Test
    void testMultipleAssignments() throws SyntaxException, EvalException{
        String prg = "var x = 1; var y = 2; wr x + y";

        double evaluation = parser.parse(prg).eval(env);
        assertEquals(3, evaluation);
    }

    @Test
    void testAssociation() throws SyntaxException, EvalException{
        String prg = "wr 10 - 4 - 3";

        double evaluation = parser.parse(prg).eval(env);
        assertEquals(3, evaluation);
    }

    @Test
    void testPrecedence() throws SyntaxException, EvalException{
        String prg = "wr 6 / (10 - 8)";

        double evaluation = parser.parse(prg).eval(env);
        assertEquals(3, evaluation);
    }

    @Test
    void testParentheses() throws SyntaxException, EvalException{
        String prg = "wr (1 + 2) * 3";

        double evaluation = parser.parse(prg).eval(env);
        assertEquals(9, evaluation);
    }

    @Test
    void testParenthesesWithNegative() throws SyntaxException, EvalException{
        String prg = "wr -(1 + 2)";

        double evaluation = parser.parse(prg).eval(env);
        assertEquals(-3, evaluation);
    }

    @Test
    void testEvalError() throws SyntaxException {

        String prg = "wr x";
        Node parseTree = parser.parse(prg);

        assertThrows(EvalException.class, () -> {
            parseTree.eval(env);
        });
    }

    @Test
    void testVariableError() throws SyntaxException {

        String prg = "x = 1; wr x";
        Node parseTree = parser.parse(prg);

        assertThrows(EvalException.class, () -> {
            parseTree.eval(env);
        });
    }

    @Test
    void testEvalError2() throws SyntaxException {

        String prg = "x = y; wr x";
        Node parseTree = parser.parse(prg);

        assertThrows(EvalException.class, () -> {
            parseTree.eval(env);
        });
    }

    @Test
    void divideByZero() throws EvalException, SyntaxException {

        String prg = "wr 1 / 0";
        Node parseTree = parser.parse(prg);

        assertThrows(EvalException.class, () -> {
            parseTree.eval(env);
        });
    }

    @Test
    void testSyntaxError() {

        String prg = "wr 1 +";
        assertThrows(SyntaxException.class, () -> {
            parser.parse(prg);
        });
    }

    @Test
    void testSyntaxError2() {

        String prg = "wr (1";
        assertThrows(SyntaxException.class, () -> {
            parser.parse(prg);
        });
    }

    @Test
    void testDoubleVariableError() {

        String prg = "var var x = 1; wr x";
        assertThrows(SyntaxException.class, () -> {
            parser.parse(prg);
        });
    }

    @Test
    void testRd() throws SyntaxException, EvalException {
        String input = "4.1";
        InputStream io = new ByteArrayInputStream(input.getBytes());
        System.setIn(io);


        String prg = "var x; rd x; wr x";


        double evaluation = parser.parse(prg).eval(env);
        assertEquals(4.1, evaluation);
    }

    @Test
    void testRd2() throws SyntaxException, EvalException {
        String input = "4.1\n5.2";
        InputStream io = new ByteArrayInputStream(input.getBytes());
        System.setIn(io);

        String prg = "var x; var y; rd x; rd y; if x > y then wr x else wr y";

        double evaluation = parser.parse(prg).eval(env);
        assertEquals(5.2, evaluation);
    }

    @Test
    void testGreaterThan() throws SyntaxException, EvalException {
        String prg = "if 2 > 1 then wr 2";

        double evaluation = parser.parse(prg).eval(env);
        assertEquals(2, evaluation);
    }

    @Test
    void testLessThan() throws SyntaxException, EvalException {
        String prg = "if 1 < 2 then wr 1";

        double evaluation = parser.parse(prg).eval(env);
        assertEquals(1, evaluation);
    }

    @Test
    void testGreaterEqual() throws SyntaxException, EvalException {
        String prg = "if 2 >= 2 then wr 2";

        double evaluation = parser.parse(prg).eval(env);
        assertEquals(2, evaluation);
    }

    @Test
    void testLessEqual() throws SyntaxException, EvalException {
        String prg = "if 2 <= 2 then wr 2";

        double evaluation = parser.parse(prg).eval(env);
        assertEquals(2, evaluation);
    }

    @Test
    void testNotEqual() throws SyntaxException, EvalException {
        String prg = "if 1 <> 2 then wr 1";

        double evaluation = parser.parse(prg).eval(env);
        assertEquals(1, evaluation);
    }

    @Test
    void testEqual() throws SyntaxException, EvalException {
        String prg = "if 2 == 2 then wr 2";

        double evaluation = parser.parse(prg).eval(env);
        assertEquals(2, evaluation);
    }

    @Test
    void testIfElseStatement() throws SyntaxException, EvalException {
        String prg = "if 1 > 2 then wr 2 else wr 3";

        double evaluation = parser.parse(prg).eval(env);
        assertEquals(3, evaluation);
    }

    @Test
    void testWhileLoop() throws SyntaxException, EvalException {
        String prg = "var x = 0; while x < 3 do x = x + 1; wr x";

        double evaluation = parser.parse(prg).eval(env);
        assertEquals(3, evaluation);
    }

    // BEGIN/END tests

    @Test
    void testOutOfScope() throws SyntaxException, EvalException {
        String prg = "begin var x = 1 end; wr x";

        assertThrows(EvalException.class, () -> {
            parser.parse(prg).eval(env);
        });
    }

    @Test
    void testVarInScope() throws SyntaxException, EvalException {
        String prg = "var x = 1; begin x = 4 end; wr x";

        double evaluation = parser.parse(prg).eval(env);
        assertEquals(4, evaluation);
    }

    @Test
    void testVarIn2Scopes() throws SyntaxException, EvalException {
        String prg = "var x = 1; begin x = 2; begin x = 3 end end; wr x";

        double evaluation = parser.parse(prg).eval(env);
        assertEquals(3, evaluation);
    }

    @Test
    void testExpressionInScope() throws SyntaxException, EvalException {
        String prg = "var x = 1; begin x = 2 * 7 end; wr x";

        double evaluation = parser.parse(prg).eval(env);
        assertEquals(14, evaluation);
    }

    @Test
    void test2Scopes() throws SyntaxException, EvalException {
        String prg = "var x = 1; begin var x = 2 end; wr x";

        double evaluation = parser.parse(prg).eval(env);
        assertEquals(1, evaluation);
    }

    @Test
    void testBeginEnd() throws SyntaxException, EvalException {
        String prg = "var x; var y; begin x = 1; y = 2 end; wr x + y";

        double evaluation = parser.parse(prg).eval(env);
        assertEquals(3, evaluation);
    }

    @Test
    void testBeginEndInIfStatement() throws SyntaxException, EvalException {
        String prg = "var x; var y; if 2 > 1 then begin x = 1; y = 2 end; wr x + y";

        double evaluation = parser.parse(prg).eval(env);
        assertEquals(3, evaluation);
    }

    @Test
    void testGCD() throws SyntaxException, EvalException {
        String input = "25\n15";
        InputStream io = new ByteArrayInputStream(input.getBytes());
        System.setIn(io);

        String prg = "var a; var b; rd a; rd b; while a<>b do if a > b then a = a-b else b = b-a; wr a";

        double evaluation = parser.parse(prg).eval(env);
        assertEquals(5, evaluation);
    }
}

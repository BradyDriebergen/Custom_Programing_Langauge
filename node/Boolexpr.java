package node;

import eval.Environment;
import eval.EvalException;

/**
 * Node that stores a boolean expression.
 */
public class Boolexpr extends Node{
    
    protected Expr expr1;
    protected Relop relop;
    protected Expr expr2;

    public Boolexpr(Expr expr1, Relop relop, Expr expr2) {
        this.expr1 = expr1;
        this.relop = relop;
        this.expr2 = expr2;
    }

    /**
     * Evaluates the boolean expression.
     * @param env the environment to store the variables
     * @return 1 if the expression is true, 0 if it is false
     * @throws EvalException if there is an error evaluating the expression
     */
    public double eval(Environment env) throws EvalException {
        return relop.compute(expr1.eval(env), expr2.eval(env)) ? 1 : 0;
    }
}

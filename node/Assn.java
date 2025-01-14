package node;

import eval.Environment;
import eval.EvalException;
import syntax.Token;

/**
 * Node that stores Assn operations.
 */
public class Assn extends Node{

    protected int pos;
    protected Token id;
    protected Token equals;
    protected Expr expr;

    public Assn(int pos, Token id, Token equals, Expr expr) {
        this.id = id;
        this.equals = equals;
        this.expr = expr;
    }

    /**
     * TODO
     * Evaluates an statement and creats a new variable in the environment.
     * @param env the environment to store the variable
     * @return 0
     * @throws EvalException if there is an error evaluating the expression
     */
    public double eval(Environment env) throws EvalException {
        return env.assign(pos, id.getLexeme(), expr.eval(env));
    }

}

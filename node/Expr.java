package node;

import eval.Environment;
import eval.EvalException;

/**
 * Node that contains expression operations
 */
public class Expr extends Node {

    protected Term term;
    protected Addop addop;
    protected Expr expr;

    public Expr(Term term, Addop addop, Expr expr) {
        this.term = term;
        this.addop = addop;
        this.expr = expr;
    }

    public Expr(Term term) {
        this.term = term;
        this.addop = null;
        this.expr = null;
    }


    /**
     * Appends an expression to the current expression
     * @param expr the expression to be appended
     */
    public void append(Expr expr) {
        if (this.expr==null)
        {
            this.addop=expr.addop;
            this.expr=expr;
            expr.addop=null;
        }
        else
        {
            this.expr.append(expr);
        }
    }

    /**
     * Evaluates an expression. If the expression contains a
     * addop (+ or -), evaluate the expression by computing the term,
     * and expression.
     * @param env the environment to store the variables
     * @return the value of the expression
     * @throws EvalException if there is an error evaluating the expression
     */
    public double eval(Environment env) throws EvalException {
        if(expr == null) {
            return term.eval(env);
        } else {
            return addop.compute(term.eval(env), expr.eval(env));
        }
    }

}

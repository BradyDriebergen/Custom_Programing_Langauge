package node.Stmt_Nodes;

import eval.Environment;
import eval.EvalException;
import node.Expr;
import syntax.Token;

/**
 * Node that stores Wr operations.
 */
public class StmtWr extends Stmt{
    
    protected Token wr;
    protected Expr expr;

    public StmtWr(Token wr, Expr expr) {
        this.wr = wr;
        this.expr = expr;
    }

    /**
     * Evaluates the expression to be printed.
     * @param env the environment to store the variables
     * @return the value of the expression
     * @throws EvalException if there is an error evaluating the expression
     */
    public double eval(Environment env) throws EvalException {
        double result = expr.eval(env);
        System.out.println(result);
        return result;
    }

}

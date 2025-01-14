package node.Stmt_Nodes;

import eval.Environment;
import eval.EvalException;
import node.Boolexpr;

/**
 * Node that stores an if-then-else statement.
 */
public class Stmt_ITE extends Stmt{
    
    protected Boolexpr boolexpr;
    protected Stmt stmt1;
    protected Stmt stmt2;

    public Stmt_ITE(Boolexpr boolexpr, Stmt stmt1, Stmt stmt2) {
        this.boolexpr = boolexpr;
        this.stmt1 = stmt1;
        this.stmt2 = stmt2;
    }

    /**
     * Evaluates an if-then-else statement
     * @param env the environment to store the variables
     * @return the value of the statement if boolean expression is true, 
     * otherwise the value of the else statement
     * @throws EvalException if there is an error evaluating the statement
     */
    public double eval(Environment env) throws EvalException {
        if(boolexpr.eval(env) > 0) {
            return stmt1.eval(env);
        }
        return stmt2.eval(env);
    }
}

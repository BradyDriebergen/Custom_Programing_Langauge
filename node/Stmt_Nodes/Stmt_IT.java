package node.Stmt_Nodes;

import eval.Environment;
import eval.EvalException;
import node.Boolexpr;

/**
 * Node that stores an if-then statement.
 */
public class Stmt_IT extends Stmt{
    
    protected Boolexpr boolexpr;
    protected Stmt stmt;

    public Stmt_IT(Boolexpr boolexpr, Stmt stmt) {
        this.boolexpr = boolexpr;
        this.stmt = stmt;
    }

    /**
     * Evaluates an if-then statement
     * @param env the environment to store the variables
     * @return the value of the statement
     * @throws EvalException if there is an error evaluating the statement
     */
    public double eval(Environment env) throws EvalException {
        if (boolexpr.eval(env) > 0) {
            return stmt.eval(env);
        }
        return 0;
    }
}

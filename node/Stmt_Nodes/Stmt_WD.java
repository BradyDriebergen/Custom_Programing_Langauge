package node.Stmt_Nodes;

import eval.Environment;
import eval.EvalException;
import node.Boolexpr;

/**
 * Node that stores a while-do statement.
 */
public class Stmt_WD extends Stmt{
    
    protected Boolexpr boolexpr;
    protected Stmt stmt;

    public Stmt_WD(Boolexpr boolexpr, Stmt stmt) {
        this.boolexpr = boolexpr;
        this.stmt = stmt;
    }

    /**
     * Evaluates a while-do statement
     * @param env the environment to store the variables
     * @return the value of the statement
     * @throws EvalException if there is an error evaluating the statement
     */
    public double eval(Environment env) throws EvalException {
        double temp = 0;

        while(boolexpr.eval(env) > 0) {
            temp = stmt.eval(env);
        }
        return temp;
    }
}

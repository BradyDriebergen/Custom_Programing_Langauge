package node.Stmt_Nodes;

import eval.Environment;
import eval.EvalException;
import node.Assn;

/**
 * Node that stores statements with assignments operations.
 */
public class StmtA extends Stmt{
    
    protected Assn assn;

    public StmtA(Assn assn) {
        this.assn = assn;
    }

    /**
     * Evaluates the assignment.
     * @param env the environment to store the variables
     * @return the value of the assignment
     * @throws EvalException if there is an error evaluating the assignment
     */
    public double eval(Environment env) throws EvalException {
        return assn.eval(env);
    }
}

package node.Stmt_Nodes;

import eval.Environment;
import eval.EvalException;
import node.Decl;

/**
 * Node that stores statements with assignments operations.
 */
public class StmtD extends Stmt{
    
    protected Decl decl;

    public StmtD(Decl decl) {
        this.decl = decl;
    }

    /**
     * Evaluates the declaaration.
     * @param env the environment to store the variables
     * @return the value of the assignment
     * @throws EvalException if there is an error evaluating the assignment
     */
    public double eval(Environment env) throws EvalException {
        return decl.eval(env);
    }
}

package node.Stmt_Nodes;

import eval.Environment;
import eval.EvalException;
import node.Block;

/**
 * Node that stores a block (Begin/End) statement.
 */
public class Stmt_BE extends Stmt{
    
    protected Block block;

    public Stmt_BE(Block block) {
        this.block = block;
    }

    /**
     * TODO
     * Evaluates a block statement
     * @param env the environment to store the variables
     * @return the value of the block
     * @throws EvalException if there is an error evaluating the block
     */
    public double eval(Environment env) throws EvalException {
        env.createNewScope();
        double temp = block.eval(env);
        env.destroyTopScope();
        return temp;
        
    }
}

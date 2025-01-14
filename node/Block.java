package node;

import eval.Environment;
import eval.EvalException;
import node.Stmt_Nodes.Stmt;
import syntax.Token;

/**
 * Node that stores a block of statements and operations.
 */
public class Block extends Node{

    protected Stmt stmt;
    protected Token semi;
    protected Block block;

    public Block(Stmt stmt, Token semi, Block block) {
        this.stmt = stmt;
        this.semi = semi;
        this.block = block;
    }

    public Block(Stmt stmt) {
        this.stmt = stmt;
        this.semi = null;
        this.block = null;
    }

    /**
     * Appends a block to the current block
     * @param block the block to be appended
     */
    public void append(Block block) {
        if (this.block==null)
        {
            this.semi=block.semi;
            this.block=block;
            block.semi=null;
        }
        else
        {
            this.block.append(block);
        }
    }
 
    /**
     * Evaluates a block
     * @param env the environment to store the variables
     * @return the value of the block
     * @throws EvalException if there is an error evaluating the block
     */
    public double eval(Environment env) throws EvalException {
        if(block == null) {
            return stmt.eval(env);
        } else {
            return stmt.eval(env) + block.eval(env);
        }
    }
}

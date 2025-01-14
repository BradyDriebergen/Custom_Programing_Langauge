package node.Stmt_Nodes;

import java.util.Scanner;

import eval.Environment;
import eval.EvalException;
import syntax.Token;

/**
 * Node that stores Stmt's read operation.
 */
public class StmtRd extends Stmt{

    private Scanner read;

    protected int pos;
    protected Token rd;
    protected Token id;

    public StmtRd(int pos, Token rd, Token id, Scanner read) {
        this.rd = rd;
        this.id = id;
        this.read = read;
    }

    /**
     * TODO
     * Evaluates a read statement.
     * @param env the environment to store the variables
     * @return 0
     * @throws EvalException if there is an error evaluating the read statement
     */
    public double eval(Environment env) throws EvalException {
        double val = read.nextDouble();
        env.assign(pos, id.getLexeme(), val);
        return 0;
    }
}

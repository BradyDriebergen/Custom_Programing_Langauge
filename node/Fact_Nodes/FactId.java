package node.Fact_Nodes;

import eval.Environment;
import eval.EvalException;
import syntax.Token;

/**
 * Node that stores Fact's ID operation.
 */
public class FactId extends Fact{

    protected Token id;
    protected int position;

    public FactId(int position, Token id) {
        this.id = id;
        this.position = position;
    }

    /**
     * Evaluates an ID operation and returns the value of the ID
     * at the current position.
     * @param env the environment to store the variables
     * @return the value of the ID
     * @throws EvalException if there is an error evaluating the ID
     */
    public double eval(Environment env) throws EvalException {
        return env.get(this.position, id.getLexeme());
    }

}

package node.Fact_Nodes;

import eval.Environment;
import eval.EvalException;
import syntax.Token;

/**
 * Node that stores Fact's num operation.
 */
public class FactNum extends Fact{
    protected Token num;
    protected int position;

    public FactNum(int position, Token num) {
        this.num = num;
        this.position = position;
    }

    /**
     * Evaluates a fact num.
     * @param env the environment to store the variables
     * @return the value of the num
     * @throws EvalException if there is an error evaluating the num
     */
    public double eval(Environment env) throws EvalException {
        return Double.parseDouble(num.getLexeme());
    }
}

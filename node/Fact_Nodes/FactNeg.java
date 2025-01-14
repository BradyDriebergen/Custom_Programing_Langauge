package node.Fact_Nodes;

import eval.Environment;
import eval.EvalException;
import syntax.Token;

/**
 * Node that stores a negative fact operation.
 */
public class FactNeg extends Fact{
    protected Token neg;
    protected Fact fact;

    public FactNeg(Token neg, Fact fact) {
        this.neg = neg;
        this.fact = fact;
    }

    /**
     * Evaluates a negative fact
     * @param env the environment to store the variables
     * @return the negative value of the fact
     * @throws EvalException if there is an error evaluating the fact
     */
    public double eval(Environment env) throws EvalException {
        return fact.eval(env) * -1;
    }
}

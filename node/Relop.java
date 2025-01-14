package node;

import eval.EvalException;
import syntax.Token;

/**
 * Node that stores a relop operation.
 */
public class Relop extends Node{
    
    protected Token relop;

    public Relop(int position, Token relop) {
        this.position = position;
        this.relop = relop;
    }

    /**
     * Computes the relop operation.
     * @param a the first value to compare
     * @param b the second value to compare
     * @return the result of the comparison as a boolean
     * @throws EvalException if there is an error computing the relop
     */
    public boolean compute(double a, double b) throws EvalException {
        switch (relop.getLexeme()) {
            case "<":
                return a < b;
            case "<=":
                return a <= b;
            case ">":
                return a > b;
            case ">=":
                return a >= b;
            case "<>":
                return a != b;
            case "==":
                return a == b;
            default:
                throw new EvalException(position, "How did you get here?");
        }
    }
}

package node;

import eval.EvalException;
import syntax.Token;

/**
 * Node that stores * and / operations
 */
public class Mulop extends Node{
    protected Token mulop;

    public Mulop(int position, Token mulop) {
        this.position = position;
        this.mulop = mulop;
    }

    /**
     * Returns the multiplication or division of two operands.
     * @param b left operand
     * @param a right operand
     * @return the result of the operation
     */
    public double compute(double b, double a) throws EvalException {
        if (mulop.equalType("/")) {
            if (b == 0) {
                throw new EvalException(position, "Division by zero");
            }
            return a / b;
        }
        return a * b;
    }
}

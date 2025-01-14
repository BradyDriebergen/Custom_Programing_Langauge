package node;

import syntax.*;

/**
 * Node that stores + and - operations
 */
public class Addop extends Node {

    protected Token addop;

    public Addop(int position, Token addop) {
        this.position = position;
        this.addop = addop;
    }

    /**
     * Returns the sum of two numbers if the addop is a +, 
     * otherwise returns the difference
     * @param b left operand
     * @param a right operand
     * @return the sum or difference of a and b
     */
    public double compute(double b, double a) {
        if (addop.equalType("-")) {
            return a - b;
        }
        return a + b;
    }

}

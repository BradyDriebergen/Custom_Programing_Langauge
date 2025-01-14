package node.Fact_Nodes;

import eval.Environment;
import eval.EvalException;
import node.Expr;
import syntax.Token;

/**
 * Node that stores an expression operation surrounded by parenthesis.
 */
public class FactParser extends Fact{
    protected Token lParenthese;
    protected Expr expr;
    protected Token rParenthese;

    public FactParser(Token lParenthese, Expr expr, Token rParenthese) {
        this.lParenthese = lParenthese;
        this.expr = expr;
        this.rParenthese = rParenthese;
    }

    /**
     * Evaluates the expression inside the parenthesis.
     * @param env the environment to store the variables
     * @return the value of the expression
     * @throws EvalException if there is an error evaluating the expression
     */
    public double eval(Environment env) throws EvalException {
        return expr.eval(env);
    }
}

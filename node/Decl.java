package node;

import eval.Environment;
import eval.EvalException;
import syntax.Token;

public class Decl extends Node{

    protected int pos;
    protected Token id;
    protected Expr expr;

    public Decl(int pos, Token id) {
        this.id = id;
        this.expr = null;
    }

    public Decl(int pos, Token id, Expr expr) {
        this.id = id;
        this.expr = expr;
    }

    //TODO
    public double eval(Environment env) throws EvalException {
        if(expr == null) {
            return env.put(pos, id.getLexeme(), 0);
        } else {
            return env.put(pos, id.getLexeme(), expr.eval(env));
        }
    }
}

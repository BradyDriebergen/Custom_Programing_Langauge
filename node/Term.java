package node;

import eval.Environment;
import eval.EvalException;
import node.Fact_Nodes.Fact;

/**
 * Node that contains term operations
 */
public class Term extends Node {

    protected Fact fact;
    protected Mulop mulop;
    protected Term term;

    public Term(Fact fact, Mulop mulop, Term term) {
        this.fact = fact;
        this.mulop = mulop;
        this.term = term;
    }

    public Term(Fact fact) {
        this.fact = fact;
        this.mulop = null;
        this.term = null;
    }

    /**
     * Appends a term to the current term
     * @param term the term to be appended
     */
    public void append(Term term) {
        if (this.term==null) {
            this.mulop = term.mulop;
            this.term = term;
            term.mulop=null;
        } else {
            this.term.append(term);
        }
    }

    /**
     * Evaluates a term. If the term contains a
     * mulop (* or /), evaluate the term by computing the fact,
     * and term.
     * @param env the environment to store the variables
     * @return the value of the term
     * @throws EvalException if there is an error evaluating the term
     */
    public double eval(Environment env) throws EvalException {
        if (term == null) {
            return fact.eval(env);
        } else {
            return mulop.compute(fact.eval(env), term.eval(env));
        }
    }
}

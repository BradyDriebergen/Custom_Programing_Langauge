package eval;

import java.util.HashMap;
import java.util.Stack;

/**
 * A referencing environment for bindings
 */
public class Environment {

    private Stack<HashMap<String, Double>> environments;

    public Environment() {
        this.environments = new Stack<>();
        environments.push(new HashMap<>());
    }

    //TODO
    public void createNewScope() {
        this.environments.push(new HashMap<>());
    }

    //TODO
    public void destroyTopScope() {
        this.environments.pop();
    }

    /**
     * TODO
     * Puts a variable in the environment
     * @param var the variable name to store
     * @param val the value of the variable
     * @return 0
     */
    public double put(int pos, String var, double val) throws EvalException {
        HashMap<String, Double> scope = this.environments.pop();

        if (!scope.containsKey(var)) {
            scope.put(var, val);
        } else {
            this.environments.push(scope);
            throw new EvalException(pos, "Declared " + var + " multiple times within scope");
        }

        this.environments.push(scope);

        return 0;
    }

    //TODO
    public double assign(int pos, String var, double val) throws EvalException {
        try {
            Stack<HashMap<String, Double>> tempStack = new Stack<>();
            HashMap<String, Double> tempMap;
            boolean found = false;

            while(!environments.empty()) {
                tempMap = this.environments.pop();
                if (tempMap.containsKey(var)) {
                    tempMap.put(var, val);
                    tempStack.push(tempMap);
                    found = true;
                    break;
                }
                tempStack.push(tempMap);
            }

            while(!tempStack.empty()) {
                environments.push(tempStack.pop());
            }

            if (found) {
                return 0;
            } else {
                throw new Exception();
            }

        } catch (Exception e) {
            throw new EvalException(pos, "Variable " + var + " not found.");
        }
    }

    /**
     * TODO
     * Gets a variable from the environment.
     * @param pos the position of the variable
     * @param var the variable name to get
     * @return the value of the variable
     * @throws EvalException if the variable is not found
     */
    public double get(int pos, String var) throws EvalException {
        try {
            Stack<HashMap<String, Double>> tempStack = new Stack<>();
            HashMap<String, Double> tempMap;
            double result = Double.NaN;

            while(!environments.empty()) {
                tempMap = this.environments.pop();
                tempStack.push(tempMap);
                if (tempMap.containsKey(var)) {
                    result = tempMap.get(var);
                    break;
                }
            }

            while(!tempStack.empty()) {
                environments.push(tempStack.pop());
            }

            if (Double.isNaN(result)) {
                throw new Exception();
            } else {
                return result;
            }

        } catch (Exception e) {
            throw new EvalException(pos, "Variable " + var + " not found.");
        }
    }
}

package ee.moo.skynet.inference;

import ee.moo.skynet.formula.Formula;
import ee.moo.skynet.util.BinaryUtil;
import ee.moo.skynet.util.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: tarmo
 * Date: 5/31/13
 * Time: 1:57 PM
 */
public class InferenceStrategyMP extends InferenceStrategy {

    @Override
    public InferenceResult apply(Formula formula, Map<String, Integer> values) {

        InferenceResult result = new InferenceResult(values);

        Formula lhs = formula.getLeft();
        Formula rhs = formula.getRight();

        List<String> unknown = getUnknown(formula.getRight(), values);

        // no unknowns in RHS, no need to try to infere anything
        if (unknown.isEmpty()) {
            for (String statement : formula.getStatements()) {
                if (!result.contains(statement)) {
                    result.set(statement, -1);
                }
            }

            return result;
        }

        // detect if LHS is always true, based on already known values

        if (!isAlwaysTrue(lhs, values)) {

            // found an interpretation that isn't true, therefore we consider
            // the inference as a failure and we can stop here

            for (String statement : formula.getStatements()) {
                if (!result.contains(statement)) {
                    result.set(statement, -1);
                }
            }

            return result;
        }

        // inference is most likely successful, fill in the missing unknowns for LHS

        for (String statement : lhs.getStatements()) {
            if (!result.contains(statement)) {
                result.set(statement, -1);
            }
        }

        // we keep an history of unknown variable values for the rhs
        Map<String, List<Integer>> history = getHistoryContainer(unknown);

        for (int[] permutation : BinaryUtil.permutations(unknown.size())) {

            // set known values for RHS
            for (String key : values.keySet()) {
                rhs.setValue(key, values.get(key));
            }

            // set unknown values for RHS based on permutations
            for (int i = 0; i < permutation.length; i++) {
                rhs.setValue(unknown.get(i), permutation[i]);
            }

            if (rhs.evaluate()) {

                // if RHS evaluates to true track history for each unknown variable

                for (int i = 0; i < permutation.length; i++) {
                    history.get(unknown.get(i)).add(permutation[i]);
                }

            }
        }

        // unknowns that have a fixed value for RHS to evaluate to true will be considered found

        for (String key : history.keySet()) {

            if (history.get(key).size() > 0) {

                boolean onlyT = true;
                boolean onlyF = true;

                for (Integer i : history.get(key)) {

                    if (i == 1) {
                        onlyF = false;
                    }

                    if (i == 0) {
                        onlyT = false;
                    }
                }

                if (onlyF) {
                    result.set(key, 0);

                } else if (onlyT) {
                    result.set(key, 1);

                } else {
                    result.set(key, -1);

                }
            }
        }

        return result;
    }

    public static void main(String[] args) {

        Formula f = Formula.parse("A&BvE⊃CvD");

        Map<String, Integer> values = new HashMap<String, Integer>();

        values.put("A", 1);
        values.put("B", 0);
        values.put("C", 0);
        values.put("E", 1);

        InferenceResult result = new InferenceStrategyMP().apply(f, values);

        for (String name : result.getNames()) {
            System.out.print(String.format("%s: ", name));
            System.out.print(StringUtil.lpad(String.valueOf(result.get(name)), 2, ' '));
            System.out.println();
        }
    }
}

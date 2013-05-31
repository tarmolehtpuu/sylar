package ee.moo.skynet.inference;

import ee.moo.skynet.formula.Formula;
import ee.moo.skynet.util.BinaryUtil;
import ee.moo.skynet.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: tarmo
 * Date: 5/31/13
 * Time: 1:58 PM
 */
public class InferenceStrategyEQ extends InferenceStrategy {

    @Override
    public InferenceResult apply(Formula formula, Map<String, Integer> values) {

        InferenceResult result = new InferenceResult(values);

        Formula lhs = formula.getLeft();
        Formula rhs = formula.getRight();

        List<String> unknownLhs = getUnknown(formula.getLeft(), values);
        List<String> unknownRhs = getUnknown(formula.getRight(), values);

        // we keep an history of unknown variable values
        Map<String, List<Integer>> history = new HashMap<String, List<Integer>>();


        if (isAlwaysTrue(formula.getLeft(), values)) {

            // LHS is always true, RHS should always be true

            for (String name : unknownRhs) {
                history.put(name, new ArrayList<Integer>());
            }

            for (int[] permutation : BinaryUtil.permutations(unknownRhs.size())) {

                // set known values for RHS
                for (String key : values.keySet()) {
                    rhs.setValue(key, values.get(key));
                }

                // set unknown values for RHS based on permutations
                for (int i = 0; i < permutation.length; i++) {
                    rhs.setValue(unknownRhs.get(i), permutation[i]);
                }

                if (rhs.evaluate()) {

                    // if RHS evaluates to true track history for each unknown variable

                    for (int i = 0; i < permutation.length; i++) {
                        history.get(unknownRhs.get(i)).add(permutation[i]);
                    }

                }
            }


        } else if (isAlwaysFalse(formula.getLeft(), values)) {

            // LHS is always false, RHS should always be false

            for (String name : unknownRhs) {
                history.put(name, new ArrayList<Integer>());
            }

            for (int[] permutation : BinaryUtil.permutations(unknownRhs.size())) {

                // set known values for RHS
                for (String key : values.keySet()) {
                    rhs.setValue(key, values.get(key));
                }

                // set unknown values for RHS based on permutations
                for (int i = 0; i < permutation.length; i++) {
                    rhs.setValue(unknownRhs.get(i), permutation[i]);
                }

                if (!rhs.evaluate()) {

                    // if RHS evaluates to false track history for each unknown variable

                    for (int i = 0; i < permutation.length; i++) {
                        history.get(unknownRhs.get(i)).add(permutation[i]);
                    }

                }
            }

        } else if (isAlwaysTrue(formula.getRight(), values)) {

            // RHS is always true, LHS should always be true

            for (String name : unknownLhs) {
                history.put(name, new ArrayList<Integer>());
            }

            for (int[] permutation : BinaryUtil.permutations(unknownLhs.size())) {

                // set known values for LHS
                for (String key : values.keySet()) {
                    lhs.setValue(key, values.get(key));
                }

                // set unknown values for LHS based on permutations
                for (int i = 0; i < permutation.length; i++) {
                    lhs.setValue(unknownLhs.get(i), permutation[i]);
                }

                if (lhs.evaluate()) {

                    // if LHS evaluates to true track history for each unknown variable

                    for (int i = 0; i < permutation.length; i++) {
                        history.get(unknownLhs.get(i)).add(permutation[i]);
                    }

                }
            }


        } else if (isAlwaysFalse(formula.getRight(), values)) {

            // RHS is always false, LHS should always be false

            for (String name : unknownLhs) {
                history.put(name, new ArrayList<Integer>());
            }

            for (int[] permutation : BinaryUtil.permutations(unknownLhs.size())) {

                // set known values for LHS
                for (String key : values.keySet()) {
                    lhs.setValue(key, values.get(key));
                }

                // set unknown values for LHS based on permutations
                for (int i = 0; i < permutation.length; i++) {
                    lhs.setValue(unknownLhs.get(i), permutation[i]);
                }

                if (!lhs.evaluate()) {

                    // if LHS evaluates to false track history for each unknown variable

                    for (int i = 0; i < permutation.length; i++) {
                        history.get(unknownLhs.get(i)).add(permutation[i]);
                    }

                }
            }


        }

        // unknowns that have a fixed value will be considered found

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

        // fill in values that we are still unsure about

        for (String statement : formula.getStatements()) {
            if (!result.contains(statement)) {
                result.set(statement, -1);
            }
        }


        return result;
    }


    public static void main(String[] args) {

        Formula f = Formula.parse("P⇔!Q");

        Map<String, Integer> values = new HashMap<String, Integer>();

        values.put("P", 1);

        InferenceResult result = new InferenceStrategyEQ().apply(f, values);

        for (String name : result.getNames()) {
            System.out.print(String.format("%s: ", name));
            System.out.print(StringUtil.lpad(String.valueOf(result.get(name)), 2, ' '));
            System.out.println();
        }
    }
}

package ee.moo.skynet.inference;

import ee.moo.skynet.Formula;
import ee.moo.skynet.util.BinaryUtil;
import ee.moo.skynet.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: tarmo
 * Date: 5/31/13
 * Time: 1:57 PM
 */
public class InferenceStrategyMP implements InferenceStrategy {

    @Override
    public InferenceResult apply(Formula formula, Map<String, Integer> values) {

        InferenceResult result = new InferenceResult();

        // all the known values, will be known also after inference

        for (String statement : values.keySet()) {
            result.set(statement, values.get(statement));
        }

        Formula lhs = formula.getLeft();
        Formula rhs = formula.getRight();

        List<String> unknownLhs = new ArrayList<String>();
        List<String> unknownRhs = new ArrayList<String>();

        // figure out the unknown values for LHS
        for (String statement : formula.getLeft().getStatements()) {
            if (!values.containsKey(statement)) {
                unknownLhs.add(statement);
            }
        }

        // figure out the unknown values for RHS
        for (String statement : formula.getRight().getStatements()) {
            if (!values.containsKey(statement)) {
                unknownRhs.add(statement);
            }
        }

        // no unknowns in RHS, no need to try to infere anything
        if (unknownRhs.isEmpty()) {
            for (String statement : formula.getStatements()) {
                if (!result.contains(statement)) {
                    result.set(statement, -1);
                }
            }

            return result;
        }

        // detect if LHS is always true, based on already known values

        for (int[] permutation : BinaryUtil.permutations(unknownLhs.size())) {

            // set known values
            for (String key : values.keySet()) {
                lhs.setValue(key, values.get(key));
            }

            // set unknown values from permutation
            for (int i = 0; i < permutation.length; i++) {
                lhs.setValue(unknownLhs.get(i), permutation[i]);
            }

            if (!lhs.evaluate()) {

                // found an interpretation that isn't true, therefore we consider
                // the inference as a failure and we can stop here

                for (String statement : formula.getStatements()) {
                    if (!result.contains(statement)) {
                        result.set(statement, -1);
                    }
                }

                return result;
            }
        }

        // inference is most likely successful, fill in the missing unknowns for LHS

        for (String statement : lhs.getStatements()) {
            if (!result.contains(statement)) {
                result.set(statement, -1);
            }
        }

        // we keep an history of unknown variable values for the rhs
        Map<String, List<Integer>> history = new HashMap<String, List<Integer>>();

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

        // unknowns that have a fixed value for RHS to evaluate to true will be considered found

        for (String key : history.keySet()) {

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

        return result;
    }

    public static void main(String[] args) {

        Formula f = Formula.parse("AvB⊃CvD");

        Map<String, Integer> values = new HashMap<String, Integer>();

        values.put("A", 1);
        values.put("C", 0);

        InferenceResult result = new InferenceStrategyMP().apply(f, values);

        for (String name : result.getNames()) {
            System.out.print(String.format("%s: ", name));
            System.out.print(StringUtil.lpad(String.valueOf(result.get(name)), 2, ' '));
            System.out.println();
        }
    }
}

package ee.moo.skynet.inference;

import ee.moo.skynet.formula.Formula;
import ee.moo.skynet.util.PermutationIterator;

import java.util.List;

/**
 * User: tarmo
 * Date: 5/31/13
 * Time: 1:58 PM
 */
public class InferenceStrategyEQ extends InferenceStrategy {

    @Override
    public InferenceResult apply(InferenceRequest request) {

        InferenceResult result = new InferenceResult(request.getValues());

        Formula lhs = request.getFormula().getLeft();
        Formula rhs = request.getFormula().getRight();

        List<String> unknownLhs = getUnknown(lhs, request.getValues());
        List<String> unknownRhs = getUnknown(rhs, request.getValues());

        if (isAlwaysTrue(lhs, request.getValues())) {

            // LHS is always true, RHS should always be true

            PermutationIterator iterator = new PermutationIterator(unknownRhs.size());

            while (iterator.hasNext()) {

                int[] permutation = iterator.next();

                // set known values for RHS
                for (String key : request.getValues().keySet()) {
                    rhs.setValue(key, request.getValues().get(key));
                }

                // set unknown values for RHS based on permutations
                for (int i = 0; i < permutation.length; i++) {
                    rhs.setValue(unknownRhs.get(i), permutation[i]);
                }

                if (rhs.evaluate()) {

                    // if RHS evaluates to true track history for each unknown variable

                    for (int i = 0; i < permutation.length; i++) {
                        result.record(unknownRhs.get(i), permutation[i]);
                    }

                }
            }


        } else if (isAlwaysFalse(lhs, request.getValues())) {

            // LHS is always false, RHS should always be false

            PermutationIterator iterator = new PermutationIterator(unknownRhs.size());

            while (iterator.hasNext()) {

                int[] permutation = iterator.next();

                // set known values for RHS
                for (String key : request.getValues().keySet()) {
                    rhs.setValue(key, request.getValues().get(key));
                }

                // set unknown values for RHS based on permutations
                for (int i = 0; i < permutation.length; i++) {
                    rhs.setValue(unknownRhs.get(i), permutation[i]);
                }

                if (!rhs.evaluate()) {

                    // if RHS evaluates to false track history for each unknown variable

                    for (int i = 0; i < permutation.length; i++) {
                        result.record(unknownRhs.get(i), permutation[i]);
                    }

                }
            }

        } else if (isAlwaysTrue(rhs, request.getValues())) {

            // RHS is always true, LHS should always be true
            PermutationIterator iterator = new PermutationIterator(unknownLhs.size());

            while (iterator.hasNext()) {

                int[] permutation = iterator.next();

                // set known values for LHS
                for (String key : request.getValues().keySet()) {
                    lhs.setValue(key, request.getValues().get(key));
                }

                // set unknown values for LHS based on permutations
                for (int i = 0; i < permutation.length; i++) {
                    lhs.setValue(unknownLhs.get(i), permutation[i]);
                }

                if (lhs.evaluate()) {

                    // if LHS evaluates to true track history for each unknown variable

                    for (int i = 0; i < permutation.length; i++) {
                        result.record(unknownLhs.get(i), permutation[i]);
                    }

                }
            }


        } else if (isAlwaysFalse(rhs, request.getValues())) {

            // RHS is always false, LHS should always be false

            PermutationIterator iterator = new PermutationIterator(unknownLhs.size());

            while (iterator.hasNext()) {

                int[] permutation = iterator.next();

                // set known values for LHS
                for (String key : request.getValues().keySet()) {
                    lhs.setValue(key, request.getValues().get(key));
                }

                // set unknown values for LHS based on permutations
                for (int i = 0; i < permutation.length; i++) {
                    lhs.setValue(unknownLhs.get(i), permutation[i]);
                }

                if (!lhs.evaluate()) {

                    // if LHS evaluates to false track history for each unknown variable

                    for (int i = 0; i < permutation.length; i++) {
                        result.record(unknownLhs.get(i), permutation[i]);
                    }

                }
            }
        }

        for (String statement : request.getFormula().getStatements()) {
            if (!result.containsKnown(statement) && !result.containsUnknown(statement)) {
                result.set(statement, -1);
            }
        }

        return result;
    }
}

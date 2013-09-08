package ee.moo.skynet.inference;

import ee.moo.skynet.formula.Formula;
import ee.moo.skynet.util.PermutationIterator;

import java.util.List;

/**
 * User: tarmo
 * Date: 5/31/13
 * Time: 1:58 PM
 */
public class InferenceStrategyMT extends InferenceStrategy {

    @Override
    public InferenceResult apply(InferenceRequest request) {

        InferenceResult result = new InferenceResult(request.getValues());

        Formula lhs = request.getFormula().getLeft();
        Formula rhs = request.getFormula().getRight();

        List<String> unknown = getUnknown(lhs, request.getValues());

        // no unknowns in LHS, no need to try to infere anything
        if (unknown.isEmpty()) {
            for (String statement : request.getFormula().getStatements()) {
                if (!result.containsKnown(statement)) {
                    result.set(statement, -1);
                }
            }

            return result;
        }

        if (!isAlwaysFalse(rhs, request.getValues())) {

            // found an interpretation that is true, therefore we consider
            // the inference as a failure and we can stop here

            for (String statement : request.getFormula().getStatements()) {
                if (!result.containsKnown(statement)) {
                    result.set(statement, -1);
                }
            }

            return result;
        }

        PermutationIterator iterator = new PermutationIterator(unknown.size());

        while (iterator.hasNext()) {

            int[] permutation = iterator.next();

            // set known values for LHS
            for (String key : request.getValues().keySet()) {
                lhs.setValue(key, request.getValues().get(key));
            }

            // set unknown values for LHS based on permutations
            for (int i = 0; i < permutation.length; i++) {
                lhs.setValue(unknown.get(i), permutation[i]);
            }

            if (!lhs.evaluate()) {

                // if lhs evaluates to false track history for each unknown variable

                for (int i = 0; i < permutation.length; i++) {
                    result.record(unknown.get(i), permutation[i]);
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

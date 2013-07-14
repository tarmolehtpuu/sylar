package ee.moo.skynet.inference;

import ee.moo.skynet.formula.Formula;
import ee.moo.skynet.util.PermutationIterator;

import java.util.List;
import java.util.Map;

/**
 * User: tarmo
 * Date: 5/31/13
 * Time: 1:57 PM
 */
public class InferenceStrategyMP extends InferenceStrategy {

    @Override
    public InferenceResult apply(InferenceRequest request) {

        InferenceResult result = new InferenceResult(request.getValues());

        Formula lhs = request.getFormula().getLeft();
        Formula rhs = request.getFormula().getRight();

        List<String> unknown = getUnknown(request.getFormula().getRight(), request.getValues());

        // no unknowns in RHS, no need to try to infere anything
        if (unknown.isEmpty()) {
            for (String statement : request.getFormula().getStatements()) {
                if (!result.contains(statement)) {
                    result.set(statement, -1);
                }
            }

            return result;
        }

        // detect if LHS is always true, based on already known values

        if (!isAlwaysTrue(lhs, request.getValues())) {

            // found an interpretation that isn't true, therefore we consider
            // the inference as a failure and we can stop here

            for (String statement : request.getFormula().getStatements()) {
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

        PermutationIterator iterator = new PermutationIterator(unknown.size());

        while (iterator.hasNext()) {

            int[] permutation = iterator.next();

            // set known values for RHS
            for (String key : request.getValues().keySet()) {
                rhs.setValue(key, request.getValues().get(key));
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
}

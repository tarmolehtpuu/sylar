package ee.moo.skynet.inference;

import ee.moo.skynet.formula.Formula;
import ee.moo.skynet.util.PermutationIterator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * User: tarmo
 * Date: 10/13/13
 * Time: 11:29 PM
 */
public class InferenceHelper {

    public static List<String> getUnknown(Formula formula, Map<String, Integer> values) {

        List<String> unknown = new ArrayList<String>();

        for (String statement : formula.getStatements()) {
            if (!values.containsKey(statement)) {
                unknown.add(statement);
            }
        }

        return unknown;
    }

    public static boolean isAlwaysTrue(Formula formula, Map<String, Integer> values) {

        List<String> unknown = new ArrayList<String>();

        // figure out the unknown values for LHS
        for (String statement : formula.getStatements()) {
            if (!values.containsKey(statement)) {
                unknown.add(statement);
            }
        }

        PermutationIterator iterator = new PermutationIterator(unknown.size());

        while (iterator.hasNext()) {

            int[] permutation = iterator.next();

            // set known values
            for (String key : values.keySet()) {
                formula.setValue(key, values.get(key));
            }

            // set unknown values from permutations
            for (int i = 0; i < permutation.length; i++) {
                formula.setValue(unknown.get(i), permutation[i]);
            }

            if (!formula.evaluate()) {
                return false;
            }
        }

        return true;
    }

    public static boolean isAlwaysFalse(Formula formula, Map<String, Integer> values) {

        List<String> unknown = new ArrayList<String>();

        // figure out the unknown values for LHS
        for (String statement : formula.getStatements()) {
            if (!values.containsKey(statement)) {
                unknown.add(statement);
            }
        }

        PermutationIterator iterator = new PermutationIterator(unknown.size());

        while (iterator.hasNext()) {

            int[] permutation = iterator.next();

            // set known values
            for (String key : values.keySet()) {
                formula.setValue(key, values.get(key));
            }

            // set unknown values from permutations
            for (int i = 0; i < permutation.length; i++) {
                formula.setValue(unknown.get(i), permutation[i]);
            }

            if (formula.evaluate()) {
                return false;
            }
        }

        return true;
    }
}

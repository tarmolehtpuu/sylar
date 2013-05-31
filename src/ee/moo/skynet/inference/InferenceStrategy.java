package ee.moo.skynet.inference;

import ee.moo.skynet.formula.Formula;
import ee.moo.skynet.util.BinaryUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: tarmo
 * Date: 5/31/13
 * Time: 1:57 PM
 */
public abstract class InferenceStrategy {

    public abstract InferenceResult apply(Formula formula, Map<String, Integer> values);

    protected List<String> getUnknown(Formula formula, Map<String, Integer> values) {

        List<String> unknown = new ArrayList<String>();

        for (String statement : formula.getStatements()) {
            if (!values.containsKey(statement)) {
                unknown.add(statement);
            }
        }

        return unknown;
    }

    protected boolean isAlwaysTrue(Formula formula, Map<String, Integer> values) {

        List<String> unknown = new ArrayList<String>();

        // figure out the unknown values for LHS
        for (String statement : formula.getStatements()) {
            if (!values.containsKey(statement)) {
                unknown.add(statement);
            }
        }

        for (int[] permutation : BinaryUtil.permutations(unknown.size())) {

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

    protected boolean isAlwaysFalse(Formula formula, Map<String, Integer> values) {

        List<String> unknown = new ArrayList<String>();

        // figure out the unknown values for LHS
        for (String statement : formula.getStatements()) {
            if (!values.containsKey(statement)) {
                unknown.add(statement);
            }
        }

        for (int[] permutation : BinaryUtil.permutations(unknown.size())) {

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

    protected Map<String, List<Integer>> getHistoryContainer(List<String> values) {

        Map<String, List<Integer>> history = new HashMap<String, List<Integer>>(values.size());

        for (String value : values) {
            history.put(value, new ArrayList<Integer>());
        }

        return history;
    }

}

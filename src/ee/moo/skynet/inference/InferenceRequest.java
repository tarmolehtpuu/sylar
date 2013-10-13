package ee.moo.skynet.inference;

import ee.moo.skynet.formula.Formula;
import ee.moo.skynet.formula.FormulaCollection;

import java.math.BigInteger;
import java.util.Map;

/**
 * User: tarmo
 * Date: 7/14/13
 * Time: 9:15 PM
 */
public class InferenceRequest {

    private Formula formula;

    private Map<String, Integer> values;

    private BigInteger min;

    private BigInteger max;

    public InferenceRequest() {
    }

    public InferenceRequest(FormulaCollection collection) {
    }

    public InferenceRequest(Formula formula) {
        this.formula = formula;
    }

    public InferenceRequest(Formula formula, Map<String, Integer> values) {
        this.formula = formula;
        this.values = values;
    }

    public InferenceRequest(Formula formula, Map<String, Integer> values, BigInteger min, BigInteger max) {
        this.formula = formula;
        this.values = values;
        this.min = min;
        this.max = max;
    }

    public InferenceRequest(Map<String, Integer> values) {
        this.values = values;
    }

    public Formula getFormula() {
        return formula;
    }

    public void setFormula(Formula formula) {
        this.formula = formula;
    }

    public void setFormulaCollection(FormulaCollection collection) {
    }

    public Map<String, Integer> getValues() {
        return values;
    }

    public void setValues(Map<String, Integer> values) {
        this.values = values;
    }

    public void setValue(String name, boolean value) {
        setValue(name, value ? 1 : 0);
    }

    public void setValue(String name, Integer value) {
        values.put(name, value);
    }

    public BigInteger getMin() {
        return min;
    }

    public void setMin(BigInteger min) {
        this.min = min;
    }

    public BigInteger getMax() {
        return max;
    }

    public void setMax(BigInteger max) {
        this.max = max;
    }

}

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

    private static final String NAME_TRUE = "TRUE";
    private static final String NAME_FALSE = "FALSE";

    private Formula formula;

    private Map<String, Integer> values;

    private BigInteger min;

    private BigInteger max;

    public InferenceRequest() {
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

    public void setFormulaCollectionEQ(FormulaCollection collection) {

        if (values == null) {
            throw new InferenceException("InferenceRequest.values must be set before calling setFormulaCollectionEQ()");
        }

        // TODO: implement setFormulaCollectionEQ
    }

    public void setFormulaCollectionMP(FormulaCollection collection) {

        if (values == null) {
            throw new InferenceException("InferenceRequest.values must be set before calling setFormulaCollectionMP()");
        }

        FormulaCollection result = new FormulaCollection();

        for (int i = 0; i < collection.size(); i++) {

            Formula formula = collection.get(i);

            if (formula.isEquivalence()) {

                if (InferenceHelper.isAlwaysTrue(formula.getLeft(), values)) {
                    result.add(formula.getRight());

                } else if (InferenceHelper.isAlwaysTrue(formula.getRight(), values)) {
                    result.add(formula.getLeft());
                }

            } else if (formula.isImplication()) {

                if (InferenceHelper.isAlwaysTrue(formula.getLeft(), values)) {
                    result.add(formula.getRight());
                }

            } else {
                throw new InferenceException("Invalid formula encountered, expecting either implication or equivalence");
            }
        }

        if (result.isEmpty()) {
            throw new InferenceException("No valid formulas found for setFormulaCollectionMP()");
        }

        values.put(NAME_TRUE, 1);

        formula = new Formula(Formula.NodeType.IMPLICATION);
        formula.setLeft(new Formula(Formula.NodeType.STATEMENT));
        formula.getLeft().setName(NAME_TRUE);
        formula.setRight(Formula.parse(result.joinConjunction()));
    }

    public void setFormulaCollectionMT(FormulaCollection collection) {

        if (values == null) {
            throw new InferenceException("InferenceRequest.values must be set before calling setFormulaCollectionMT()");
        }

        FormulaCollection result = new FormulaCollection();

        for (int i = 0; i < collection.size(); i++) {

            Formula formula = collection.get(i);

            if (formula.isEquivalence()) {

                if (InferenceHelper.isAlwaysFalse(formula.getLeft(), values)) {
                    result.add(formula.getRight());

                } else if (InferenceHelper.isAlwaysFalse(formula.getRight(), values)) {
                    result.add(formula.getLeft());

                }

            } else if (formula.isImplication()) {

                if (InferenceHelper.isAlwaysFalse(formula.getRight(), values)) {
                    result.add(formula.getLeft());
                }

            } else {
                throw new InferenceException("Invalid formula encountered, expecting either implication or equivalence");
            }
        }

        if (result.isEmpty()) {
            throw new InferenceException("No valid formulas found for setFormulaCollectionMP()");
        }

        values.put(NAME_FALSE, 0);

        formula = new Formula(Formula.NodeType.IMPLICATION);
        formula.setLeft(Formula.parse(result.joinConjunction()));
        formula.setRight(new Formula(Formula.NodeType.STATEMENT));
        formula.getRight().setName(NAME_FALSE);
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

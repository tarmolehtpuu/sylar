package ee.moo.sylar.inference;

import ee.moo.sylar.formula.FormulaException;

/**
 * User: tarmo
 * Date: 9/8/13
 * Time: 3:44 PM
 */
public class InferenceException extends FormulaException {

    public InferenceException() {
        super();
    }

    public InferenceException(String s) {
        super(s);
    }

    public InferenceException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public InferenceException(Throwable throwable) {
        super(throwable);
    }
}

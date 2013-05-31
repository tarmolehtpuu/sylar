package ee.moo.skynet.generator;

import ee.moo.skynet.formula.FormulaException;

/**
 * User: tarmo
 * Date: 3/23/13
 * Time: 8:55 PM
 */
public class GeneratorException extends FormulaException {

    public GeneratorException() {
        super();
    }

    public GeneratorException(String s) {
        super(s);
    }

    public GeneratorException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public GeneratorException(Throwable throwable) {
        super(throwable);
    }
}

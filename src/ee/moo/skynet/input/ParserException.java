package ee.moo.skynet.input;

import ee.moo.skynet.formula.FormulaException;

/**
 * User: tarmo
 * Date: 3/23/13
 * Time: 9:33 PM
 */
public class ParserException extends FormulaException {

    public ParserException() {
        super();
    }

    public ParserException(String s) {
        super(s);
    }

    public ParserException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public ParserException(Throwable throwable) {
        super(throwable);
    }
}

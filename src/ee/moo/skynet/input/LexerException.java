package ee.moo.skynet.input;

import ee.moo.skynet.FormulaException;

/**
 * User: tarmo
 * Date: 3/23/13
 * Time: 10:18 PM
 */
public class LexerException extends FormulaException {

    public LexerException() {
        super();
    }

    public LexerException(String s) {
        super(s);
    }

    public LexerException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public LexerException(Throwable throwable) {
        super(throwable);
    }

}

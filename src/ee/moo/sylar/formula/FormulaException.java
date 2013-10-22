package ee.moo.sylar.formula;

/**
 * User: tarmo
 * Date: 3/23/13
 * Time: 8:55 PM
 */
public class FormulaException extends RuntimeException {

    public FormulaException() {
    }

    public FormulaException(String s) {
        super(s);
    }

    public FormulaException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public FormulaException(Throwable throwable) {
        super(throwable);
    }
}

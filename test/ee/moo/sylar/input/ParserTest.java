package ee.moo.sylar.input;

import ee.moo.sylar.formula.Formula;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * User: tarmo
 * Date: 10/21/13
 * Time: 4:29 PM
 */
public class ParserTest {

    private Parser parser;

    @Before
    public void setUp() {
        parser = new ParserFormula();
    }

    @Test(expected = ParserException.class)
    public void testMissingOperand() {
        parser.parse("A&");
    }

    @Test(expected = ParserException.class)
    public void testMissingRightParenthesis() {
        parser.parse("(A&B");
    }

    @Test
    public void testPrecedence() {
        Formula formula1 = parser.parse("AvB&C");
        Formula formula2 = parser.parse("(AvB)&C");

        assertTrue(formula1.isDisjunction());
        assertTrue(formula2.isConjunction());
    }
}

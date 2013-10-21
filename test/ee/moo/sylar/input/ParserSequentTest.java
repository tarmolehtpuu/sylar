package ee.moo.sylar.input;

import ee.moo.sylar.formula.Formula;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * User: tarmo
 * Date: 10/21/13
 * Time: 3:58 PM
 */
public class ParserSequentTest {

    private ParserSequent parser;

    @Before
    public void setUp() {
        parser = new ParserSequent();
    }

    @Test(expected = ParserException.class)
    public void testParseEmpty() {
        parser.parse("");
    }

    @Test(expected = ParserException.class)
    public void testParseWhitespace() {
        parser.parse("    ");
    }

    @Test
    public void testParse() {
        Formula formula = parser.parse("A,B,C→A,B,C");

        assertTrue(formula.isImplication());
        assertTrue(formula.getLeft().isConjunction());
        assertTrue(formula.getRight().isDisjunction());
    }

}

package ee.moo.sylar.input;

import ee.moo.sylar.formula.Formula;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

/**
 * User: tarmo
 * Date: 10/21/13
 * Time: 3:58 PM
 */
public class ParserFormulaTest {

    private ParserFormula parser;

    @Before
    public void setUp() {
        parser = new ParserFormula();
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
        Formula formula = parser.parse("(A v B ⊃ ¬C)⇔(B v A ⊃ ¬C)");

        assertTrue(formula.isEquivalence());
        assertTrue(formula.getLeft().isImplication());
        assertTrue(formula.getRight().isImplication());
        assertArrayEquals(new String[]{"A", "B", "C"}, formula.getStatements());
    }
}

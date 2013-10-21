package ee.moo.sylar.input;

import ee.moo.sylar.formula.Formula;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

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

    @Test(expected = ParserException.class)
    public void testParseOnlySequent() {
        parser.parse("→");
    }

    @Test
    public void testParse() {
        Formula formula = parser.parse("A,B,C→A,B,C");

        assertTrue(formula.isImplication());
        assertTrue(formula.getLeft().isConjunction());
        assertTrue(formula.getRight().isDisjunction());
    }

    @Test
    public void testParseEmptyLHS() {
        Formula formula = parser.parse("→A");

        assertTrue(formula.isStatement());
        assertEquals("A", formula.getName());
    }

    @Test
    public void testParseEmptyRHS() {
        Formula formula = parser.parse("A→");

        assertTrue(formula.isInversion());
        assertArrayEquals(new String[]{"A"}, formula.getStatements());
    }

}

package ee.moo.sylar.alphabet;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * User: tarmo
 * Date: 10/15/13
 * Time: 6:38 PM
 */
public class AlphabetFormulaTest {

    private AlphabetFormula alphabet;

    @Before
    public void setUp() throws Exception {
        alphabet = new AlphabetFormula();
    }

    @Test
    public void testIsValid() {
        assertTrue(alphabet.isValid("!AvB&C⊃(C⇔E)"));
    }

    @Test
    public void testIsValidEmpty() {
        assertFalse(alphabet.isValid(null));
        assertFalse(alphabet.isValid(""));
    }

    @Test
    public void testIsValidFalse() {
        assertFalse(alphabet.isValid("A,B→C,D"));
    }

    @Test
    public void testIsSymbolStatement() {
        assertTrue(alphabet.isSymbolStatement('A'));
        assertTrue(alphabet.isSymbolStatement("A"));
        assertTrue(alphabet.isSymbolStatement("AA"));
        assertTrue(alphabet.isSymbolStatement("1234"));
        assertFalse(alphabet.isSymbolStatement('a'));
        assertFalse(alphabet.isSymbolStatement("a"));
        assertFalse(alphabet.isSymbolStatement("aa"));
    }

    @Test
    public void testIsSymbolInversion() {
        assertTrue(alphabet.isSymbolInversion('!'));
        assertTrue(alphabet.isSymbolInversion("!"));
        assertTrue(alphabet.isSymbolInversion("¬"));
        assertTrue(alphabet.isSymbolInversion('¬'));
        assertFalse(alphabet.isSymbolInversion("!!"));
        assertFalse(alphabet.isSymbolInversion("¬¬"));
    }

    @Test
    public void testIsSymbolConjunction() {
        assertTrue(alphabet.isSymbolConjunction('&'));
        assertTrue(alphabet.isSymbolConjunction('∧'));
        assertTrue(alphabet.isSymbolConjunction("&"));
        assertTrue(alphabet.isSymbolConjunction("∧"));
        assertFalse(alphabet.isSymbolConjunction("&&"));
        assertFalse(alphabet.isSymbolConjunction("∧∧"));
    }

    @Test
    public void testIsSymbolDisjunction() {
        assertTrue(alphabet.isSymbolDisjunction('v'));
        assertTrue(alphabet.isSymbolDisjunction('∨'));
        assertTrue(alphabet.isSymbolDisjunction("v"));
        assertTrue(alphabet.isSymbolDisjunction("∨"));
        assertFalse(alphabet.isSymbolDisjunction("vv"));
        assertFalse(alphabet.isSymbolDisjunction("∨∨"));
    }

    @Test
    public void testIsSymbolImplication() {
        assertTrue(alphabet.isSymbolImplication('⊃'));
        assertTrue(alphabet.isSymbolImplication('⇒'));
        assertTrue(alphabet.isSymbolImplication("⊃"));
        assertTrue(alphabet.isSymbolImplication("⇒"));
        assertFalse(alphabet.isSymbolImplication("⊃⊃"));
        assertFalse(alphabet.isSymbolImplication("⇒⇒"));
    }

    @Test
    public void testIsSymbolEquivalence() {
        assertTrue(alphabet.isSymbolEquivalence('⇔'));
        assertTrue(alphabet.isSymbolEquivalence('↔'));
        assertTrue(alphabet.isSymbolEquivalence('='));
        assertTrue(alphabet.isSymbolEquivalence("⇔"));
        assertTrue(alphabet.isSymbolEquivalence("↔"));
        assertTrue(alphabet.isSymbolEquivalence("="));
        assertFalse(alphabet.isSymbolEquivalence("⇔⇔"));
        assertFalse(alphabet.isSymbolEquivalence("↔↔"));
        assertFalse(alphabet.isSymbolEquivalence("=="));
    }

    @Test
    public void testIsSymbolLeftParenthesis() {
        assertTrue(alphabet.isSymbolLeftParenthesis('('));
        assertTrue(alphabet.isSymbolLeftParenthesis('['));
        assertTrue(alphabet.isSymbolLeftParenthesis('{'));
        assertTrue(alphabet.isSymbolLeftParenthesis("("));
        assertTrue(alphabet.isSymbolLeftParenthesis("["));
        assertTrue(alphabet.isSymbolLeftParenthesis("{"));
        assertFalse(alphabet.isSymbolLeftParenthesis("(("));
        assertFalse(alphabet.isSymbolLeftParenthesis("[["));
        assertFalse(alphabet.isSymbolLeftParenthesis("{{"));
    }

    @Test
    public void testIsSymbolRightParenthesis() {
        assertTrue(alphabet.isSymbolRightParenthesis(')'));
        assertTrue(alphabet.isSymbolRightParenthesis(']'));
        assertTrue(alphabet.isSymbolRightParenthesis('}'));
        assertTrue(alphabet.isSymbolRightParenthesis(")"));
        assertTrue(alphabet.isSymbolRightParenthesis("]"));
        assertTrue(alphabet.isSymbolRightParenthesis("}"));
        assertFalse(alphabet.isSymbolRightParenthesis("))"));
        assertFalse(alphabet.isSymbolRightParenthesis("}}"));
        assertFalse(alphabet.isSymbolRightParenthesis("]]"));
    }

    @Test
    public void testIsSymbolWhitespace() {
        assertTrue(alphabet.isSymbolWhitespace(' '));
        assertTrue(alphabet.isSymbolWhitespace('\t'));
        assertTrue(alphabet.isSymbolWhitespace('\n'));
        assertTrue(alphabet.isSymbolWhitespace('\r'));
        assertTrue(alphabet.isSymbolWhitespace(" "));
        assertTrue(alphabet.isSymbolWhitespace("\t"));
        assertTrue(alphabet.isSymbolWhitespace("\n"));
        assertTrue(alphabet.isSymbolWhitespace("\r"));
        assertFalse(alphabet.isSymbolWhitespace("  "));
        assertFalse(alphabet.isSymbolWhitespace("\t\t"));
        assertFalse(alphabet.isSymbolWhitespace("\n\n"));
        assertFalse(alphabet.isSymbolWhitespace("\r\r"));
    }

    @Test
    public void testGetSymbolInversion() {
        assertEquals('¬', alphabet.getSymbolInversion());
        assertEquals("¬", alphabet.getSymbolInversionString());
    }

    @Test
    public void testGetSymbolConjunction() {
        assertEquals('&', alphabet.getSymbolConjunction());
        assertEquals("&", alphabet.getSymbolConjunctionString());
    }

    @Test
    public void testGetSymbolDisjunction() {
        assertEquals('∨', alphabet.getSymbolDisjunction());
        assertEquals("∨", alphabet.getSymbolDisjunctionString());
    }

    @Test
    public void testGetSymbolImplication() {
        assertEquals('⊃', alphabet.getSymbolImplication());
        assertEquals("⊃", alphabet.getSymbolImplicationString());
    }

    @Test
    public void testGetSymbolEquivalence() {
        assertEquals('⇔', alphabet.getSymbolEquivalence());
        assertEquals("⇔", alphabet.getSymbolEquivalenceString());
    }

    @Test
    public void testGetSymbolLeftParenthesis() {
        assertEquals('(', alphabet.getSymbolLeftParenthesis());
        assertEquals("(", alphabet.getSymbolLeftParenthesisString());
    }

    @Test
    public void testGetSymbolRightParenthesis() {
        assertEquals(')', alphabet.getSymbolRightParenthesis());
        assertEquals(")", alphabet.getSymbolRightParenthesisString());
    }

    @Test
    public void testGetSymbolWhitespace() {
        assertEquals(' ', alphabet.getSymbolWhitespace());
        assertEquals(" ", alphabet.getSymbolWhitespaceString());
    }
}

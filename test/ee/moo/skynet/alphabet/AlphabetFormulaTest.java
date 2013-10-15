package ee.moo.skynet.alphabet;

import junit.framework.TestCase;

/**
 * User: tarmo
 * Date: 10/15/13
 * Time: 6:38 PM
 */
public class AlphabetFormulaTest extends TestCase {

    private AlphabetFormula alphabet;

    @Override
    protected void setUp() throws Exception {
        alphabet = new AlphabetFormula();
    }

    public void testIsValid() {
        assertTrue(alphabet.isValid("!AvB&C⊃(C⇔E)"));
    }

    public void testIsValidEmpty() {
        assertFalse(alphabet.isValid(null));
        assertFalse(alphabet.isValid(""));
    }

    public void testIsValidFalse() {
        assertFalse(alphabet.isValid("A,B→C,D"));
    }

    public void testIsSymbolStatement() {
        assertTrue(alphabet.isSymbolStatement('A'));
        assertTrue(alphabet.isSymbolStatement("A"));
        assertTrue(alphabet.isSymbolStatement("AA"));
        assertTrue(alphabet.isSymbolStatement("1234"));
        assertFalse(alphabet.isSymbolStatement('a'));
        assertFalse(alphabet.isSymbolStatement("a"));
        assertFalse(alphabet.isSymbolStatement("aa"));
    }

    public void testIsSymbolInversion() {
        assertTrue(alphabet.isSymbolInversion('!'));
        assertTrue(alphabet.isSymbolInversion("!"));
        assertTrue(alphabet.isSymbolInversion("¬"));
        assertTrue(alphabet.isSymbolInversion('¬'));
        assertFalse(alphabet.isSymbolInversion("!!"));
        assertFalse(alphabet.isSymbolInversion("¬¬"));
    }

    public void testIsSymbolConjunction() {
        assertTrue(alphabet.isSymbolConjunction('&'));
        assertTrue(alphabet.isSymbolConjunction('∧'));
        assertTrue(alphabet.isSymbolConjunction("&"));
        assertTrue(alphabet.isSymbolConjunction("∧"));
        assertFalse(alphabet.isSymbolConjunction("&&"));
        assertFalse(alphabet.isSymbolConjunction("∧∧"));
    }

    public void testIsSymbolDisjunction() {
        assertTrue(alphabet.isSymbolDisjunction('v'));
        assertTrue(alphabet.isSymbolDisjunction('∨'));
        assertTrue(alphabet.isSymbolDisjunction("v"));
        assertTrue(alphabet.isSymbolDisjunction("∨"));
        assertFalse(alphabet.isSymbolDisjunction("vv"));
        assertFalse(alphabet.isSymbolDisjunction("∨∨"));
    }

    public void testIsSymbolImplication() {
        assertTrue(alphabet.isSymbolImplication('⊃'));
        assertTrue(alphabet.isSymbolImplication('⇒'));
        assertTrue(alphabet.isSymbolImplication("⊃"));
        assertTrue(alphabet.isSymbolImplication("⇒"));
        assertFalse(alphabet.isSymbolImplication("⊃⊃"));
        assertFalse(alphabet.isSymbolImplication("⇒⇒"));
    }

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

    public void testGetSymbolInversion() {
        assertEquals('¬', alphabet.getSymbolInversion());
        assertEquals("¬", alphabet.getSymbolInversionString());
    }

    public void testGetSymbolConjunction() {
        assertEquals('&', alphabet.getSymbolConjunction());
        assertEquals("&", alphabet.getSymbolConjunctionString());
    }

    public void testGetSymbolDisjunction() {
        assertEquals('∨', alphabet.getSymbolDisjunction());
        assertEquals("∨", alphabet.getSymbolDisjunctionString());
    }

    public void testGetSymbolImplication() {
        assertEquals('⊃', alphabet.getSymbolImplication());
        assertEquals("⊃", alphabet.getSymbolImplicationString());
    }

    public void testGetSymbolEquivalence() {
        assertEquals('⇔', alphabet.getSymbolEquivalence());
        assertEquals("⇔", alphabet.getSymbolEquivalenceString());
    }

    public void testGetSymbolLeftParenthesis() {
        assertEquals('(', alphabet.getSymbolLeftParenthesis());
        assertEquals("(", alphabet.getSymbolLeftParenthesisString());
    }

    public void testGetSymbolRightParenthesis() {
        assertEquals(')', alphabet.getSymbolRightParenthesis());
        assertEquals(")", alphabet.getSymbolRightParenthesisString());
    }

    public void testGetSymbolWhitespace() {
        assertEquals(' ', alphabet.getSymbolWhitespace());
        assertEquals(" ", alphabet.getSymbolWhitespaceString());
    }
}

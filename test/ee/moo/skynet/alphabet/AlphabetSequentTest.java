package ee.moo.skynet.alphabet;

import junit.framework.TestCase;

/**
 * User: tarmo
 * Date: 10/15/13
 * Time: 6:46 PM
 */
public class AlphabetSequentTest extends TestCase {

    private AlphabetSequent alphabet;

    protected void setUp() {
        alphabet = new AlphabetSequent();
    }

    public void testIsValid() {
        assertTrue(alphabet.isValid("A,B→C,!D"));
    }

    public void testIsValidEmpty() {
        assertFalse(alphabet.isValid(null));
        assertFalse(alphabet.isValid(""));
    }

    public void testIsValidFalse() {
        assertFalse(alphabet.isValid("a,b->c,d"));
    }

    public void testIsSymbolComma() {
        assertTrue(alphabet.isSymbolComma(','));
        assertTrue(alphabet.isSymbolComma(","));
        assertFalse(alphabet.isSymbolComma('.'));
        assertFalse(alphabet.isSymbolComma(",,"));
    }

    public void testIsSymbolSequent() {
        assertTrue(alphabet.isSymbolSequent('→'));
        assertTrue(alphabet.isSymbolSequent('⊢'));
        assertTrue(alphabet.isSymbolSequent("→"));
        assertTrue(alphabet.isSymbolSequent("⊢"));
        assertFalse(alphabet.isSymbolSequent("→→"));
        assertFalse(alphabet.isSymbolSequent("⊢⊢"));
    }

    public void testGetSymbolComma() {
        assertEquals(',', alphabet.getSymbolComma());
        assertEquals(",", alphabet.getSymbolCommaString());
    }

    public void testGetSymbolSequent() {
        assertEquals('→', alphabet.getSymbolSequent());
        assertEquals("→", alphabet.getSymbolSequentString());
    }
}

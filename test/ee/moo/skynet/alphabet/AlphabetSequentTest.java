package ee.moo.skynet.alphabet;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * User: tarmo
 * Date: 10/15/13
 * Time: 6:46 PM
 */
public class AlphabetSequentTest {

    private AlphabetSequent alphabet;

    @Before
    public void setUp() {
        alphabet = new AlphabetSequent();
    }

    @Test
    public void testIsValid() {
        assertTrue(alphabet.isValid("A,B→C,!D"));
    }

    @Test
    public void testIsValidEmpty() {
        assertFalse(alphabet.isValid(null));
        assertFalse(alphabet.isValid(""));
    }

    @Test
    public void testIsValidFalse() {
        assertFalse(alphabet.isValid("a,b->c,d"));
    }

    @Test
    public void testIsSymbolComma() {
        assertTrue(alphabet.isSymbolComma(','));
        assertTrue(alphabet.isSymbolComma(","));
        assertFalse(alphabet.isSymbolComma('.'));
        assertFalse(alphabet.isSymbolComma(",,"));
    }

    @Test
    public void testIsSymbolSequent() {
        assertTrue(alphabet.isSymbolSequent('→'));
        assertTrue(alphabet.isSymbolSequent('⊢'));
        assertTrue(alphabet.isSymbolSequent("→"));
        assertTrue(alphabet.isSymbolSequent("⊢"));
        assertFalse(alphabet.isSymbolSequent("→→"));
        assertFalse(alphabet.isSymbolSequent("⊢⊢"));
    }

    @Test
    public void testGetSymbolComma() {
        assertEquals(',', alphabet.getSymbolComma());
        assertEquals(",", alphabet.getSymbolCommaString());
    }

    @Test
    public void testGetSymbolSequent() {
        assertEquals('→', alphabet.getSymbolSequent());
        assertEquals("→", alphabet.getSymbolSequentString());
    }
}

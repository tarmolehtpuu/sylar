package ee.moo.skynet.input;

import ee.moo.skynet.alphabet.AlphabetFormula;
import ee.moo.skynet.alphabet.AlphabetSequent;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * User: tarmo
 * Date: 10/21/13
 * Time: 8:30 AM
 */
public class LexerTest {

    @Test(expected = LexerException.class)
    public void testInvalid() {
        new Lexer("-", new AlphabetFormula()).next();
    }

    @Test
    public void testEmpty() {
        assertNull(new Lexer("", new AlphabetFormula()).next());
    }

    @Test
    public void testStatement() {
        assertEquals(TokenType.STATEMENT, new Lexer("FOO", new AlphabetFormula()).next().getType());
        assertEquals("FOO", new Lexer("FOO", new AlphabetFormula()).next().getData());
    }

    @Test
    public void testConjunction() {
        assertEquals(TokenType.CONJUNCTION, new Lexer("∧", new AlphabetFormula()).next().getType());
        assertEquals(TokenType.CONJUNCTION, new Lexer("&", new AlphabetFormula()).next().getType());
    }

    @Test
    public void testDisjunction() {
        assertEquals(TokenType.DISJUNCTION, new Lexer("v", new AlphabetFormula()).next().getType());
        assertEquals(TokenType.DISJUNCTION, new Lexer("∨", new AlphabetFormula()).next().getType());
    }

    @Test
    public void testImplication() {
        assertEquals(TokenType.IMPLICATION, new Lexer("⇒", new AlphabetFormula()).next().getType());
        assertEquals(TokenType.IMPLICATION, new Lexer("⊃", new AlphabetFormula()).next().getType());
    }

    @Test
    public void testEquivalence() {
        assertEquals(TokenType.EQUIVALENCE, new Lexer("⇔", new AlphabetFormula()).next().getType());
        assertEquals(TokenType.EQUIVALENCE, new Lexer("↔", new AlphabetFormula()).next().getType());
        assertEquals(TokenType.EQUIVALENCE, new Lexer("=", new AlphabetFormula()).next().getType());
    }

    @Test
    public void testParenthesis() {
        assertEquals(TokenType.LEFTPAREN, new Lexer("(", new AlphabetFormula()).next().getType());
        assertEquals(TokenType.LEFTPAREN, new Lexer("[", new AlphabetFormula()).next().getType());
        assertEquals(TokenType.LEFTPAREN, new Lexer("{", new AlphabetFormula()).next().getType());
        assertEquals(TokenType.RIGHTPAREN, new Lexer(")", new AlphabetFormula()).next().getType());
        assertEquals(TokenType.RIGHTPAREN, new Lexer("]", new AlphabetFormula()).next().getType());
        assertEquals(TokenType.RIGHTPAREN, new Lexer("}", new AlphabetFormula()).next().getType());
    }

    @Test
    public void testWhitespace() {
        assertEquals(TokenType.WHITESPACE, new Lexer(" ", new AlphabetFormula()).next().getType());
        assertEquals(TokenType.WHITESPACE, new Lexer("\n", new AlphabetFormula()).next().getType());
        assertEquals(TokenType.WHITESPACE, new Lexer("\r", new AlphabetFormula()).next().getType());
        assertEquals(TokenType.WHITESPACE, new Lexer("\t", new AlphabetFormula()).next().getType());
    }

    @Test
    public void testComma() {
        assertEquals(TokenType.COMMA, new Lexer(",", new AlphabetSequent()).next().getType());
    }


    @Test
    public void testSequent() {
        assertEquals(TokenType.SEQUENT, new Lexer("→", new AlphabetSequent()).next().getType());
        assertEquals(TokenType.SEQUENT, new Lexer("⊢", new AlphabetSequent()).next().getType());
    }
}

package ee.moo.sylar.input;

import ee.moo.sylar.formula.Formula;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * User: tarmo
 * Date: 10/21/13
 * Time: 8:56 AM
 */
public class TokenTest {

    @Test
    public void testType() {
        assertEquals(TokenType.IMPLICATION, new Token(TokenType.IMPLICATION).getType());
    }

    @Test
    public void testData() {
        Token token = new Token(TokenType.STATEMENT, "P");
        assertEquals(TokenType.STATEMENT, token.getType());
        assertEquals("P", token.getData());
    }

    @Test
    public void testPrecedence() {
        assertEquals(6, new Token(TokenType.INVERSION).getPrecedence());
        assertEquals(5, new Token(TokenType.CONJUNCTION).getPrecedence());
        assertEquals(4, new Token(TokenType.DISJUNCTION).getPrecedence());
        assertEquals(3, new Token(TokenType.IMPLICATION).getPrecedence());
        assertEquals(2, new Token(TokenType.EQUIVALENCE).getPrecedence());
        assertEquals(1, new Token(TokenType.COMMA).getPrecedence());
        assertEquals(0, new Token(TokenType.SEQUENT).getPrecedence());
        assertEquals(0, new Token(TokenType.WHITESPACE).getPrecedence());
        assertEquals(0, new Token(TokenType.LEFTPAREN).getPrecedence());
        assertEquals(0, new Token(TokenType.RIGHTPAREN).getPrecedence());
    }

    @Test
    public void testNodeType() {
        assertEquals(Formula.NodeType.STATEMENT, new Token(TokenType.STATEMENT, "P").getNodeType(Token.Side.LHS));
        assertEquals(Formula.NodeType.INVERSION, new Token(TokenType.INVERSION).getNodeType(Token.Side.LHS));
        assertEquals(Formula.NodeType.CONJUNCTION, new Token(TokenType.CONJUNCTION).getNodeType(Token.Side.LHS));
        assertEquals(Formula.NodeType.CONJUNCTION, new Token(TokenType.COMMA).getNodeType(Token.Side.LHS));
        assertEquals(Formula.NodeType.DISJUNCTION, new Token(TokenType.DISJUNCTION).getNodeType(Token.Side.LHS));
        assertEquals(Formula.NodeType.DISJUNCTION, new Token(TokenType.COMMA).getNodeType(Token.Side.RHS));
        assertEquals(Formula.NodeType.IMPLICATION, new Token(TokenType.IMPLICATION).getNodeType(Token.Side.LHS));
        assertEquals(Formula.NodeType.EQUIVALENCE, new Token(TokenType.EQUIVALENCE).getNodeType(Token.Side.LHS));
    }

    @Test(expected = ParserException.class)
    public void testNodeTypeNone() {
        new Token(TokenType.COMMA).getNodeType(Token.Side.NONE);
    }

    @Test(expected = ParserException.class)
    public void testNodeTypeWhitespace() {
        new Token(TokenType.WHITESPACE).getNodeType(Token.Side.LHS);
    }

    @Test(expected = ParserException.class)
    public void testNodeTypeLeftParenthesis() {
        new Token(TokenType.LEFTPAREN).getNodeType(Token.Side.LHS);
    }

    @Test(expected = ParserException.class)
    public void testNodeTypeRightParenthesis() {
        new Token(TokenType.RIGHTPAREN).getNodeType(Token.Side.LHS);
    }

    @Test(expected = ParserException.class)
    public void testNodeTypeSequent() {
        new Token(TokenType.SEQUENT).getNodeType(Token.Side.LHS);
    }

    @Test
    public void testIsStatement() {
        assertTrue(new Token(TokenType.STATEMENT, "P").isStatement());
    }

    @Test
    public void testIsInversion() {
        assertTrue(new Token(TokenType.INVERSION).isInversion());
    }

    @Test
    public void testIsConjunction() {
        assertTrue(new Token(TokenType.CONJUNCTION).isConjunction());
    }

    @Test
    public void testIsDisjunction() {
        assertTrue(new Token(TokenType.DISJUNCTION).isDisjunction());
    }

    @Test
    public void testIsImplication() {
        assertTrue(new Token(TokenType.IMPLICATION).isImplication());
    }

    @Test
    public void testIsEquivalence() {
        assertTrue(new Token(TokenType.EQUIVALENCE).isEquivalence());
    }

    @Test
    public void testIsLeftParenthesis() {
        assertTrue(new Token(TokenType.LEFTPAREN).isLeftParenthesis());
    }

    @Test
    public void testIsRightParenthesis() {
        assertTrue(new Token(TokenType.RIGHTPAREN).isRightParenthesis());
    }

    @Test
    public void testIsWhitespace() {
        assertTrue(new Token(TokenType.WHITESPACE).isWhitespace());
    }

    @Test
    public void testIsComma() {
        assertTrue(new Token(TokenType.COMMA).isComma());
    }

    @Test
    public void testIsSequent() {
        assertTrue(new Token(TokenType.SEQUENT).isSequent());
    }

    @Test
    public void testIsUnary() {
        assertTrue(new Token(TokenType.INVERSION).isUnary());
        assertFalse(new Token(TokenType.CONJUNCTION).isUnary());
        assertFalse(new Token(TokenType.DISJUNCTION).isUnary());
        assertFalse(new Token(TokenType.IMPLICATION).isUnary());
        assertFalse(new Token(TokenType.EQUIVALENCE).isUnary());
    }

    @Test
    public void testIsBinary() {
        assertTrue(new Token(TokenType.CONJUNCTION).isBinary());
        assertTrue(new Token(TokenType.DISJUNCTION).isBinary());
        assertTrue(new Token(TokenType.IMPLICATION).isBinary());
        assertTrue(new Token(TokenType.EQUIVALENCE).isBinary());
        assertFalse(new Token(TokenType.INVERSION).isBinary());
    }

    @Test
    public void testToString() {
        assertEquals("Token[type = STATEMENT, data = P]", new Token(TokenType.STATEMENT, "P").toString());
    }

    @Test
    public void testToStringStatement() {
        assertEquals("Token[type = INVERSION]", new Token(TokenType.INVERSION).toString());
        assertEquals("Token[type = CONJUNCTION]", new Token(TokenType.CONJUNCTION).toString());
        assertEquals("Token[type = DISJUNCTION]", new Token(TokenType.DISJUNCTION).toString());
        assertEquals("Token[type = IMPLICATION]", new Token(TokenType.IMPLICATION).toString());
        assertEquals("Token[type = EQUIVALENCE]", new Token(TokenType.EQUIVALENCE).toString());
        assertEquals("Token[type = LEFTPAREN]", new Token(TokenType.LEFTPAREN).toString());
        assertEquals("Token[type = RIGHTPAREN]", new Token(TokenType.RIGHTPAREN).toString());
        assertEquals("Token[type = WHITESPACE]", new Token(TokenType.WHITESPACE).toString());
        assertEquals("Token[type = COMMA]", new Token(TokenType.COMMA).toString());
        assertEquals("Token[type = SEQUENT]", new Token(TokenType.SEQUENT).toString());
    }
}

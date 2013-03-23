package ee.moo.skynet.input;

import ee.moo.skynet.alphabet.Alphabet;

import java.util.LinkedList;
import java.util.List;

/**
 * User: tarmo
 * Date: 3/23/13
 * Time: 4:48 PM
 */
public class Lexer {

    private List<Character> input;

    private Alphabet alphabet;

    public Lexer(String input, Alphabet alphabet) {

        this.input = new LinkedList<Character>();
        this.alphabet = alphabet;

        for (char c : input.toCharArray()) {

            if (!alphabet.isValid(c)) {
                throw new LexerException(String.format("Invalid character: %s", c));
            }

            this.input.add(c);
        }
    }

    public Token next() {

        if (input.size() == 0) {
            return null;
        }

        StringBuilder builder = new StringBuilder();

        while (input.size() > 0 && alphabet.isSymbolStatement(input.get(0))) {
            builder.append(input.remove(0));
        }

        if (builder.length() > 0) {
            return new Token(TokenType.STATEMENT, builder.toString());

        } else {

            char c = input.remove(0);

            if (alphabet.isSymbolInversion(c)) {
                return new Token(TokenType.INVERSION);

            } else if (alphabet.isSymbolConjunction(c)) {
                return new Token(TokenType.CONJUNCTION);

            } else if (alphabet.isSymbolDisjunction(c)) {
                return new Token(TokenType.DISJUNCTION);

            } else if (alphabet.isSymbolImplication(c)) {
                return new Token(TokenType.IMPLICATION);

            } else if (alphabet.isSymbolEquivalence(c)) {
                return new Token(TokenType.EQUIVALENCE);

            } else if (alphabet.isSymbolLeftParenthesis(c)) {
                return new Token(TokenType.LEFTPAREN);

            } else if (alphabet.isSymbolRightParenthesis(c)) {
                return new Token(TokenType.RIGHTPAREN);

            } else if (alphabet.isSymbolWhitespace(c)) {
                return new Token(TokenType.WHITESPACE);

            } else if (alphabet.isSymbolComma(c)) {
                return new Token(TokenType.COMMA);

            } else if (alphabet.isSymbolSequent(c)) {
                return new Token(TokenType.SEQUENT);

            } else {
                throw new LexerException(String.format("Invalid character: %s", c));
            }
        }
    }
}

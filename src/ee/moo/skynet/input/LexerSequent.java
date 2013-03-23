package ee.moo.skynet.input;

import ee.moo.skynet.alphabet.AlphabetSequent;

/**
 * User: tarmo
 * Date: 3/23/13
 * Time: 4:48 PM
 */
public class LexerSequent extends Lexer {

    private AlphabetSequent alphabet = new AlphabetSequent();

    public LexerSequent(String input) {
        super(input);
    }

    @Override
    public Token next() {

        if (input.size() == 0) {
            return null;
        }

        StringBuilder builder = new StringBuilder();

        while (input.size() > 0 && alphabet.isSymbolStatement(input.get(0))) {
            builder.append(input.remove(0));
        }

        if (builder.length() > 0) {
            return new TokenSequent(TokenType.STATEMENT, builder.toString());

        } else {

            char c = input.remove(0);

            if (alphabet.isSymbolInversion(c)) {
                return new TokenSequent(TokenType.INVERSION);

            } else if (alphabet.isSymbolConjunction(c)) {
                return new TokenSequent(TokenType.CONJUNCTION);

            } else if (alphabet.isSymbolDisjunction(c)) {
                return new TokenSequent(TokenType.DISJUNCTION);

            } else if (alphabet.isSymbolImplication(c)) {
                return new TokenSequent(TokenType.IMPLICATION);

            } else if (alphabet.isSymbolEquivalence(c)) {
                return new TokenSequent(TokenType.EQUIVALENCE);

            } else if (alphabet.isSymbolLeftParenthesis(c)) {
                return new TokenSequent(TokenType.LEFTPAREN);

            } else if (alphabet.isSymbolRightParenthesis(c)) {
                return new TokenSequent(TokenType.RIGHTPAREN);

            } else if (alphabet.isSymbolWhitespace(c)) {
                return new TokenSequent(TokenType.WHITESPACE);

            } else if (alphabet.isSymbolComma(c)) {
                return new TokenSequent(TokenType.COMMA);

            } else if (alphabet.isSymbolSequent(c)) {
                return new TokenSequent(TokenType.SEQUENT);

            } else {
                throw new ParserException(String.format("Unkown character token: %s", c));
            }
        }

    }
}

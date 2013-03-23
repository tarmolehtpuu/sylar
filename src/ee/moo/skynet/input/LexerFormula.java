package ee.moo.skynet.input;

import ee.moo.skynet.alphabet.AlphabetFormula;

/**
 * User: tarmo
 * Date: 3/23/13
 * Time: 4:48 PM
 */
public class LexerFormula extends Lexer {

    private AlphabetFormula alphabet = new AlphabetFormula();

    public LexerFormula(String input) {
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
            return new TokenFormula(TokenType.STATEMENT, builder.toString());

        } else {

            char c = input.remove(0);

            if (alphabet.isSymbolInversion(c)) {
                return new TokenFormula(TokenType.INVERSION);

            } else if (alphabet.isSymbolConjunction(c)) {
                return new TokenFormula(TokenType.CONJUNCTION);

            } else if (alphabet.isSymbolDisjunction(c)) {
                return new TokenFormula(TokenType.DISJUNCTION);

            } else if (alphabet.isSymbolImplication(c)) {
                return new TokenFormula(TokenType.IMPLICATION);

            } else if (alphabet.isSymbolEquivalence(c)) {
                return new TokenFormula(TokenType.EQUIVALENCE);

            } else if (alphabet.isSymbolLeftParenthesis(c)) {
                return new TokenFormula(TokenType.LEFTPAREN);

            } else if (alphabet.isSymbolRightParenthesis(c)) {
                return new TokenFormula(TokenType.RIGHTPAREN);

            } else if (alphabet.isSymbolWhitespace(c)) {
                return new TokenFormula(TokenType.WHITESPACE);

            } else {
                throw new ParserException(String.format("Unkown character token: %s", c));
            }
        }
    }

}

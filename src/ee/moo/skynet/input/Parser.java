package ee.moo.skynet.input;

import ee.moo.skynet.formula.Formula;

import java.util.List;

/**
 * User: tarmo
 * Date: 3/23/13
 * Time: 4:47 PM
 */
public abstract class Parser {

    public abstract Formula parse(String input);

    protected Formula parseExpression(List<Token> tokens, int precedence, Token.Side side) {

        Formula formula = parsePrimary(tokens, side);

        while (true) {

            if (tokens.isEmpty()) {
                break;
            }

            Token token = tokens.get(0);

            if (!token.isBinary()) {
                break;
            }

            if (token.getPrecedence() <= precedence) {
                break;
            }

            tokens.remove(0);

            if (tokens.isEmpty()) {
                throw new ParserException("Unexpected end of input");
            }

            formula = new Formula(token.getNodeType(side), formula, parseExpression(tokens, token.getPrecedence(), side));

        }

        return formula;
    }

    protected Formula parsePrimary(List<Token> tokens, Token.Side side) {

        if (tokens.isEmpty()) {
            throw new ParserException(String.format("Unexpected end of input, expecting: %s or %s or %s",
                    TokenType.STATEMENT, TokenType.LEFTPAREN, TokenType.INVERSION));
        }

        Token token = tokens.remove(0);

        if (token.isStatement()) {
            return new Formula(Formula.NodeType.STATEMENT, token.getData());

        } else if (token.isInversion()) {
            return new Formula(Formula.NodeType.INVERSION, parseExpression(tokens, token.getPrecedence(), side));

        } else if (token.isLeftParenthesis()) {

            Formula formula = parseExpression(tokens, 0, side);

            if (tokens.isEmpty()) {
                throw new ParserException(String.format("Unexpected end of input, expecting: %s", TokenType.RIGHTPAREN));
            }

            if (!tokens.remove(0).isRightParenthesis()) {
                throw new ParserException(String.format("Unexpected token: %s, expecting: %s", token, TokenType.RIGHTPAREN));
            }

            return formula;

        } else {
            throw new ParserException(String.format("Unexpected token: %s", token));
        }
    }
}

package ee.moo.skynet.input;

import ee.moo.skynet.Formula;
import ee.moo.skynet.alphabet.AlphabetFormula;

import java.util.ArrayList;
import java.util.List;

/**
 * User: tarmo
 * Date: 3/23/13
 * Time: 4:47 PM
 */
public class ParserFormula extends Parser {

    private AlphabetFormula alphabet = new AlphabetFormula();

    @Override
    public Formula parse(String input) {

        Lexer lexer = new Lexer(input, alphabet);
        Token token;

        List<Token> tokens = new ArrayList<Token>(input.length());

        while ((token = lexer.next()) != null) {

            if (token.getType() != TokenType.WHITESPACE) {
                tokens.add(token);
            }

        }

        Formula formula = parseExpression(tokens, 0);

        if (!tokens.isEmpty()) {
            throw new ParserException(String.format("Expecting end of input: %d token(s) remaining", tokens.size()));
        }

        return formula;
    }

    private Formula parseExpression(List<Token> tokens, int precedence) {

        Formula formula = parsePrimary(tokens);

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

            formula = new Formula(token.getNodeType(Token.Side.NONE), formula, parseExpression(tokens, token.getPrecedence()));


        }

        return formula;
    }

    private Formula parsePrimary(List<Token> tokens) {

        if (tokens.isEmpty()) {
            throw new ParserException(String.format("Unexpected end of input, expecting: %s or %s or %s",
                    TokenType.STATEMENT, TokenType.LEFTPAREN, TokenType.INVERSION));
        }

        Token token = tokens.remove(0);

        if (token.isStatement()) {
            return new Formula(Formula.NodeType.STATEMENT, token.getData());

        } else if (token.isInversion()) {
            return new Formula(Formula.NodeType.INVERSION, parseExpression(tokens, token.getPrecedence()));

        } else if (token.isLeftParenthesis()) {

            Formula formula = parseExpression(tokens, 0);

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
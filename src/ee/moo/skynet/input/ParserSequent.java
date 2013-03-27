package ee.moo.skynet.input;

import ee.moo.skynet.Formula;
import ee.moo.skynet.alphabet.AlphabetSequent;

import java.util.ArrayList;
import java.util.List;

/**
 * User: tarmo
 * Date: 3/23/13
 * Time: 4:48 PM
 */
public class ParserSequent extends Parser {


    private AlphabetSequent alphabet = new AlphabetSequent();

    @Override
    public Formula parse(String input) {

        Lexer lexer = new Lexer(input, alphabet);
        Token token;

        List<Token> left = new ArrayList<Token>();
        List<Token> right = new ArrayList<Token>();

        int sequent = 0;

        while ((token = lexer.next()) != null) {

            if (token.getType() == TokenType.WHITESPACE) {
                continue;
            }

            if (token.getType() == TokenType.SEQUENT) {
                sequent++;

            } else if (sequent == 0) {
                left.add(token);

            } else {
                right.add(token);

            }
        }

        if (sequent != 1) {
            throw new ParserException(String.format("Expecting input to contain exactly 1%s", TokenType.SEQUENT));
        }

        Formula lhs = null;
        Formula rhs = null;

        if (!left.isEmpty()) {
            lhs = parseExpression(left, 0, Token.Side.LHS);
        }

        if (!right.isEmpty()) {
            rhs = parseExpression(right, 0, Token.Side.RHS);
        }

        if (!left.isEmpty()) {
            throw new ParserException(String.format("Expecting end of input: %d token(s) remaining", left.size()));
        }

        if (!right.isEmpty()) {
            throw new ParserException(String.format("Expecting end of input: %d token(s) remaining", right.size()));
        }

        if (lhs == null && rhs == null) {
            throw new ParserException("Empty sequent!");

        } else if (lhs == null) {
            return rhs;

        } else if (rhs == null) {
            return new Formula(Formula.NodeType.INVERSION, lhs);

        } else {
            return new Formula(Formula.NodeType.IMPLICATION, lhs, rhs);

        }

    }

    private Formula parseExpression(List<Token> tokens, int precedence, Token.Side side) {

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

    private Formula parsePrimary(List<Token> tokens, Token.Side side) {

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

    public static void main(String[] args) {


        String[] formulas = new String[]{
                "E,C→",
                "→E,C",
                "E→C"
        };

        for (String input : formulas) {
            System.out.println(String.format("%s = %s", input, Formula.parse(input)));
        }
    }
}

package ee.moo.skynet.input;

import ee.moo.skynet.formula.Formula;
import ee.moo.skynet.alphabet.AlphabetSequent;

import java.util.ArrayList;
import java.util.List;

/**
 * User: tarmo
 * Date: 3/23/13
 * Time: 4:48 PM
 */
public class ParserSequent extends Parser {

    @Override
    public Formula parse(String input) {

        Lexer lexer = new Lexer(input, new AlphabetSequent());
        Token token;

        List<Token> left = new ArrayList<Token>();
        List<Token> right = new ArrayList<Token>();

        int sequent = 0;

        while ((token = lexer.next()) != null) {

            if (token.isWhitespace()) {
                continue;
            }

            if (token.isSequent()) {
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

}

package ee.moo.skynet.input;

import java.util.ArrayList;
import java.util.List;

/**
 * User: tarmo
 * Date: 3/23/13
 * Time: 4:48 PM
 */
public class TokenFormula extends Token {

    private static final List<TokenType> ALLOWED = new ArrayList<TokenType>();

    static {
        ALLOWED.add(TokenType.STATEMENT);
        ALLOWED.add(TokenType.INVERSION);
        ALLOWED.add(TokenType.CONJUNCTION);
        ALLOWED.add(TokenType.DISJUNCTION);
        ALLOWED.add(TokenType.IMPLICATION);
        ALLOWED.add(TokenType.EQUIVALENCE);
        ALLOWED.add(TokenType.LEFTPAREN);
        ALLOWED.add(TokenType.RIGHTPAREN);
        ALLOWED.add(TokenType.WHITESPACE);
    }

    public TokenFormula(TokenType type, String data) {

        if (!ALLOWED.contains(type)) {
            throw new ParserException(String.format("TokenType not allowed: %s", type.toString()));
        }

        this.type = type;
        this.data = data;
    }

    public TokenFormula(TokenType type) {

        if (!ALLOWED.contains(type)) {
            throw new ParserException(String.format("TokenType not allowed: %s", type.toString()));
        }

        this.type = type;
    }

}

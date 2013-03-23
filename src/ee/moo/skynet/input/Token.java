package ee.moo.skynet.input;

/**
 * User: tarmo
 * Date: 3/23/13
 * Time: 9:31 PM
 */
public class Token {

    private TokenType type;

    private String data;

    public Token(TokenType type, String data) {
        this.type = type;
        this.data = data;
    }

    public Token(TokenType type) {
        this.type = type;
    }

    public TokenType getType() {
        return type;
    }

    public String getData() {
        return data;
    }

    public String toString() {

        if (type == TokenType.STATEMENT) {
            return String.format("Token[type = %s, data = %s]", type, data);
        } else {
            return String.format("Token[type = %s]", type);
        }
    }
}

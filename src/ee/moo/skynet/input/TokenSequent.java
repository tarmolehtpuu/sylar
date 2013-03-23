package ee.moo.skynet.input;

/**
 * User: tarmo
 * Date: 3/23/13
 * Time: 4:48 PM
 */
public class TokenSequent extends Token {

    public TokenSequent(TokenType type, String data) {
        this.type = type;
        this.data = data;
    }

    public TokenSequent(TokenType type) {
        this.type = type;
    }
}

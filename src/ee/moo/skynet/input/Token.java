package ee.moo.skynet.input;

/**
 * User: tarmo
 * Date: 3/23/13
 * Time: 9:31 PM
 */
public abstract class Token {

    protected TokenType type;

    protected String data;

    public TokenType getType() {
        return type;
    }

    public String getData() {
        return data;
    }
}

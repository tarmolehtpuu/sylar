package ee.moo.skynet.generator;

/**
 * User: tarmo
 * Date: 3/4/13
 * Time: 10:21 PM
 */
public interface Generator {

    public static final String[] STATEMENTS = {
            "A", "B", "C", "D", "E",
            "F", "G", "H", "I", "J"
    };

    String generate();

}

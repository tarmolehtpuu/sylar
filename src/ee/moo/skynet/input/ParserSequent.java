package ee.moo.skynet.input;

import ee.moo.skynet.Formula;
import ee.moo.skynet.alphabet.AlphabetSequent;

/**
 * User: tarmo
 * Date: 3/23/13
 * Time: 4:48 PM
 */
public class ParserSequent extends Parser {

    private AlphabetSequent alphabet;

    @Override
    public Formula parse(String input) {

        Lexer lexer = new Lexer(input, alphabet);
        Token token;

        while ((token = lexer.next()) != null) {
            System.out.println(token);
        }

        return null;

    }
}

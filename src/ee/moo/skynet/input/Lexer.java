package ee.moo.skynet.input;

import ee.moo.skynet.alphabet.Alphabet;
import ee.moo.skynet.alphabet.AlphabetFormula;

import java.util.LinkedList;
import java.util.List;

/**
 * User: tarmo
 * Date: 3/23/13
 * Time: 4:48 PM
 */
public abstract class Lexer {

    protected List<Character> input;

    public Lexer(String input) {

        this.input = new LinkedList<Character>();

        for (char c : input.toCharArray()) {
            this.input.add(c);
        }
    }

    public abstract Token next();


}

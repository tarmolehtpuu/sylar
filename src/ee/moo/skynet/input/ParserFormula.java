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

    @Override
    public Formula parse(String input) {

        Lexer lexer = new Lexer(input, new AlphabetFormula());
        Token token;

        List<Token> tokens = new ArrayList<Token>(input.length());

        while ((token = lexer.next()) != null) {

            if (!token.isWhitespace()) {
                tokens.add(token);
            }

        }

        Formula formula = parseExpression(tokens, 0, Token.Side.NONE);

        if (!tokens.isEmpty()) {
            throw new ParserException(String.format("Expecting end of input: %d token(s) remaining", tokens.size()));
        }

        return formula;
    }
}
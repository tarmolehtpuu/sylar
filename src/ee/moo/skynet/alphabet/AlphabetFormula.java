package ee.moo.skynet.alphabet;

/**
 * User: tarmo
 * Date: 3/23/13
 * Time: 4:49 PM
 */
public class AlphabetFormula extends Alphabet {

    @Override
    public boolean isValid(char c) {

        return isSymbolStatement(c)
                || isSymbolInversion(c)
                || isSymbolConjunction(c)
                || isSymbolDisjunction(c)
                || isSymbolImplication(c)
                || isSymbolEquivalence(c)
                || isSymbolLeftParenthesis(c)
                || isSymbolRightParenthesis(c)
                || isSymbolWhitespace(c);
    }

}

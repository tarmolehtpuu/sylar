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

    @Override
    public boolean isValid(String s) {

        if (s == null || s.length() == 0) {
            return false;
        }

        for (char c : s.toCharArray()) {
            if (!isValid(c)) {
                return false;
            }
        }

        return true;
    }


}

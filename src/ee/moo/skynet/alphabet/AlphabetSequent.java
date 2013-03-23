package ee.moo.skynet.alphabet;

/**
 * User: tarmo
 * Date: 3/23/13
 * Time: 4:49 PM
 */
public class AlphabetSequent extends Alphabet {

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
                || isSymbolWhitespace(c)
                || isSymbolComma(c)
                || isSymbolSequent(c);
    }

    @Override
    public boolean isValid(String s) {
        return false;
    }

    public boolean isSymbolComma(char c) {
        return c == ',';
    }

    public boolean isSymbolComma(String s) {
        return s.length() == 1 && isSymbolComma(s.charAt(0));
    }

    public boolean isSymbolSequent(char c) {
        return c == '→' || c == '⊢';
    }

    public boolean isSymbolSequent(String s) {
        return s.length() == 1 && isSymbolSequent(s.charAt(0));
    }

    public char getSymbolComma() {
        return ',';
    }

    public String getSymbolCommaString() {
        return String.valueOf(getSymbolComma());
    }

    public char getSymbolSequent() {
        return '→';
    }

    public String getSymbolSequentString() {
        return String.valueOf(getSymbolSequent());
    }
}

package ee.moo.skynet.alphabet;

/**
 * User: tarmo
 * Date: 3/23/13
 * Time: 4:49 PM
 */
public abstract class Alphabet {

    public abstract boolean isValid(char c);

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

    public boolean isSymbolStatement(char c) {
        return isSymbolStatement(String.valueOf(c));
    }

    public boolean isSymbolStatement(String s) {
        return s.matches("^[A-Z0-9]+$");
    }

    public boolean isSymbolInversion(char c) {
        return c == '!' || c == '¬';
    }

    public boolean isSymbolInversion(String s) {
        return s.length() == 1 && isSymbolInversion(s.charAt(0));
    }

    public boolean isSymbolConjunction(char c) {
        return c == '&' || c == '∧';
    }

    public boolean isSymbolConjunction(String s) {
        return s.length() == 1 && isSymbolConjunction(s.charAt(0));
    }

    public boolean isSymbolDisjunction(char c) {
        return c == 'v' || c == '∨';
    }

    public boolean isSymbolDisjunction(String s) {
        return s.length() == 1 && isSymbolDisjunction(s.charAt(0));
    }

    public boolean isSymbolImplication(char c) {
        return c == '⊃' || c == '⇒';
    }

    public boolean isSymbolImplication(String s) {
        return s.length() == 1 && isSymbolImplication(s.charAt(0));
    }

    public boolean isSymbolEquivalence(char c) {
        return c == '⇔' || c == '↔' || c == '=';
    }

    public boolean isSymbolEquivalence(String s) {
        return s.length() == 1 && isSymbolEquivalence(s.charAt(0));
    }

    public boolean isSymbolLeftParenthesis(char c) {
        return c == '(' || c == '[' || c == '{';
    }

    public boolean isSymbolLeftParenthesis(String s) {
        return s.length() == 1 && isSymbolLeftParenthesis(s.charAt(0));
    }

    public boolean isSymbolRightParenthesis(char c) {
        return c == ')' || c == ']' || c == '}';
    }

    public boolean isSymbolRightParenthesis(String s) {
        return s.length() == 1 && isSymbolRightParenthesis(s.charAt(0));
    }

    public boolean isSymbolWhitespace(char c) {
        return Character.isWhitespace(c);
    }

    public boolean isSymbolWhitespace(String s) {
        return s.length() == 1 && isSymbolWhitespace(s.charAt(0));
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

    public char getSymbolInversion() {
        return '¬';
    }

    public String getSymbolInversionString() {
        return String.valueOf(getSymbolInversion());
    }

    public char getSymbolConjunction() {
        return '&';
    }

    public String getSymbolConjunctionString() {
        return String.valueOf(getSymbolConjunction());
    }

    public char getSymbolDisjunction() {
        return '∨';
    }

    public String getSymbolDisjunctionString() {
        return String.valueOf(getSymbolDisjunction());
    }

    public char getSymbolImplication() {
        return '⊃';
    }

    public String getSymbolImplicationString() {
        return String.valueOf(getSymbolImplication());
    }

    public char getSymbolEquivalence() {
        return '⇔';
    }

    public String getSymbolEquivalenceString() {
        return String.valueOf(getSymbolEquivalence());
    }

    public char getSymbolLeftParenthesis() {
        return '(';
    }

    public String getSymbolLeftParenthesisString() {
        return String.valueOf(getSymbolLeftParenthesis());
    }

    public char getSymbolRightParenthesis() {
        return ')';
    }

    public String getSymbolRightParenthesisString() {
        return String.valueOf(getSymbolRightParenthesis());
    }

    public char getSymbolWhitespace() {
        return ' ';
    }

    public String getSymbolWhitespaceString() {
        return String.valueOf(getSymbolWhitespace());
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

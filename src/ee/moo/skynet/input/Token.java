package ee.moo.skynet.input;

import ee.moo.skynet.Formula;

/**
 * User: tarmo
 * Date: 3/23/13
 * Time: 9:31 PM
 */
public class Token {

    private TokenType type;

    private String data;

    public Token(TokenType type, String data) {
        this.type = type;
        this.data = data;
    }

    public Token(TokenType type) {
        this.type = type;
    }

    public TokenType getType() {
        return type;
    }

    public String getData() {
        return data;
    }

    public int getPrecedence() {

        switch (type) {

            case INVERSION:
                return 5;

            case CONJUNCTION:
                return 4;

            case DISJUNCTION:
                return 3;

            case IMPLICATION:
                return 2;

            case EQUIVALENCE:
                return 1;

            default:
                return 0;
        }
    }

    public Formula.NodeType getNodeType() {

        switch (type) {

            case STATEMENT:
                return Formula.NodeType.STATEMENT;

            case INVERSION:
                return Formula.NodeType.INVERSION;

            case CONJUNCTION:
                return Formula.NodeType.CONJUNCTION;

            case DISJUNCTION:
                return Formula.NodeType.DISJUNCTION;

            case IMPLICATION:
                return Formula.NodeType.IMPLICATION;

            case EQUIVALENCE:
                return Formula.NodeType.EQUIVALENCE;

            default:
                throw new ParserException(String.format("Unable to convert %s to Formula.NodeType.", type));

        }
    }

    public boolean isStatement() {
        return type == TokenType.STATEMENT;
    }

    public boolean isInversion() {
        return type == TokenType.INVERSION;
    }

    public boolean isConjunction() {
        return type == TokenType.CONJUNCTION;
    }

    public boolean isDisjunction() {
        return type == TokenType.DISJUNCTION;
    }

    public boolean isImplication() {
        return type == TokenType.IMPLICATION;
    }

    public boolean isEquivalence() {
        return type == TokenType.EQUIVALENCE;
    }

    public boolean isLeftParenthesis() {
        return type == TokenType.LEFTPAREN;
    }

    public boolean isRightParenthesis() {
        return type == TokenType.RIGHTPAREN;
    }

    public boolean isWhitespace() {
        return type == TokenType.WHITESPACE;
    }

    public boolean isComma() {
        return type == TokenType.COMMA;
    }

    public boolean isSequent() {
        return type == TokenType.SEQUENT;
    }

    public boolean isBinary() {

        return type == TokenType.CONJUNCTION
                || type == TokenType.DISJUNCTION
                || type == TokenType.IMPLICATION
                || type == TokenType.EQUIVALENCE;

    }

    public boolean isUnary() {
        return type == TokenType.INVERSION;
    }

    public String toString() {

        if (type == TokenType.STATEMENT) {
            return String.format("Token[type = %s, data = %s]", type, data);
        } else {
            return String.format("Token[type = %s]", type);
        }

    }

}

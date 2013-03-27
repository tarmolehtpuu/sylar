package ee.moo.skynet;

import ee.moo.skynet.alphabet.Alphabet;
import ee.moo.skynet.alphabet.AlphabetFormula;
import ee.moo.skynet.alphabet.AlphabetSequent;
import ee.moo.skynet.input.ParserFormula;
import ee.moo.skynet.input.ParserSequent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User: tarmo
 * Date: 3/16/13
 * Time: 12:31 PM
 */
public class Formula {

    public enum NodeType {
        STATEMENT,
        INVERSION,
        CONJUNCTION,
        DISJUNCTION,
        IMPLICATION,
        EQUIVALENCE
    }

    private NodeType type;

    private Formula left;
    private Formula right;

    private String name;

    private boolean data;

    public Formula() {
    }

    public Formula(NodeType type) {
        this.type = type;
    }

    public Formula(NodeType type, String name) {
        this.type = type;
        this.name = name;
    }

    public Formula(NodeType type, Formula left) {
        this.type = type;
        this.left = left;
    }

    public Formula(NodeType type, Formula left, Formula right) {
        this.type = type;
        this.left = left;
        this.right = right;
    }

    public String[] getStatements() {

        List<String> statements = new ArrayList<String>();

        if (left != null) {

            for (String statement : left.getStatements()) {
                if (!statements.contains(statement)) {
                    statements.add(statement);
                }
            }
        }

        if (right != null) {

            for (String statement : right.getStatements()) {
                if (!statements.contains(statement)) {
                    statements.add(statement);
                }
            }
        }

        if (isStatement() && !statements.contains(name)) {
            statements.add(name);
        }

        Collections.sort(statements);

        return statements.toArray(new String[statements.size()]);
    }

    public int[][] getStatementPermutations() {

        String[] statements = getStatements();

        int[][] permutations = new int[(int) Math.pow(2, statements.length)][statements.length];

        for (int i = 0; i < permutations.length; i++) {

            StringBuilder builder = new StringBuilder(Integer.toBinaryString(i));

            while (builder.length() < statements.length) {
                builder.insert(0, 0);
            }

            for (int j = 0; j < permutations[i].length; j++) {

                if (builder.charAt(j) == '1') {
                    permutations[i][j] = 1;
                } else {
                    permutations[i][j] = 0;
                }

            }
        }

        return permutations;
    }

    public void setValue(String name, boolean value) {

        if (left != null) {
            left.setValue(name, value);
        }

        if (right != null) {
            right.setValue(name, value);
        }

        if (isStatement() && this.name.equals(name)) {
            data = value;
        }
    }

    public void setValue(String name, int value) {
        setValue(name, value == 1);
    }

    public Formula getLeft() {
        return left;
    }

    public void setLeft(Formula left) {
        this.left = left;
    }

    public Formula getRight() {
        return right;
    }

    public void setRight(Formula right) {
        this.right = right;
    }

    public boolean isStatement() {
        return type == NodeType.STATEMENT;
    }

    public boolean isInversion() {
        return type == NodeType.INVERSION;
    }

    public boolean isConjunction() {
        return type == NodeType.CONJUNCTION;
    }

    public boolean isDisjunction() {
        return type == NodeType.DISJUNCTION;
    }

    public boolean isImplication() {
        return type == NodeType.IMPLICATION;
    }

    public boolean isEquivalence() {
        return type == NodeType.EQUIVALENCE;
    }

    public boolean isFalseAlways() {

        String[] statements = getStatements();

        for (int[] permutation : getStatementPermutations()) {

            for (int i = 0; i < statements.length; i++) {
                setValue(statements[i], permutation[i]);
            }

            if (evaluate()) {
                return false;
            }

        }

        return true;
    }

    public boolean isTrueAlways() {

        String[] statements = getStatements();

        for (int[] permutation : getStatementPermutations()) {

            for (int i = 0; i < statements.length; i++) {
                setValue(statements[i], permutation[i]);
            }

            if (!evaluate()) {
                return false;
            }

        }

        return true;
    }

    public boolean evaluate() {

        if (isStatement()) {
            return data;

        } else if (isInversion()) {

            return !left.evaluate();

        } else {

            boolean a = left.evaluate();
            boolean b = right.evaluate();

            if (isConjunction()) {
                return a && b;

            } else if (isDisjunction()) {
                return a || b;

            } else if (isImplication()) {
                return !a || b;

            } else if (isEquivalence()) {
                return a == b;

            } else {
                throw new RuntimeException(String.format("Unknown node type: %s", type));

            }
        }
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();

        Alphabet alphabet = new AlphabetFormula();

        if (isInversion()) {
            builder.append(alphabet.getSymbolInversion());
        }

        if (left != null) {
            builder.append(alphabet.getSymbolLeftParenthesis());
            builder.append(left.toString());
        }

        if (isStatement()) {
            builder.append(name);

        } else {

            switch (type) {

                case INVERSION:
                    break;

                case CONJUNCTION:
                    builder.append(alphabet.getSymbolConjunction());
                    break;

                case DISJUNCTION:
                    builder.append(alphabet.getSymbolDisjunction());
                    break;

                case IMPLICATION:
                    builder.append(alphabet.getSymbolImplication());
                    break;

                case EQUIVALENCE:
                    builder.append(alphabet.getSymbolEquivalence());
                    break;

                default:
                    throw new FormulaException(String.format("Unexpected node type: %s", type));
            }

        }

        if (right != null) {

            builder.append(right.toString());
            builder.append(alphabet.getSymbolRightParenthesis());

        } else {

            if (isInversion()) {
                builder.append(alphabet.getSymbolRightParenthesis());
            }

        }

        return builder.toString();

    }

    public static Formula parse(String input) {

        if (new AlphabetFormula().isValid(input)) {
            return new ParserFormula().parse(input);
        }

        if (new AlphabetSequent().isValid(input)) {
            return new ParserSequent().parse(input);
        }

        throw new FormulaException(String.format("Invalid formula: %s", input));

    }

    public static void main(String[] args) {

        String[] formulas = new String[]{
                "A v B & C",
                "A & B v C",
                "!A & B v C",
                "A & !B v C",
                "A & B & C ↔ B & C & D",
                "A v B ↔ B & C v D",
                "A & B ⊃ !C & D"
        };

        for (String input : formulas) {
            System.out.println(String.format("%s = %s", input, Formula.parse(input)));
        }

    }
}

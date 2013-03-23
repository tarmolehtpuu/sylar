package ee.moo.skynet;

import ee.moo.skynet.alphabet.AlphabetFormula;

/**
 * User: tarmo
 * Date: 3/23/13
 * Time: 10:35 PM
 */
public class Node {

    private NodeType type;

    private String name;

    private boolean data;

    public Node() {
    }

    public Node(NodeType type) {
        this.type = type;
    }

    public Node(NodeType type, String name) {
        this.type = type;
        this.name = name;

        if (isStatement() && name == null || name.length() == 0) {
            throw new FormulaException("Illegal statement: name is required!");
        }
    }

    public NodeType getType() {
        return type;
    }

    public String getTypeString() {

        AlphabetFormula alphabet = new AlphabetFormula();

        switch (type) {

            case INVERSION:
                return alphabet.getSymbolInversionString();

            case CONJUNCTION:
                return alphabet.getSymbolConjunctionString();

            case DISJUNCTION:
                return alphabet.getSymbolDisjunctionString();

            case IMPLICATION:
                return alphabet.getSymbolImplicationString();

            case EQUIVALENCE:
                return alphabet.getSymbolEquivalenceString();

            default:
                throw new FormulaException(String.format("Invalid node type: %s", type));
        }

    }

    public String getName() {

        if (!isStatement()) {
            throw new FormulaException("Only NodeType.STATEMENT may contain a value for name!");
        }

        return name;
    }

    public boolean getData() {

        if (!isStatement()) {
            throw new FormulaException("Only NodeType.STATEMENT may contain a value for data!");
        }

        return data;
    }

    public void setData(boolean data) {

        if (!isStatement()) {
            throw new FormulaException("Only NodeType.STATEMENT may contain a value for data!");
        }

        this.data = data;
    }

    public void setData(int data) {
        this.setData(data == 1);
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

    public String toString() {

        if (this.isStatement()) {
            return String.format("Node[type=%s, name=%s, data=%s",
                    String.valueOf(this.getType()),
                    String.valueOf(this.getName()),
                    String.valueOf(this.getData()));
        } else {
            return String.format("Node[type=%s]", String.valueOf(this.getType()));
        }
    }
}

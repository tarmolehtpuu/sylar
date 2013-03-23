package ee.moo.skynet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User: tarmo
 * Date: 3/16/13
 * Time: 12:31 PM
 */
public class Formula {

    private Formula left;
    private Formula right;

    private Node node;

    public Formula(Node node) {
        this.node = node;
    }

    public Formula(Node node, Formula left) {
        this.node = node;
        this.left = left;
    }

    public Formula(Node node, Formula left, Formula right) {
        this.node = node;
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

        if (node.isStatement() && !statements.contains(node.getName())) {
            statements.add(node.getName());
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

        if (node.isStatement() && name.equals(node.getName())) {
            node.setData(value);
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

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
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

        if (node.isStatement()) {
            return node.getData();

        } else if (node.isInversion()) {

            return !left.evaluate();

        } else {

            boolean a = left.evaluate();
            boolean b = right.evaluate();

            if (node.isConjunction()) {
                return a && b;

            } else if (node.isDisjunction()) {
                return a || b;

            } else if (node.isImplication()) {
                return !a || b;

            } else if (node.isEquivalence()) {
                return a == b;

            } else {
                throw new RuntimeException(String.format("Unknown node type: %s", node.getType()));

            }

        }

    }

    public static void main(String[] args) {

        Formula lhs = new Formula(new Node(NodeType.CONJUNCTION),
                new Formula(new Node(NodeType.STATEMENT, "A")),
                new Formula(new Node(NodeType.STATEMENT, "B")));

        Formula rhs = new Formula(new Node(NodeType.INVERSION),
                new Formula(new Node(NodeType.STATEMENT, "C")));

        Formula root = new Formula(new Node(NodeType.IMPLICATION), lhs, rhs);

        System.out.println(root.isTrueAlways());
        System.out.println(root.isFalseAlways());

        String[] statement = root.getStatements();

        for (int[] permutation : root.getStatementPermutations()) {

            for (int i = 0; i < statement.length; i++) {
                root.setValue(statement[i], permutation[i]);
            }

            System.out.println(root.evaluate());
        }
    }
}

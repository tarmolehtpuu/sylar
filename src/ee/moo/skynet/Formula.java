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

    private String label;

    private boolean value;

    public Formula(String label) {
        this.label = label;
    }

    public Formula(String label, Formula left) {
        this.label = label;
        this.left = left;
    }

    public Formula(String label, Formula left, Formula right) {
        this.label = label;
        this.left = left;
        this.right = right;
    }

    public String[] getAtoms() {

        List<String> atoms = new ArrayList<String>();

        if (left != null) {

            for (String atom : left.getAtoms()) {

                if (!atoms.contains(atom)) {
                    atoms.add(atom);
                }
            }
        }

        if (right != null) {

            for (String atom : right.getAtoms()) {

                if (!atoms.contains(atom)) {
                    atoms.add(atom);
                }
            }
        }

        if (this.isAtomic() && !atoms.contains(label)) {
            atoms.add(label);
        }

        Collections.sort(atoms);

        return atoms.toArray(new String[atoms.size()]);
    }

    public int[][] getAtomPermutations() {

        String[] atoms = getAtoms();

        int[][] permutations = new int[(int) Math.pow(2, atoms.length)][atoms.length];

        for (int i = 0; i < permutations.length; i++) {

            StringBuilder builder = new StringBuilder(Integer.toBinaryString(i));

            while (builder.length() < atoms.length) {
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

    public void setValue(String label, boolean value) {

        if (left != null) {
            left.setValue(label, value);
        }

        if (right != null) {
            right.setValue(label, value);
        }

        if (label.equals(this.label)) {
            this.value = value;
        }
    }

    public void setValue(String label, int value) {
        setValue(label, value == 1);
    }

    public boolean isAtomic() {
        return label.matches("^[A-Z0-9]+$");
    }

    public boolean isInversion() {
        return label.equals("!");
    }

    public boolean isConjunction() {
        return label.equals("&");
    }

    public boolean isDisjunction() {
        return label.equals("v") || label.equals("∨");
    }

    public boolean isImplication() {
        return label.equals("->") || label.equals("⊃") || label.equals("|-") || label.equals("→");
    }

    public boolean isEquivalence() {
        return label.equals("=") || label.equals("⇔");
    }

    public boolean isFalseAlways() {

        String[] atoms = getAtoms();

        for (int[] permutation : getAtomPermutations()) {

            for (int i = 0; i < atoms.length; i++) {
                setValue(atoms[i], permutation[i]);
            }

            if (evaluate()) {
                return false;
            }

        }

        return true;
    }

    public boolean isTrueAlways() {

        String[] atoms = getAtoms();

        for (int[] permutation : getAtomPermutations()) {

            for (int i = 0; i < atoms.length; i++) {
                setValue(atoms[i], permutation[i]);
            }

            if (!evaluate()) {
                return false;
            }

        }

        return true;
    }

    public boolean evaluate() {

        if (isAtomic()) {

            return value;

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
                throw new RuntimeException(String.format("Unknown operation: %s", label));

            }

        }

    }

    public static void main(String[] args) {

        Formula lhs = new Formula("&", new Formula("A"), new Formula("B"));
        Formula rhs = new Formula("!", new Formula("C"));

        Formula root = new Formula("⊃", lhs, rhs);

        System.out.println(root.isTrueAlways());
        System.out.println(root.isFalseAlways());

        String[] atoms = root.getAtoms();

        for (int[] permutation : root.getAtomPermutations()) {

            for (int i = 0; i < atoms.length; i++) {
                root.setValue(atoms[i], permutation[i]);
            }

            System.out.println(root.evaluate());
        }
    }
}

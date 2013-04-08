package ee.moo.skynet;

import ee.moo.skynet.util.BinaryUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User: tarmo
 * Date: 4/1/13
 * Time: 01:04 AM
 */
public class FormulaCollection {

    private List<Formula> formulas = new ArrayList<Formula>();

    public FormulaCollection() {
    }

    public void add(Formula formula) {
        formulas.add(formula);
    }

    public boolean isContradictory() {

        List<String> statements = new ArrayList<String>();

        for (Formula formula : formulas) {
            for (String statement : formula.getStatements()) {

                if (!statements.contains(statement)) {
                    statements.add(statement);
                }
            }
        }

        Collections.sort(statements);

        outer:
        for (int[] permutation : BinaryUtil.permutations(statements.size())) {

            for (int i = 0; i < statements.size(); i++) {
                for (Formula formula : formulas) {
                    formula.setValue(statements.get(i), permutation[i]);
                }
            }

            for (Formula formula : formulas) {
                if (!formula.evaluate()) {
                    continue outer;
                }
            }

            return false;
        }


        return true;
    }

    public static void main(String[] args) {

        FormulaCollection collection = new FormulaCollection();

        collection.add(Formula.parse("!(A&B)→A"));
        collection.add(Formula.parse("!(A&B)→!A"));
        collection.add(Formula.parse("A,B→!A"));

        System.out.println(collection.isContradictory());
    }
}

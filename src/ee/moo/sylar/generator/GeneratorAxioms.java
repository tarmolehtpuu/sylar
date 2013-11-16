package ee.moo.sylar.generator;

import ee.moo.sylar.formula.Formula;
import ee.moo.sylar.formula.FormulaCollection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: tarmo
 * Date: 10/28/13
 * Time: 4:12 PM
 */
public class GeneratorAxioms {

    private List<String> axioms = new ArrayList<String>();

    private Generator generator = new GeneratorFormula();

    private FormulaCollection collection = new FormulaCollection();

    public void generate() {

        while (collection.size() < 100) {

            String formula = generator.generate();

            if (formula.length() < 6) {
                continue;
            }

            collection.add(Formula.parse(formula));

            if (collection.isContradictory()) {
                collection.remove(collection.size() - 1);
                continue;
            }

            axioms.add(formula);
        }
    }

    public void dump() {

        System.out.println("--------------------------------------------------------------------------");
        System.out.println(String.format("AXIOM SET: %d axioms", axioms.size()));
        System.out.println("--------------------------------------------------------------------------");


        for (String axiom : axioms) {
            System.out.println(axiom);
        }
    }

    public static void main(String[] args) throws IOException {
        for (int i = 0; i < 1; i++) {
            GeneratorAxioms generator = new GeneratorAxioms();

            generator.generate();
            generator.dump();
        }
    }
}

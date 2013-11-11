package ee.moo.sylar.inference;

import ee.moo.sylar.formula.Formula;
import ee.moo.sylar.util.StringUtil;

import java.util.*;

/**
 * User: tarmo
 * Date: 11/11/13
 * Time: 05:09 PM
 */
public class InferenceEngine {

    private Map<String, Integer> values = new HashMap<String, Integer>();

    private List<Formula> formulas = new ArrayList<Formula>();

    private int step = 0;

    public InferenceEngine() {
    }

    public void addFormula(String formula) {
        formulas.add(Formula.parse(formula));
    }

    public void addFormula(Formula formula) {
        formulas.add(formula);
    }

    public void setValue(String name, Integer value) {
        values.put(name, value);
    }

    public void setValue(String name, boolean value) {
        values.put(name, value ? 1 : 0);
    }

    public void init() {

        for (Formula formula : formulas) {
            for (String name : formula.getStatements()) {
                if (!values.containsKey(name)) {
                    values.put(name, -1);
                }
            }
        }

    }

    public boolean step() {

        int countOld = 0;

        for (String name : values.keySet()) {
            if (values.get(name) == -1) {
                countOld++;
            }
        }

        if (countOld == 0) {
            return false;
        }

        step++;

        for (Formula formula : formulas) {

            InferenceRequest request = new InferenceRequest(formula);

            for (String name : formula.getStatements()) {
                if (values.get(name) != -1) {
                    request.setValue(name, values.get(name));
                }
            }

            if (formula.isImplication()) {
                InferenceResult resultMP = new InferenceStrategyMP().apply(request);
                InferenceResult resultMT = new InferenceStrategyMT().apply(request);

                for (String name : resultMP.getNames()) {
                    if (resultMP.get(name) != -1) {
                        values.put(name, resultMP.get(name));
                    }
                }

                for (String name : resultMT.getNames()) {
                    if (resultMT.get(name) != -1) {
                        values.put(name, resultMT.get(name));
                    }
                }
            }

            if (formula.isEquivalence()) {
                InferenceResult resultEQ = new InferenceStrategyEQ().apply(request);

                for (String name : resultEQ.getNames()) {
                    if (resultEQ.get(name) != -1) {
                        values.put(name, resultEQ.get(name));
                    }
                }
            }
        }

        int countNew = 0;

        for (String name : values.keySet()) {
            if (values.get(name) == -1) {
                countNew++;
            }
        }

        return countNew != countOld;
    }

    public void run() {

        dump();

        while (step()) {
            dump();
        }
    }

    public void dump() {

        System.out.println(String.format("STEP: %d", step));

        List<String> names = new ArrayList<String>(values.keySet());

        Collections.sort(names);

        for (String name : names) {
            System.out.print(StringUtil.lpad(name, 3, ' '));
        }

        System.out.println();

        for (String name : names) {
            System.out.print(StringUtil.lpad(String.valueOf(values.get(name)), 3, ' '));
        }

        System.out.println();
        System.out.println();

    }

    public static void main(String[] args) {
        InferenceEngine engine = new InferenceEngine();

        engine.addFormula("C⊃D");
        engine.addFormula("A⊃B");
        engine.addFormula("D⊃E");
        engine.addFormula("B⊃C");

        engine.setValue("A", 1);
        engine.init();
        engine.run();
    }
}

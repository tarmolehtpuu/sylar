package ee.moo.sylar.util;

import ee.moo.sylar.formula.Formula;
import ee.moo.sylar.formula.FormulaCollection;
import ee.moo.sylar.formula.FormulaException;
import ee.moo.sylar.generator.GeneratorFormula;
import ee.moo.sylar.generator.GeneratorSequent;
import ee.moo.sylar.inference.*;
import ee.moo.sylar.output.SerializerDot;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * User: tarmo
 * Date: 3/28/13
 * Time: 2:56 AM
 */
public class Console {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static Formula eval(String input) {
        return Formula.parse(input);
    }

    public static String generateF() {
        return new GeneratorFormula().generate();
    }

    public static String generateS() {
        return new GeneratorSequent().generate();
    }

    public static void print(String input) {
        System.out.println(Formula.parse(input));
    }

    public static void print(Formula formula) {
        System.out.println(formula);
    }

    public static void plot(String input) {
        plot(Formula.parse(input));
    }

    public static void interpret(String input) {
        interpret(Formula.parse(input));
    }

    public static void interpret(Formula formula) {

        String[] statements = formula.getStatements();

        StringBuilder builder = new StringBuilder();

        for (String statement : statements) {
            builder.append(ANSI_YELLOW);
            builder.append(statement);
            builder.append(ANSI_RESET);
            builder.append(' ');
        }

        builder.append('=');
        builder.append(' ');
        builder.append(ANSI_YELLOW);
        builder.append("OUT");
        builder.append(ANSI_RESET);
        builder.append("\n");

        PermutationIterator iterator = new PermutationIterator(statements.length);

        while (iterator.hasNext()) {

            int[] permutation = iterator.next();

            for (int i = 0; i < statements.length; i++) {
                formula.setValue(statements[i], permutation[i]);
                builder.append(StringUtil.rpad(String.valueOf(permutation[i]), statements[i].length(), ' '));
                builder.append(' ');
            }

            builder.append('=');
            builder.append(' ');

            if (formula.evaluate()) {

                builder.append(ANSI_GREEN);
                builder.append('1');
                builder.append(ANSI_RESET);

            } else {

                builder.append(ANSI_RED);
                builder.append('0');
                builder.append(ANSI_RESET);
            }

            builder.append("\n");
        }

        System.out.println(builder.toString());
    }

    public static void infer(String values, Formula formula) {
        infer(new InferenceRequest(formula, decode(values)));
    }

    public static void infer(String values, String formula) {
        infer(new InferenceRequest(Formula.parse(formula), decode(values)));
    }

    public static void inferMP(String values, String... formulas) {

        FormulaCollection collection = new FormulaCollection();

        for (String formula : formulas) {
            collection.add(Formula.parse(formula));
        }

        InferenceRequest request = new InferenceRequest();

        request.setValues(decode(values));
        request.setFormulaCollectionMP(collection);

        infer(request);
    }

    public static void inferMP(String values, Formula... formulas) {

        FormulaCollection collection = new FormulaCollection();

        for (Formula formula : formulas) {
            collection.add(formula);
        }

        InferenceRequest request = new InferenceRequest();

        request.setValues(decode(values));
        request.setFormulaCollectionMP(collection);

        infer(request);
    }

    public static void inferMT(String values, String... formulas) {

        FormulaCollection collection = new FormulaCollection();

        for (String formula : formulas) {
            collection.add(Formula.parse(formula));
        }

        InferenceRequest request = new InferenceRequest();

        request.setValues(decode(values));
        request.setFormulaCollectionMT(collection);

        infer(request);
    }

    public static void inferMT(String values, Formula... formulas) {

        FormulaCollection collection = new FormulaCollection();

        for (Formula formula : formulas) {
            collection.add(formula);
        }

        InferenceRequest request = new InferenceRequest();

        request.setValues(decode(values));
        request.setFormulaCollectionMT(collection);

        infer(request);
    }

    public static void inferEQ(String values, String... formulas) {

        FormulaCollection collection = new FormulaCollection();

        for (String formula : formulas) {
            collection.add(Formula.parse(formula));
        }

        InferenceRequest request = new InferenceRequest();

        request.setValues(decode(values));
        request.setFormulaCollectionEQ(collection);

        infer(request);
    }

    public static void inferEQ(String values, Formula... formulas) {

        FormulaCollection collection = new FormulaCollection();

        for (Formula formula : formulas) {
            collection.add(formula);
        }

        InferenceRequest request = new InferenceRequest();

        request.setValues(decode(values));
        request.setFormulaCollectionEQ(collection);

        infer(request);
    }

    public static void infer(InferenceRequest request) {

        InferenceResult resultEQ = null;
        InferenceResult resultMP = null;
        InferenceResult resultMT = null;

        if (request.getFormula().isEquivalence()) {
            resultEQ = new InferenceStrategyEQ().apply(request);
        }

        if (request.getFormula().isImplication()) {
            resultMP = new InferenceStrategyMP().apply(request);
            resultMT = new InferenceStrategyMT().apply(request);
        }

        InferenceResult header = null;

        if (resultEQ != null) {
            header = resultEQ;
        } else if (resultMP != null) {
            header = resultMP;
        } else if (resultMT != null) {
            header = resultMT;
        }

        StringBuilder builder = new StringBuilder();

        // write header

        if (header != null) {

            builder.append("    ");

            for (String name : header.getNames()) {

                if (StringUtil.equals(name, InferenceRequest.NAME_FALSE) || StringUtil.equals(name, InferenceRequest.NAME_TRUE)) {
                    continue;
                }

                builder.append(ANSI_CYAN);
                builder.append(StringUtil.lpad(name, 3, ' '));
                builder.append(ANSI_RESET);
            }

            builder.append("\n");
        }

        // write EQ

        if (resultEQ != null) {

            builder.append(ANSI_CYAN);
            builder.append("EQ: ");
            builder.append(ANSI_RESET);

            for (String name : resultEQ.getNames()) {


                if (StringUtil.equals(name, InferenceRequest.NAME_FALSE) || StringUtil.equals(name, InferenceRequest.NAME_TRUE)) {
                    continue;
                }

                switch (resultEQ.get(name)) {

                    case -1:
                        builder.append(ANSI_YELLOW);
                        builder.append(StringUtil.lpad("-1", 3, ' '));
                        builder.append(ANSI_RESET);
                        break;

                    case 0:
                        builder.append(ANSI_RED);
                        builder.append(StringUtil.lpad("0", 3, ' '));
                        builder.append(ANSI_RESET);
                        break;

                    case 1:
                        builder.append(ANSI_GREEN);
                        builder.append(StringUtil.lpad("1", 3, ' '));
                        builder.append(ANSI_RESET);
                        break;
                }
            }

            builder.append("\n");
        }

        // write MP

        if (resultMP != null) {

            builder.append(ANSI_CYAN);
            builder.append("MP: ");
            builder.append(ANSI_RESET);

            for (String name : resultMP.getNames()) {

                if (StringUtil.equals(name, InferenceRequest.NAME_FALSE) || StringUtil.equals(name, InferenceRequest.NAME_TRUE)) {
                    continue;
                }

                switch (resultMP.get(name)) {

                    case -1:
                        builder.append(ANSI_YELLOW);
                        builder.append(StringUtil.lpad("-1", 3, ' '));
                        builder.append(ANSI_RESET);
                        break;

                    case 0:
                        builder.append(ANSI_RED);
                        builder.append(StringUtil.lpad("0", 3, ' '));
                        builder.append(ANSI_RESET);
                        break;

                    case 1:
                        builder.append(ANSI_GREEN);
                        builder.append(StringUtil.lpad("1", 3, ' '));
                        builder.append(ANSI_RESET);
                        break;
                }
            }

            builder.append("\n");
        }

        // write MT

        if (resultMT != null) {

            builder.append(ANSI_CYAN);
            builder.append("MT: ");
            builder.append(ANSI_RESET);

            for (String name : resultMT.getNames()) {

                if (StringUtil.equals(name, InferenceRequest.NAME_FALSE) || StringUtil.equals(name, InferenceRequest.NAME_TRUE)) {
                    continue;
                }

                switch (resultMT.get(name)) {

                    case -1:
                        builder.append(ANSI_YELLOW);
                        builder.append(StringUtil.lpad("-1", 3, ' '));
                        builder.append(ANSI_RESET);
                        break;

                    case 0:
                        builder.append(ANSI_RED);
                        builder.append(StringUtil.lpad("0", 3, ' '));
                        builder.append(ANSI_RESET);
                        break;

                    case 1:
                        builder.append(ANSI_GREEN);
                        builder.append(StringUtil.lpad("1", 3, ' '));
                        builder.append(ANSI_RESET);
                        break;
                }
            }

            builder.append("\n");
        }

        System.out.println(builder.toString());
    }

    public static Map<String, Integer> decode(String values) {

        Map<String, Integer> result = new HashMap<String, Integer>();

        if (StringUtil.isEmpty(values)) {
            return result;
        }

        for (String item : values.split(",")) {

            String key = item.split("=")[0].trim();
            String val = item.split("=")[1].trim();

            result.put(key, Integer.valueOf(val));
        }

        return result;
    }

    public static boolean isContradictory(Formula... formulas) {

        FormulaCollection collection = new FormulaCollection();

        for (Formula formula : formulas) {
            collection.add(formula);
        }

        return collection.isContradictory();
    }

    public static boolean isContradictory(String... formulas) {

        FormulaCollection collection = new FormulaCollection();

        for (String formula : formulas) {
            collection.add(Formula.parse(formula));
        }

        return collection.isContradictory();
    }

    public static boolean isContradictory(Formula formula) {
        return formula.isFalseAlways();
    }

    public static boolean isContradictory(String formula) {
        return isContradictory(Formula.parse(formula));
    }


    public static boolean isTautology(Formula formula) {
        return formula.isTrueAlways();
    }

    public static boolean isTautology(String formula) {
        return isTautology(Formula.parse(formula));
    }

    public static void plot(Formula formula) {

        String dot = String.format("%d%s.dot", System.currentTimeMillis(), StringUtil.random(10));
        String png = String.format("%d%s.png", System.currentTimeMillis(), StringUtil.random(10));

        String dotFile = String.format("%s/%s", System.getProperty("java.io.tmpdir"), dot);
        String pngFile = String.format("%s/%s", System.getProperty("java.io.tmpdir"), png);

        try {

            FileOutputStream out = new FileOutputStream(dotFile, false);

            out.write(new SerializerDot().serialize(formula).getBytes());
            out.close();

            Process process1;
            Process process2;

            process1 = new ProcessBuilder("dot", "-T", "png", dotFile, "-o", pngFile).start();
            process1.waitFor();

            process2 = new ProcessBuilder("open", pngFile).start();
            process2.waitFor();

        } catch (FileNotFoundException e) {
            throw new FormulaException(e.getMessage(), e);

        } catch (IOException e) {
            throw new FormulaException(e.getMessage(), e);

        } catch (InterruptedException e) {
            throw new FormulaException(e.getMessage(), e);
        }
    }
}

package ee.moo.skynet.util;

import ee.moo.skynet.Formula;
import ee.moo.skynet.FormulaException;
import ee.moo.skynet.generator.GeneratorFormula;
import ee.moo.skynet.generator.GeneratorSequent;
import ee.moo.skynet.output.SerializerDot;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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

        for (int[] permutation : BinaryUtil.permutations(statements.length)) {

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

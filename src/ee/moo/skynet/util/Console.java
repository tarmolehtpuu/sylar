package ee.moo.skynet.util;

import ee.moo.skynet.Formula;
import ee.moo.skynet.FormulaException;
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

    public static Formula eval(String input) {
        return Formula.parse(input);
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

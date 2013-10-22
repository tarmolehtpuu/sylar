package ee.moo.sylar.util;

import ee.moo.sylar.formula.Formula;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

/**
 * User: tarmo
 * Date: 10/16/13
 * Time: 4:52 PM
 */
public class ConsoleTest {

    private StringBuilder builder;

    private PrintStream out;

    @Before
    public void setUp() {

        if (out == null) {
            out = System.out;
        }

        builder = new StringBuilder();

        OutputStream stream = new OutputStream() {
            public void write(int i) throws IOException {
                builder.append((char) i);
            }
        };

        System.setOut(new PrintStream(stream));
    }

    @After
    public void tearDown() {
        System.setOut(out);
    }

    @Test
    public void testEval() {
        Formula formula = Console.eval("A&B");
        assertNotNull(formula);
        assertTrue(formula.isConjunction());
        assertEquals("A", formula.getLeft().getName());
        assertEquals("B", formula.getRight().getName());
    }

    @Test
    public void testGenerateFormula() {
        assertNotNull(Console.generateF());
    }

    @Test
    public void testGenerateSequent() {
        assertNotNull(Console.generateS());
    }

    @Test
    public void testPrintString() {
        Console.print("A&B");

        assertConsoleLines(1);
        assertConsoleLine(0, "(A&B)");
    }

    @Test
    public void testPrintFormula() {
        Console.print(Formula.parse("C&D"));

        assertConsoleLines(1);
        assertConsoleLine(0, "(C&D)");
    }

    @Test
    public void testInterpretString() {
        Console.interpret("A&B");

        assertConsoleLines(5);
        assertConsoleLine(0, "A B = OUT");
        assertConsoleLine(1, "0 0 = 0");
        assertConsoleLine(2, "0 1 = 0");
        assertConsoleLine(3, "1 0 = 0");
        assertConsoleLine(4, "1 1 = 1");
    }

    @Test
    public void testInterpretFormula() {
        Console.interpret("C&D");

        assertConsoleLines(5);
        assertConsoleLine(0, "C D = OUT");
        assertConsoleLine(1, "0 0 = 0");
        assertConsoleLine(2, "0 1 = 0");
        assertConsoleLine(3, "1 0 = 0");
        assertConsoleLine(4, "1 1 = 1");
    }

    @Test
    public void testInferString() {
        Console.infer("A=1", "A=B");

        assertConsoleLines(2);
        assertConsoleLine(0, "      A  B");
        assertConsoleLine(1, "EQ:   1  1");
    }

    @Test
    public void testInferFormula() {
        Console.infer("D=0", Formula.parse("!C⊃D"));

        assertConsoleLines(3);
        assertConsoleLine(0, "      C  D");
        assertConsoleLine(1, "MP:  -1  0");
        assertConsoleLine(2, "MT:   1  0");
    }

    @Test
    public void testInferMPStrig() {
        Console.inferMP("A=1,B=1", "A⊃CvD", "B⊃!CvD");

        assertConsoleLines(3);
        assertConsoleLine(0, "      A  B  C  D");
        assertConsoleLine(1, "MP:   1  1 -1  1");
        assertConsoleLine(2, "MT:   1  1 -1 -1");
    }

    @Test
    public void testInferMPFormula() {
        Console.inferMP("A=1,B=1", Formula.parse("A⊃CvD"), Formula.parse("B⊃!CvD"));

        assertConsoleLines(3);
        assertConsoleLine(0, "      A  B  C  D");
        assertConsoleLine(1, "MP:   1  1 -1  1");
        assertConsoleLine(2, "MT:   1  1 -1 -1");
    }

    @Test
    public void testIsContradictory() {
        assertTrue(Console.isContradictory("A&!A"));
        assertTrue(Console.isContradictory(Formula.parse("A&!A")));
        assertTrue(Console.isContradictory("A", "!A"));
        assertTrue(Console.isContradictory(Formula.parse("A"), Formula.parse("!A")));
    }

    @Test
    public void testIsTautology() {
        assertTrue(Console.isTautology("Av!A"));
        assertTrue(Console.isTautology(Formula.parse("Av!A")));
    }

    private void assertConsoleLines(int lines) {
        assertEquals(lines, builder.toString().split("\n").length);
    }

    private void assertConsoleLine(int line, String expected) {
        String result = builder.toString();

        result = result.replace(Console.ANSI_RESET, "");
        result = result.replace(Console.ANSI_BLACK, "");
        result = result.replace(Console.ANSI_RED, "");
        result = result.replace(Console.ANSI_GREEN, "");
        result = result.replace(Console.ANSI_YELLOW, "");
        result = result.replace(Console.ANSI_BLUE, "");
        result = result.replace(Console.ANSI_PURPLE, "");
        result = result.replace(Console.ANSI_CYAN, "");
        result = result.replace(Console.ANSI_WHITE, "");

        String[] lines = result.split("\n");

        assertEquals(expected, lines[line]);
    }
}

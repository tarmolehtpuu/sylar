package ee.moo.sylar.formula;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * User: tarmo
 * Date: 10/22/13
 * Time: 3:21 PM
 */
public class FormulaTest {

    @Test
    public void testConstructors() {

        Formula f1 = new Formula();

        assertNull(f1.getLeft());
        assertNull(f1.getRight());

        Formula f2 = new Formula(Formula.NodeType.CONJUNCTION);

        assertNull(f2.getLeft());
        assertNull(f2.getRight());
        assertTrue(f2.isConjunction());

        Formula f3 = new Formula(Formula.NodeType.DISJUNCTION, f1);

        assertTrue(f3.getLeft() == f1);
        assertNull(f3.getRight());
        assertTrue(f3.isDisjunction());

        Formula f4 = new Formula(Formula.NodeType.IMPLICATION, f1, f2);

        assertTrue(f4.getLeft() == f1);
        assertTrue(f4.getRight() == f2);
        assertTrue(f4.isImplication());

    }

    @Test
    public void testValue() {
        Formula left = new Formula(Formula.NodeType.STATEMENT)
                .setName("L")
                .setValue("L", 1);

        Formula right = new Formula(Formula.NodeType.STATEMENT)
                .setName("R")
                .setValue("R", 0);

        Formula formula = new Formula(Formula.NodeType.CONJUNCTION, left, right);

        assertEquals(true, formula.getValue("L"));
        assertEquals(false, formula.getValue("R"));
    }

    @Test
    public void testStatements() {
        Formula f1 = Formula.parse("A&BvC&DvA");
        Formula f2 = Formula.parse("EvBvHvJ");

        assertArrayEquals(new String[]{"A", "B", "C", "D"}, f1.getStatements());
        assertArrayEquals(new String[]{"B", "E", "H", "J"}, f2.getStatements());
    }

    @Test
    public void testName() {
        assertEquals("A", new Formula(Formula.NodeType.STATEMENT).setName("A").getName());
        assertEquals("¬", new Formula(Formula.NodeType.INVERSION).getName());
        assertEquals("&", new Formula(Formula.NodeType.CONJUNCTION).getName());
        assertEquals("∨", new Formula(Formula.NodeType.DISJUNCTION).getName());
        assertEquals("⊃", new Formula(Formula.NodeType.IMPLICATION).getName());
        assertEquals("⇔", new Formula(Formula.NodeType.EQUIVALENCE).getName());
    }

    @Test(expected = FormulaException.class)
    public void testNameNull() {
        new Formula().getName();
    }

    @Test
    public void testIsFalseAlways() {
        assertFalse(Formula.parse("AvB").isFalseAlways());
        assertTrue(Formula.parse("A&!A").isFalseAlways());
    }

    @Test
    public void testIsTrueAlways() {
        assertFalse(Formula.parse("AvB").isTrueAlways());
        assertTrue(Formula.parse("Av!A").isTrueAlways());
    }

    @Test
    public void testInversion() {
        assertTrue(Formula.parse("!A").isInversion());
        assertTrue(Formula.parse("!A").setValue("A", 0).evaluate());
        assertFalse(Formula.parse("!A").setValue("A", 1).evaluate());
    }

    @Test
    public void testConjunction() {
        assertTrue(Formula.parse("A&B").isConjunction());
        assertFalse(Formula.parse("A&B").setValue("A", 0).setValue("B", 0).evaluate());
        assertFalse(Formula.parse("A&B").setValue("A", 0).setValue("B", 1).evaluate());
        assertFalse(Formula.parse("A&B").setValue("A", 1).setValue("B", 0).evaluate());
        assertTrue(Formula.parse("A&B").setValue("A", 1).setValue("B", 1).evaluate());
    }

    @Test
    public void testDisjunction() {
        assertTrue(Formula.parse("AvB").isDisjunction());
        assertFalse(Formula.parse("AvB").setValue("A", 0).setValue("B", 0).evaluate());
        assertTrue(Formula.parse("AvB").setValue("A", 0).setValue("B", 1).evaluate());
        assertTrue(Formula.parse("AvB").setValue("A", 1).setValue("B", 0).evaluate());
        assertTrue(Formula.parse("AvB").setValue("A", 1).setValue("B", 1).evaluate());
    }

    @Test
    public void testImplication() {
        assertTrue(Formula.parse("A⊃B").isImplication());
        assertTrue(Formula.parse("A⊃B").setValue("A", 0).setValue("B", 0).evaluate());
        assertTrue(Formula.parse("A⊃B").setValue("A", 0).setValue("B", 1).evaluate());
        assertFalse(Formula.parse("A⊃B").setValue("A", 1).setValue("B", 0).evaluate());
        assertTrue(Formula.parse("A⊃B").setValue("A", 1).setValue("B", 1).evaluate());
    }

    @Test
    public void testEquivalence() {
        assertTrue(Formula.parse("A=B").isEquivalence());
        assertTrue(Formula.parse("A=B").setValue("A", 0).setValue("B", 0).evaluate());
        assertFalse(Formula.parse("A=B").setValue("A", 0).setValue("B", 1).evaluate());
        assertFalse(Formula.parse("A=B").setValue("A", 1).setValue("B", 0).evaluate());
        assertTrue(Formula.parse("A=B").setValue("A", 1).setValue("B", 1).evaluate());
    }

    @Test(expected = FormulaException.class)
    public void testEvaluateInvalid() {
        Formula formula = new Formula();

        formula.setLeft(new Formula(Formula.NodeType.STATEMENT).setName("A").setValue("A", 1));
        formula.setRight(new Formula(Formula.NodeType.STATEMENT).setName("B").setValue("B", 1));

        formula.evaluate();
    }

    @Test
    public void testToStringInversion() {
        assertEquals("¬(A)", Formula.parse("!A").toString());
    }

    @Test
    public void testToStringConjunction() {
        assertEquals("(A&B)", Formula.parse("A&B").toString());
    }

    @Test
    public void testToStringDisjunction() {
        assertEquals("(A∨B)", Formula.parse("A∨B").toString());
    }

    @Test
    public void testToStringImplication() {
        assertEquals("(A⊃B)", Formula.parse("A⊃B").toString());
    }

    @Test
    public void testToStringEquivalence() {
        assertEquals("(A⇔B)", Formula.parse("A⇔B").toString());
    }

    @Test
    public void testParseFormula() {
        Formula formula = Formula.parse("A⊃B");
        assertTrue(formula.isImplication());
        assertEquals("A", formula.getLeft().getName());
        assertEquals("B", formula.getRight().getName());
    }

    @Test
    public void testParseSequent() {
        Formula formula = Formula.parse("A,B→C,D");
        assertTrue(formula.isImplication());
        assertTrue(formula.getLeft().isConjunction());
        assertTrue(formula.getRight().isDisjunction());
    }

    @Test(expected = FormulaException.class)
    public void testParseInvalid() {
        Formula.parse("gibberish");
    }
}

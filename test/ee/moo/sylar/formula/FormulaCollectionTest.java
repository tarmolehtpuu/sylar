package ee.moo.sylar.formula;

import ee.moo.sylar.util.StringUtil;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * User: tarmo
 * Date: 10/22/13
 * Time: 5:11 PM
 */
public class FormulaCollectionTest {

    @Test
    public void testAddGet() {
        FormulaCollection c = new FormulaCollection();

        Formula f1 = Formula.parse("A");
        Formula f2 = Formula.parse("B");

        c.add(f1);
        c.add(f2);

        assertTrue(c.get(0) == f1);
        assertTrue(c.get(1) == f2);
    }

    @Test
    public void testIsEmpty() {
        FormulaCollection c = new FormulaCollection();

        assertTrue(c.isEmpty());
        c.add(Formula.parse("A"));
        assertFalse(c.isEmpty());
    }

    @Test
    public void testSize() {
        FormulaCollection c = new FormulaCollection();
        assertEquals(0, c.size());
        c.add(Formula.parse("A"));
        assertEquals(1, c.size());
        c.add(Formula.parse("B"));
        assertEquals(2, c.size());
    }


    @Test
    public void testIsContradictory() {
        FormulaCollection c1 = new FormulaCollection();
        FormulaCollection c2 = new FormulaCollection();

        c1.add(Formula.parse("A&B"));
        c1.add(Formula.parse("A"));

        c2.add(Formula.parse("A&B"));
        c2.add(Formula.parse("!A"));

        assertFalse(c1.isContradictory());
        assertTrue(c2.isContradictory());
    }

    @Test
    public void testSelect() {
        FormulaCollection c = new FormulaCollection();

        c.add(Formula.parse("A"));
        c.add(Formula.parse("A"));
        c.add(Formula.parse("B"));
        c.add(Formula.parse("B"));
        c.add(Formula.parse("A"));
        c.add(Formula.parse("A"));

        FormulaCollection result = c.select(new FormulaFilter() {
            public boolean accepts(Formula formula) {
                return StringUtil.equals("A", formula.getName());
            }
        });

        assertFalse(result.isEmpty());
        assertEquals(4, result.size());

        for (int i = 0; i < result.size(); i++) {
            assertEquals("A", result.get(i).getName());
        }
    }

    @Test
    public void testDetect() {
        FormulaCollection c = new FormulaCollection();

        c.add(Formula.parse("A"));
        c.add(Formula.parse("B"));

        Formula formula = c.detect(new FormulaFilter() {
            public boolean accepts(Formula formula) {
                return StringUtil.equals("B", formula.getName());
            }
        });

        assertNotNull(formula);
        assertEquals("B", formula.getName());

        formula = c.detect(new FormulaFilter() {
            public boolean accepts(Formula formula) {
                return StringUtil.equals("C", formula.getName());
            }
        });

        assertNull(formula);
    }

    @Test
    public void testJoinConjunction() {
        FormulaCollection c = new FormulaCollection();

        c.add(Formula.parse("A"));
        c.add(Formula.parse("B"));
        c.add(Formula.parse("C"));

        assertEquals("A&B&C", c.joinConjunction());
    }

    @Test
    public void testJoinDisjunction() {
        FormulaCollection c = new FormulaCollection();

        c.add(Formula.parse("A"));
        c.add(Formula.parse("B"));
        c.add(Formula.parse("C"));

        assertEquals("A∨B∨C", c.joinDisjunction());
    }
}

package ee.moo.skynet.inference;

import ee.moo.skynet.formula.Formula;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;

/**
 * User: tarmo
 * Date: 6/1/13
 * Time: 1:17 AM
 */
public class InferenceStrategyMPTest extends TestCase {

    private Map<String, Integer> known = new HashMap<String, Integer>();

    private InferenceStrategy strategy = new InferenceStrategyMP();

    public void testTautology() {
        known.clear();

        InferenceResult result = strategy.apply(Formula.parse("Av!A⊃B"), known);

        assertEquals(2, result.size());
        assertEquals(-1, result.get("A"));
        assertEquals(1, result.get("B"));
    }

    public void test1() {

        known.clear();
        known.put("A", 1);

        InferenceResult result = strategy.apply(Formula.parse("A⊃B"), known);

        assertEquals(2, result.size());
        assertEquals(1, result.get("A"));
        assertEquals(1, result.get("B"));
    }

    public void test2() {

        known.clear();
        known.put("A", 1);

        InferenceResult result = strategy.apply(Formula.parse("AvB⊃C"), known);

        assertEquals(3, result.size());
        assertEquals(1, result.get("A"));
        assertEquals(-1, result.get("B"));
        assertEquals(1, result.get("C"));
    }

    public void test3() {

        known.clear();
        known.put("A", 1);

        InferenceResult result = strategy.apply(Formula.parse("AvB⊃!C"), known);

        assertEquals(3, result.size());
        assertEquals(1, result.get("A"));
        assertEquals(-1, result.get("B"));
        assertEquals(0, result.get("C"));
    }

    public void test4() {

        known.clear();
        known.put("A", 1);
        known.put("B", 1);

        InferenceResult result = strategy.apply(Formula.parse("A⊃B&C"), known);

        assertEquals(3, result.size());
        assertEquals(1, result.get("A"));
        assertEquals(1, result.get("B"));
        assertEquals(1, result.get("C"));
    }

    public void test5() {
        known.clear();
        known.put("B", 0);

        InferenceResult result = strategy.apply(Formula.parse("Av!BvC⊃E&D"), known);

        assertEquals(5, result.size());
        assertEquals(0, result.get("B"));
        assertEquals(-1, result.get("A"));
        assertEquals(-1, result.get("C"));
        assertEquals(1, result.get("E"));
        assertEquals(1, result.get("D"));
    }
}

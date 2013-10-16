package ee.moo.skynet.inference;

import ee.moo.skynet.formula.Formula;
import ee.moo.skynet.formula.FormulaCollection;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * User: tarmo
 * Date: 6/1/13
 * Time: 1:17 AM
 */
public class InferenceStrategyMPTest {

    private Map<String, Integer> known = new HashMap<String, Integer>();

    private InferenceStrategy strategy = new InferenceStrategyMP();

    @Test
    public void testTautology() {
        known.clear();

        InferenceResult result = strategy.apply(new InferenceRequest(Formula.parse("Av!A⊃B"), known));

        assertEquals(2, result.size());
        assertEquals(-1, result.get("A"));
        assertEquals(1, result.get("B"));
    }

    @Test
    public void test1() {

        known.clear();
        known.put("A", 1);

        InferenceResult result = strategy.apply(new InferenceRequest(Formula.parse("A⊃B"), known));

        assertEquals(2, result.size());
        assertEquals(1, result.get("A"));
        assertEquals(1, result.get("B"));
    }

    @Test
    public void test2() {

        known.clear();
        known.put("A", 1);

        InferenceResult result = strategy.apply(new InferenceRequest(Formula.parse("AvB⊃C"), known));

        assertEquals(3, result.size());
        assertEquals(1, result.get("A"));
        assertEquals(-1, result.get("B"));
        assertEquals(1, result.get("C"));
    }

    @Test
    public void test3() {

        known.clear();
        known.put("A", 1);

        InferenceResult result = strategy.apply(new InferenceRequest(Formula.parse("AvB⊃!C"), known));

        assertEquals(3, result.size());
        assertEquals(1, result.get("A"));
        assertEquals(-1, result.get("B"));
        assertEquals(0, result.get("C"));
    }

    @Test
    public void test4() {

        known.clear();
        known.put("A", 1);
        known.put("B", 1);

        InferenceResult result = strategy.apply(new InferenceRequest(Formula.parse("A⊃B&C"), known));

        assertEquals(3, result.size());
        assertEquals(1, result.get("A"));
        assertEquals(1, result.get("B"));
        assertEquals(1, result.get("C"));
    }

    @Test
    public void test5() {
        known.clear();
        known.put("B", 0);

        InferenceResult result = strategy.apply(new InferenceRequest(Formula.parse("Av!BvC⊃E&D"), known));

        assertEquals(5, result.size());
        assertEquals(0, result.get("B"));
        assertEquals(-1, result.get("A"));
        assertEquals(-1, result.get("C"));
        assertEquals(1, result.get("E"));
        assertEquals(1, result.get("D"));
    }

    @Test
    public void test6() {
        known.clear();
        known.put("A", 1);
        known.put("D", 0);

        FormulaCollection collection = new FormulaCollection();

        collection.add(Formula.parse("A⊃BvC"));
        collection.add(Formula.parse("!D⊃!CvB"));

        InferenceRequest request = new InferenceRequest();

        request.setValues(known);
        request.setFormulaCollectionMP(collection);

        InferenceResult result = strategy.apply(request);

        assertEquals(1, result.get("A"));
        assertEquals(1, result.get("B"));
        assertEquals(-1, result.get("C"));
        assertEquals(0, result.get("D"));
    }
}

package ee.moo.sylar.inference;

import ee.moo.sylar.formula.Formula;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * User: tarmo
 * Date: 6/1/13
 * Time: 1:35 AM
 */
public class InferenceStrategyMTTest {

    private Map<String, Integer> known = new HashMap<String, Integer>();

    private InferenceStrategy strategy = new InferenceStrategyMT();

    @Test
    public void test1() {

        known.clear();

        InferenceResult result = strategy.apply(new InferenceRequest(Formula.parse("B⊃A&!A"), known));

        assertEquals(2, result.size());
        assertEquals(0, result.get("B"));
        assertEquals(-1, result.get("A"));
    }

    @Test
    public void test2() {
    }
}

package ee.moo.skynet.inference;

import ee.moo.skynet.formula.Formula;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;

/**
 * User: tarmo
 * Date: 6/1/13
 * Time: 1:35 AM
 */
public class InferenceStrategyMTTest extends TestCase {

    private Map<String, Integer> known = new HashMap<String, Integer>();

    private InferenceStrategy strategy = new InferenceStrategyMT();

    public void test1() {

        known.clear();

        InferenceResult result = strategy.apply(new InferenceRequest(Formula.parse("B⊃A&!A"), known));

        assertEquals(2, result.size());
        assertEquals(0, result.get("B"));
        assertEquals(-1, result.get("A"));
    }

    public void test2() {
    }
}

package ee.moo.skynet.inference;

import ee.moo.skynet.Formula;

import java.util.Map;

/**
 * User: tarmo
 * Date: 5/31/13
 * Time: 1:57 PM
 */
public interface InferenceStrategy {

    InferenceResult apply(Formula formula, Map<String, Integer> values);

}

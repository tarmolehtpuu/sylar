package ee.moo.skynet.inference;

/**
 * User: tarmo
 * Date: 5/31/13
 * Time: 1:57 PM
 */
public interface InferenceStrategy {

    public abstract InferenceResult apply(InferenceRequest request);

}

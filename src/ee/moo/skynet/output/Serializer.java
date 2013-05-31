package ee.moo.skynet.output;

import ee.moo.skynet.formula.Formula;

/**
 * User: tarmo
 * Date: 3/28/13
 * Time: 4:26 PM
 */
public abstract class Serializer {

    public abstract String serialize(Formula formula);

}

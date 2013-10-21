package ee.moo.sylar.output;

import ee.moo.sylar.formula.Formula;

/**
 * User: tarmo
 * Date: 3/28/13
 * Time: 4:26 PM
 */
public abstract class Serializer {

    public abstract String serialize(Formula formula);

}

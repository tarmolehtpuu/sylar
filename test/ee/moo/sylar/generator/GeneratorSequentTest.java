package ee.moo.sylar.generator;

import ee.moo.sylar.formula.Formula;
import org.junit.Test;

/**
 * User: tarmo
 * Date: 10/21/13
 * Time: 8:57 AM
 */
public class GeneratorSequentTest {

    @Test
    public void testGenerate() {
        for (int i = 0; i < 100; i++) {
            Formula.parse(new GeneratorSequent().generate());
        }
    }
}

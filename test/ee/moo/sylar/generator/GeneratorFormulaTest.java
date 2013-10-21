package ee.moo.sylar.generator;

import ee.moo.sylar.formula.Formula;
import org.junit.Test;

/**
 * User: tarmo
 * Date: 10/21/13
 * Time: 9:02 AM
 */
public class GeneratorFormulaTest {

    @Test
    public void testGenerate() {
        for (int i = 0; i < 100; i++) {
            Formula.parse(new GeneratorFormula().generate());
        }
    }
}

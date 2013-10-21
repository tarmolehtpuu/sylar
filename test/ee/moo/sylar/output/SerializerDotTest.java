package ee.moo.sylar.output;

import ee.moo.sylar.formula.Formula;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * User: tarmo
 * Date: 10/21/13
 * Time: 4:39 PM
 */
public class SerializerDotTest {

    @Test
    public void testSerialize() {

        StringBuilder expected = new StringBuilder();

        expected.append("Digraph Tree {\n");
        expected.append("  node[font=\"Arial\", shape=box];\n");
        expected.append("  node1[label=\"∨\"];\n");
        expected.append("  node2[label=\"∨\"];\n");
        expected.append("  node1 -> node2;\n");
        expected.append("  node3[label=\"∨\"];\n");
        expected.append("  node1 -> node3;\n");
        expected.append("  node2[label=\"A\"];\n");
        expected.append("  node3[label=\"B\"];\n");
        expected.append("}\n");

        assertEquals(expected.toString(), new SerializerDot().serialize(Formula.parse("AvB")));
    }
}

package ee.moo.skynet.output;

import ee.moo.skynet.Formula;

import java.util.HashMap;
import java.util.Map;

/**
 * User: tarmo
 * Date: 3/28/13
 * Time: 4:26 PM
 */
public class SerializerDot extends Serializer {

    private Map<Formula, String> names;

    private StringBuilder builder;

    private int depth;
    private int count;

    @Override
    public String serialize(Formula formula) {

        builder = new StringBuilder();

        depth = 0;
        count = 0;
        names = new HashMap<Formula, String>();

        setDepth(0);
        writeStartGraph("Tree");

        setDepth(1);
        writeNodeConfig("Arial", "box");
        writeFormula(formula);

        setDepth(0);
        writeEndGraph();

        return builder.toString();
    }

    private void writeFormula(Formula formula) {

        String name = getName(formula);
        String data = formula.getName();

        writeNode(name, data);

        if (formula.getLeft() != null) {

            String nameLeft = getName(formula.getLeft());
            String dataLeft = formula.getName();

            writeNode(nameLeft, dataLeft);
            writeEdge(name, nameLeft);
        }

        if (formula.getRight() != null) {

            String nameRight = getName(formula.getRight());
            String dataRight = formula.getName();

            writeNode(nameRight, dataRight);
            writeEdge(name, nameRight);
        }

        if (formula.getLeft() != null) {
            writeFormula(formula.getLeft());
        }

        if (formula.getRight() != null) {
            writeFormula(formula.getRight());
        }

    }

    private String getName(Formula formula) {

        if (!names.containsKey(formula)) {
            names.put(formula, String.format("node%d", ++count));
        }

        return names.get(formula);

    }

    private void setDepth(int depth) {
        this.depth = depth;
    }

    private void writeNode(String name, String data) {
        writeLine(String.format("%s[label=\"%s\"];", name, data));
    }

    private void writeEdge(String from, String to) {
        writeLine(String.format("%s -> %s;", from, to));
    }

    private void writeNodeConfig(String font, String shape) {
        writeLine(String.format("node[font=\"%s\", shape=%s];", font, shape));
    }

    private void writeStartGraph(String name) {
        writeLine(String.format("Digraph %s {", name));
    }

    private void writeEndGraph() {
        writeLine("}");
    }

    private void writeLine(String line) {

        for (int i = 0; i < depth; i++) {
            builder.append("  ");
        }

        builder.append(line);
        builder.append("\n");
    }

}

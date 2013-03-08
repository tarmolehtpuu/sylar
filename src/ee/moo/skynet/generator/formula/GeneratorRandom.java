package ee.moo.skynet.generator.formula;

import ee.moo.skynet.generator.Generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * User: tarmo
 * Date: 3/4/13
 * Time: 10:21 PM
 */
public class GeneratorRandom implements Generator {

    private static final String TYPE_TERM = "T";
    private static final String TYPE_UNARY = "U";
    private static final String TYPE_BINARY = "B";

    private static final String[] TERMS = {
            "A", "B", "C", "D", "E",
            "F", "G", "H", "I", "J",
    };

    private static final String[] OPERATOR_UNARY = {
            "!"
    };

    private static final String[] OPERATOR_BINARY = new String[]{
            "∨", "&", "⊃", "⇔"
    };

    private Random random;

    private List<String> types;

    public GeneratorRandom() {
        this.random = new Random();
        this.types = new ArrayList<String>(3);
    }

    @Override
    public String generate() {

        StringBuilder result = new StringBuilder();

        types.clear();
        types.add(TYPE_TERM);
        types.add(TYPE_UNARY);


        while (true) {

            String type = types.get(random.nextInt(types.size()));

            if (type.equals(TYPE_TERM)) {

                result.append(random(TERMS));

                // 50% chance of stopping after a term, given that result already contains an implication
                if (result.indexOf("⊃") != -1 && random.nextInt(100) > 50) {
                    break;
                }

                types.add(TYPE_BINARY);
                types.remove(TYPE_TERM);
                types.remove(TYPE_UNARY);

            } else if (type.equals(TYPE_UNARY)) {

                result.append(random(OPERATOR_UNARY));

            } else if (type.equals(TYPE_BINARY)) {

                result.append(' ');
                result.append(random(OPERATOR_BINARY));
                result.append(' ');

                types.add(TYPE_TERM);
                types.add(TYPE_UNARY);
                types.remove(TYPE_BINARY);
            }
        }

        return result.toString();

    }

    @Override
    public boolean isDone() {
        return false;
    }

    private String random(String[] array) {
        return array[random.nextInt(array.length)];
    }

    public static void main(String[] args) {

        Generator generator = new GeneratorRandom();

        for (int i = 0; i < 5040; i++) {
            System.out.println(generator.generate());
        }

    }


}

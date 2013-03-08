package ee.moo.skynet.generator.formula;

import ee.moo.skynet.generator.Generator;

import java.util.Random;

/**
 * User: tarmo
 * Date: 3/4/13
 * Time: 10:21 PM
 */
public class GeneratorMarkov implements Generator {

    private static final int STATE_TERM = 0;
    private static final int STATE_NOT = 1;
    private static final int STATE_OR = 2;
    private static final int STATE_AND = 3;
    private static final int STATE_IMP = 4;
    private static final int STATE_EQ = 5;

    private static final String[] TERMS = {
            "A", "B", "C", "D", "E",
            "F", "G", "H", "I", "J"
    };

    private static final String[] OPERATORS = {
            "",
            "!",
            "∨",
            "&",
            "⊃",
            "⇔"
    };

    private static final double[][] MATRIX = {
            // T     !     ∨     &     ⊃     ⇔
            {0.00, 0.00, 0.30, 0.60, 0.90, 1.00}, // T
            {0.80, 1.00, 0.00, 0.00, 0.00, 0.00}, // !
            {0.80, 1.00, 0.00, 0.00, 0.00, 0.00}, // ∨
            {0.80, 1.00, 0.00, 0.00, 0.00, 0.00}, // &
            {0.80, 1.00, 0.00, 0.00, 0.00, 0.00}, // ⊃
            {0.80, 1.00, 0.00, 0.00, 0.00, 0.00}, // ⇔
    };

    private Random random;

    private int state;


    public GeneratorMarkov() {
        this.random = new Random();
    }

    @Override
    public String generate() {

        state = STATE_AND;

        StringBuilder result = new StringBuilder();

        while (true) {
            result.append(step());

            // 50% chance of stopping after a term, given that result already contains an implication
            if (result.indexOf("⊃") != -1 && state == STATE_TERM && random.nextInt(100) > 50) {
                break;
            }
        }

        return result.toString();
    }

    @Override
    public boolean isDone() {
        return false;
    }

    private String step() {

        double roll = random.nextDouble();

        for (int i = 0; i < MATRIX[state].length; i++) {

            if (roll <= MATRIX[state][i]) {

                state = i;

                switch (state) {

                    case STATE_TERM:
                        return TERMS[random.nextInt(TERMS.length)];

                    case STATE_NOT:
                        return OPERATORS[state];

                    case STATE_AND:
                    case STATE_OR:
                    case STATE_IMP:
                    case STATE_EQ:

                        StringBuilder builder = new StringBuilder();

                        builder.append(' ');
                        builder.append(OPERATORS[state]);
                        builder.append(' ');

                        return builder.toString();


                    default:
                        throw new RuntimeException(String.format("Illegal state: %d", state));

                }
            }
        }

        return "";
    }

    public static void main(String[] args) {

        Generator generator = new GeneratorMarkov();

        for (int i = 0; i < 5040; i++) {
            System.out.println(generator.generate());
        }
    }

}

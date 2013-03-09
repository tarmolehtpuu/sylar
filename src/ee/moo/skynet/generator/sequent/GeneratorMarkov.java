package ee.moo.skynet.generator.sequent;

import ee.moo.skynet.generator.Generator;

import java.util.*;

/**
 * User: tarmo
 * Date: 3/8/13
 * Time: 9:19 PM
 */
public class GeneratorMarkov implements Generator {

    private static final int STATE_ATOM = 0;
    private static final int STATE_NOT = 1;
    private static final int STATE_OR = 2;
    private static final int STATE_AND = 3;
    private static final int STATE_IMP = 4;
    private static final int STATE_SEQ = 5;
    private static final int STATE_LEFT = 6;
    private static final int STATE_RIGHT = 7;
    private static final int STATE_STOP = 8;

    private static final String[] ATOMS = {
            "A", "B", "C", "D", "E",
            "F", "G", "H", "I", "J"
    };

    private static final String[] OPERATORS = {
            "",
            "!",
            "∨",
            "&",
            "⊃",
            "→",
            "(",
            ")"
    };

    private static final double[][] MATRIX = {
            // T     !     ∨     &     ⊃     →     (     )
            {0.00, 0.00, 0.25, 0.25, 0.10, 0.30, 0.00, 0.10}, // T
            {0.90, 0.00, 0.00, 0.00, 0.00, 0.00, 0.10, 0.00}, // !
            {0.70, 0.20, 0.00, 0.00, 0.00, 0.00, 0.10, 0.00}, // ∨
            {0.70, 0.20, 0.00, 0.00, 0.00, 0.00, 0.10, 0.00}, // &
            {0.70, 0.20, 0.00, 0.00, 0.00, 0.00, 0.10, 0.00}, // ⊃
            {0.70, 0.20, 0.00, 0.00, 0.00, 0.00, 0.10, 0.00}, // →
            {0.80, 0.20, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00}, // (
            {0.00, 0.00, 0.10, 0.10, 0.10, 0.60, 0.00, 0.10}, // )

    };

    private Random random;

    private Map<Integer, List<Integer>> matrix;

    private int state;
    private int depth;

    private StringBuilder result;

    public GeneratorMarkov() {

        random = new Random();
        matrix = new HashMap<Integer, List<Integer>>();

        for (int i = 0; i < MATRIX.length; i++) {

            List<Integer> list = new ArrayList<Integer>();

            for (int j = 0; j < MATRIX[i].length; j++) {

                for (int k = 0; k < (int) (MATRIX[i][j] * 1000); k++) {
                    list.add(j);
                }
            }

            matrix.put(i, list);
        }

    }

    @Override
    public String generate() {

        state = STATE_AND;
        depth = 0;

        result = new StringBuilder();

        while (true) {

            result.append(step());

            if (state == STATE_STOP) {
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

        Collections.shuffle(matrix.get(state));

        int statePrev = state;
        int stateNext = matrix.get(statePrev).get(random.nextInt(matrix.get(statePrev).size()));

        StringBuilder builder = new StringBuilder();

        switch (stateNext) {

            case STATE_ATOM:

                builder.append(ATOMS[random.nextInt(ATOMS.length)]);

                // 30% chance of stopping after an atom,
                // given that result already contains a sequent sign

                if (result.indexOf(OPERATORS[STATE_SEQ]) != -1 && random.nextInt(100) > 70) {
                    stateNext = STATE_STOP;

                    // make sure all right parentheses are closed before
                    // formula is considered complete
                    while (depth > 0) {
                        builder.append(OPERATORS[STATE_RIGHT]);
                        depth--;
                    }
                }

                break;

            case STATE_NOT:

                builder.append(OPERATORS[stateNext]);
                break;

            case STATE_AND:
            case STATE_OR:
            case STATE_IMP:

                builder.append(' ');
                builder.append(OPERATORS[stateNext]);
                builder.append(' ');
                break;

            case STATE_SEQ:

                // only allow one sequent sign

                if (result.indexOf(OPERATORS[STATE_SEQ]) != -1) {
                    return step();
                }

                // make sure all parentheses are closed before sequent sign
                while (depth > 0) {
                    builder.append(OPERATORS[STATE_RIGHT]);
                    depth--;
                }

                builder.append(' ');
                builder.append(OPERATORS[stateNext]);
                builder.append(' ');
                break;

            case STATE_LEFT:

                builder.append(OPERATORS[stateNext]);
                depth++;
                break;

            case STATE_RIGHT:

                if (depth <= 0) {

                    // right parentheses not allowed, try steping again

                    state = statePrev;
                    return step();

                } else {
                    builder.append(OPERATORS[stateNext]);
                    depth--;
                    break;
                }


            default:
                throw new RuntimeException(String.format("Illegal state: %d", state));

        }

        state = stateNext;

        return builder.toString();

    }

    public static void main(String[] args) {

        Generator generator = new GeneratorMarkov();

        for (int i = 0; i < 5040; i++) {
            System.out.println(generator.generate());
        }
    }
}

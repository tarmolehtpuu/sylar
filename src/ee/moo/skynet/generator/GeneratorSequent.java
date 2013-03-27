package ee.moo.skynet.generator;

import ee.moo.skynet.Formula;
import ee.moo.skynet.alphabet.AlphabetSequent;

import java.util.*;

/**
 * User: tarmo
 * Date: 3/8/13
 * Time: 9:19 PM
 */
public class GeneratorSequent implements Generator {

    private static final int STATE_STATEMENT = 0;
    private static final int STATE_INVERSION = 1;
    private static final int STATE_DISJUNCTION = 2;
    private static final int STATE_CONJUNCTION = 3;
    private static final int STATE_IMPLICATION = 4;
    private static final int STATE_SEQUENT = 5;
    private static final int STATE_LEFT = 6;
    private static final int STATE_RIGHT = 7;
    private static final int STATE_STOP = 8;

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

    private AlphabetSequent alphabet;

    private Map<Integer, List<Integer>> matrix;

    private int state;
    private int depth;

    private StringBuilder result;

    public GeneratorSequent() {

        alphabet = new AlphabetSequent();

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

        state = STATE_CONJUNCTION;
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

    private String step() {

        Collections.shuffle(matrix.get(state));

        int statePrev = state;
        int stateNext = matrix.get(statePrev).get(random.nextInt(matrix.get(statePrev).size()));

        StringBuilder builder = new StringBuilder();

        switch (stateNext) {

            case STATE_STATEMENT:

                builder.append(STATEMENTS[random.nextInt(STATEMENTS.length)]);

                // 30% chance of stopping after a statement,
                // given that result already contains a sequent sign

                if (result.indexOf(alphabet.getSymbolSequentString()) != -1 && random.nextInt(100) > 70) {
                    stateNext = STATE_STOP;

                    // make sure all right parentheses are closed before
                    // formula is considered complete
                    while (depth > 0) {
                        builder.append(alphabet.getSymbolRightParenthesis());
                        depth--;
                    }
                }

                break;

            case STATE_INVERSION:

                builder.append(alphabet.getSymbolInversion());
                break;

            case STATE_CONJUNCTION:

                builder.append(alphabet.getSymbolWhitespace());
                builder.append(alphabet.getSymbolConjunction());
                builder.append(alphabet.getSymbolWhitespace());
                break;

            case STATE_DISJUNCTION:

                builder.append(alphabet.getSymbolWhitespace());
                builder.append(alphabet.getSymbolDisjunction());
                builder.append(alphabet.getSymbolWhitespace());
                break;

            case STATE_IMPLICATION:

                builder.append(alphabet.getSymbolWhitespace());
                builder.append(alphabet.getSymbolImplication());
                builder.append(alphabet.getSymbolWhitespace());
                break;

            case STATE_SEQUENT:

                // only allow one sequent sign

                if (result.indexOf(alphabet.getSymbolSequentString()) != -1) {
                    return step();
                }

                // make sure all parentheses are closed before sequent sign
                while (depth > 0) {
                    builder.append(alphabet.getSymbolRightParenthesis());
                    depth--;
                }

                builder.append(alphabet.getSymbolWhitespace());
                builder.append(alphabet.getSymbolSequent());
                builder.append(alphabet.getSymbolWhitespace());
                break;

            case STATE_LEFT:

                builder.append(alphabet.getSymbolLeftParenthesis());
                depth++;
                break;

            case STATE_RIGHT:

                if (depth <= 0) {

                    // right parentheses not allowed, try steping again

                    state = statePrev;
                    return step();

                } else {

                    builder.append(alphabet.getSymbolRightParenthesis());
                    depth--;
                    break;
                }


            default:
                throw new GeneratorException(String.format("Illegal state: %d", state));

        }

        state = stateNext;

        return builder.toString();

    }

    public static void main(String[] args) {

        Generator generator = new GeneratorSequent();

        int total = 5040;

        int totalFalse = 0;
        int totalTrue = 0;
        int totalOther = 0;

        for (int i = 0; i < total; i++) {

            String input = generator.generate();

            Formula formula = Formula.parse(input);

            if (formula.isTrueAlways()) {
                System.out.println(String.format("[T]: %s = %s", input, formula.toString()));
                totalTrue++;

            } else if (formula.isFalseAlways()) {
                System.out.println(String.format("[F]: %s = %s", input, formula.toString()));
                totalFalse++;

            } else {
                System.out.println(String.format("[O]: %s = %s", input, formula.toString()));
                totalOther++;
            }
        }

        float percentFalse = ((float) totalFalse / (float) total) * 100;
        float percentTrue = ((float) totalTrue / (float) total) * 100;
        float percentOther = ((float) totalOther / (float) total) * 100;

        System.out.println(String.format("[O]: %d (%,.2f%%), [T]: %d (%,.2f%%), [F]: %d (%,.2f%%)",
                totalOther, percentOther, totalTrue, percentTrue, totalFalse, percentFalse));
    }
}

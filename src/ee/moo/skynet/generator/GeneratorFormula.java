package ee.moo.skynet.generator;

import ee.moo.skynet.alphabet.Alphabet;
import ee.moo.skynet.alphabet.AlphabetFormula;

import java.util.Random;

/**
 * User: tarmo
 * Date: 3/4/13
 * Time: 10:21 PM
 */
public class GeneratorFormula implements Generator {

    private static final int STATE_STATEMENT = 0;
    private static final int STATE_INVERSION = 1;
    private static final int STATE_DISJUNCTION = 2;
    private static final int STATE_CONJUNCTION = 3;
    private static final int STATE_IMPLICATION = 4;
    private static final int STATE_EQUIVALENCE = 5;

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

    private Alphabet alphabet;

    private int state;

    public GeneratorFormula() {
        this.random = new Random();
        this.alphabet = new AlphabetFormula();
    }

    @Override
    public String generate() {

        state = STATE_CONJUNCTION;

        StringBuilder result = new StringBuilder();

        while (true) {
            result.append(step());

            // 50% chance of stopping after an atom, given that result already contains an implication
            if (result.indexOf(alphabet.getSymbolImplicationString()) != -1 && state == STATE_STATEMENT && random.nextInt(100) > 50) {
                break;
            }
        }

        return result.toString();
    }

    private String step() {

        double roll = random.nextDouble();

        for (int i = 0; i < MATRIX[state].length; i++) {

            if (roll <= MATRIX[state][i]) {

                state = i;

                StringBuilder builder = new StringBuilder();

                switch (state) {

                    case STATE_STATEMENT:
                        return STATEMENTS[random.nextInt(STATEMENTS.length)];

                    case STATE_INVERSION:
                        return alphabet.getSymbolInversionString();

                    case STATE_CONJUNCTION:

                        builder.append(alphabet.getSymbolWhitespace());
                        builder.append(alphabet.getSymbolConjunction());
                        builder.append(alphabet.getSymbolWhitespace());

                        return builder.toString();

                    case STATE_DISJUNCTION:

                        builder.append(alphabet.getSymbolWhitespace());
                        builder.append(alphabet.getSymbolDisjunction());
                        builder.append(alphabet.getSymbolWhitespace());

                        return builder.toString();

                    case STATE_IMPLICATION:

                        builder.append(alphabet.getSymbolWhitespace());
                        builder.append(alphabet.getSymbolImplication());
                        builder.append(alphabet.getSymbolWhitespace());

                        return builder.toString();

                    case STATE_EQUIVALENCE:

                        builder.append(alphabet.getSymbolWhitespace());
                        builder.append(alphabet.getSymbolEquivalence());
                        builder.append(alphabet.getSymbolWhitespace());

                        return builder.toString();

                    default:
                        throw new GeneratorException(String.format("Illegal state: %d", state));

                }
            }
        }

        return "";
    }

    public static void main(String[] args) {

        Generator generator = new GeneratorFormula();

        for (int i = 0; i < 5040; i++) {
            System.out.println(generator.generate());
        }
    }

}

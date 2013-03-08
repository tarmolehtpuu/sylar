package ee.moo.skynet.generator.formula;

import ee.moo.skynet.generator.Generator;

import java.util.ArrayList;
import java.util.List;

/**
 * User: tarmo
 * Date: 3/5/13
 * Time: 12:01 AM
 */
public class GeneratorPermutation implements Generator {

    // 7! or 5040 permutations, most of them invalid

    private static final String[] SOURCE = {
            "A", "B", "C", "&", "∨", "⊃", "!"
    };

    private List<String> permutations;

    @Override
    public String generate() {

        if (permutations == null) {

            permutations = new ArrayList<String>();

            String start = "";
            String end = "";

            for (String s : SOURCE) {
                end += s;
            }

            permute(start, end);

        }

        return permutations.remove(0);

    }

    @Override
    public boolean isDone() {
        return permutations != null && permutations.size() == 0;
    }

    private void permute(String start, String end) {

        // not a very efficient implementation, keeps all permutations in memory,
        // but perhaps permutations is not the best way to go ...

        if (end.length() <= 1) {
            permutations.add(String.format("%s%s", start, end));

        } else {

            for (int i = 0; i < end.length(); i++) {
                permute(start + end.charAt(i), end.substring(0, i) + end.substring(i + 1));
            }
        }
    }

    public static void main(String[] args) {

        Generator generator = new GeneratorPermutation();

        while (!generator.isDone()) {
            System.out.println(generator.generate());
        }

    }
}

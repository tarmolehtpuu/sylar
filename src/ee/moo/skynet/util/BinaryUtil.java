package ee.moo.skynet.util;

/**
 * User: tarmo
 * Date: 4/2/13
 * Time: 12:26 PM
 */
public class BinaryUtil {

    public static int[][] permutations(int n) {

        int[][] permutations = new int[(int) Math.pow(2, n)][n];

        for (int i = 0; i < Math.pow(2, n); i++) {

            StringBuilder builder = new StringBuilder(Integer.toBinaryString(i));

            while (builder.length() < n) {
                builder.insert(0, '0');
            }

            for (int j = 0; j < n; j++) {

                if (builder.charAt(j) == '1') {
                    permutations[i][j] = 1;
                } else {
                    permutations[i][j] = 0;
                }
            }
        }

        return permutations;
    }
}

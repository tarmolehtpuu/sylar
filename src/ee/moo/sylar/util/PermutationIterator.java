package ee.moo.sylar.util;

import java.math.BigInteger;

/**
 * User: tarmo
 * Date: 7/14/13
 * Time: 10:26 PM
 */
public class PermutationIterator {

    private int size;

    private BigInteger min;

    private BigInteger max;

    private BigInteger cur;

    public PermutationIterator(int size) {
        this.size = size;
        this.min = BigInteger.valueOf(0);
        this.max = BigInteger.valueOf(2).pow(size);
        this.cur = BigInteger.valueOf(0);
    }

    public PermutationIterator(int size, BigInteger min, BigInteger max) {
        this.size = size;
        this.min = min;
        this.max = max;
        this.cur = min;

        if (this.min.compareTo(BigInteger.ZERO) < 0) {
            throw new IllegalArgumentException("min must be greater than zero.");
        }

        if (this.max.compareTo(BigInteger.valueOf(2).pow(size)) > 0) {
            throw new IllegalArgumentException("max must be less than 2**n");
        }
    }


    public boolean hasNext() {
        return cur.compareTo(max) < 0;
    }

    public int[] next() {

        if (cur.compareTo(max) >= 0) {
            return null;
        }

        StringBuilder builder = new StringBuilder(cur.toString(2));

        while (builder.length() < size) {
            builder.insert(0, '0');
        }

        int[] result = new int[size];

        for (int i = 0; i < size; i++) {

            if (builder.charAt(i) == '1') {
                result[i] = 1;
            } else {
                result[i] = 0;
            }
        }

        cur = cur.add(BigInteger.ONE);

        return result;
    }
}

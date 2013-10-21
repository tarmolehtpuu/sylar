package ee.moo.sylar.util;

import java.util.Random;

/**
 * User: tarmo
 * Date: 3/23/13
 * Time: 11:40 PM
 */
public class StringUtil {

    private static final Random RANDOM = new Random();
    private static final char[] ALPHABET;

    static {
        ALPHABET = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
    }

    public static boolean isEmpty(String s) {
        return s == null || s.length() == 0;
    }

    public static boolean equals(String s1, String s2) {

        if (s1 == null && s2 == null) {
            return true;

        } else if (s1 != null) {
            return s1.equals(s2);

        } else {
            return s2.equals(s1);

        }
    }

    public static boolean equalsIgnoreCase(String s1, String s2) {

        if (s1 == null && s2 == null) {
            return true;

        } else if (s1 != null) {
            return s1.equalsIgnoreCase(s2);

        } else {
            return s2.equalsIgnoreCase(s1);

        }
    }

    public static String lpad(String string, int length, char pad) {

        StringBuilder builder = new StringBuilder(string);

        while (builder.length() < length) {
            builder.insert(0, pad);
        }

        return builder.toString();
    }

    public static String rpad(String string, int length, char pad) {

        StringBuilder builder = new StringBuilder(string);

        while (builder.length() < length) {
            builder.append(pad);
        }

        return builder.toString();

    }

    public static String random(int length) {

        StringBuilder result = new StringBuilder(length);

        while (result.length() < length) {
            result.append(ALPHABET[RANDOM.nextInt(ALPHABET.length)]);
        }

        return result.toString();
    }
}

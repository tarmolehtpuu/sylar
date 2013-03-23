package ee.moo.skynet.util;

/**
 * User: tarmo
 * Date: 3/23/13
 * Time: 11:40 PM
 */
public class StringUtil {

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
}

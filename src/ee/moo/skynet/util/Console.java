package ee.moo.skynet.util;

import ee.moo.skynet.Formula;

/**
 * User: tarmo
 * Date: 3/28/13
 * Time: 2:56 AM
 */
public class Console {

    public static Formula e(String input) {
        return Formula.parse(input);
    }

    public static void p(String input) {
        System.out.println(Formula.parse(input));
    }

    public static void i(String input) {
    }
}

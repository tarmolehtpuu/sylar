package ee.moo.skynet.util;

import junit.framework.TestCase;

/**
 * User: tarmo
 * Date: 10/15/13
 * Time: 11:22 PM
 */
public class StringUtilTest extends TestCase {

    public void testIsEmpty() {
        assertTrue(StringUtil.isEmpty(""));
        assertTrue(StringUtil.isEmpty(null));
        assertFalse(StringUtil.isEmpty("hello"));
    }

    public void testEquals() {
        assertTrue(StringUtil.equals(null, null));
        assertTrue(StringUtil.equals("", ""));
        assertTrue(StringUtil.equals("foo", "foo"));
        assertFalse(StringUtil.equals(null, "foo"));
        assertFalse(StringUtil.equals("foo", null));
        assertFalse(StringUtil.equals("foo", "bar"));
        assertFalse(StringUtil.equals("foo", "FOO"));
    }

    public void testEqualsIgnoreCase() {
        assertTrue(StringUtil.equalsIgnoreCase(null, null));
        assertTrue(StringUtil.equalsIgnoreCase("", ""));
        assertTrue(StringUtil.equalsIgnoreCase("foo", "foo"));
        assertTrue(StringUtil.equalsIgnoreCase("FOO", "foo"));
        assertFalse(StringUtil.equalsIgnoreCase(null, "foo"));
        assertFalse(StringUtil.equalsIgnoreCase("foo", null));
        assertFalse(StringUtil.equalsIgnoreCase("foo", "bar"));
    }

    public void testLpad() {
        assertEquals("XXXX1", StringUtil.lpad("1", 5, 'X'));
    }

    public void testRpad() {
        assertEquals("1XXXX", StringUtil.rpad("1", 5, 'X'));
    }

    public void testRandom() {
        assertEquals(5, StringUtil.random(5).length());
        assertEquals(4, StringUtil.random(4).length());
        assertEquals(3, StringUtil.random(3).length());
    }
}

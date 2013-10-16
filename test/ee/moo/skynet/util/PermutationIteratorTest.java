package ee.moo.skynet.util;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.*;

/**
 * User: tarmo
 * Date: 10/16/13
 * Time: 4:17 PM
 */
public class PermutationIteratorTest {

    @Test
    public void testFull() {
        PermutationIterator i = new PermutationIterator(3);

        assertTrue(i.hasNext());

        assertArrayEquals(new int[]{0, 0, 0}, i.next());
        assertArrayEquals(new int[]{0, 0, 1}, i.next());
        assertArrayEquals(new int[]{0, 1, 0}, i.next());
        assertArrayEquals(new int[]{0, 1, 1}, i.next());
        assertArrayEquals(new int[]{1, 0, 0}, i.next());
        assertArrayEquals(new int[]{1, 0, 1}, i.next());
        assertArrayEquals(new int[]{1, 1, 0}, i.next());
        assertArrayEquals(new int[]{1, 1, 1}, i.next());

        assertNull(i.next());
        assertFalse(i.hasNext());
    }

    @Test
    public void testPartial() {
        PermutationIterator i = new PermutationIterator(3, BigInteger.ZERO, BigInteger.valueOf(4));

        assertTrue(i.hasNext());

        assertArrayEquals(new int[]{0, 0, 0}, i.next());
        assertArrayEquals(new int[]{0, 0, 1}, i.next());
        assertArrayEquals(new int[]{0, 1, 0}, i.next());
        assertArrayEquals(new int[]{0, 1, 1}, i.next());

        assertNull(i.next());
        assertFalse(i.hasNext());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPartialBoundsLower() {
        new PermutationIterator(3, BigInteger.valueOf(-1), BigInteger.valueOf(4));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPartialBoundsUpper() {
        new PermutationIterator(3, BigInteger.ZERO, BigInteger.valueOf(9));
    }
}

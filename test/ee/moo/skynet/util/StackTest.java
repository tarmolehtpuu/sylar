package ee.moo.skynet.util;

import junit.framework.TestCase;

/**
 * User: tarmo
 * Date: 10/15/13
 * Time: 11:22 PM
 */
public class StackTest extends TestCase {

    private Stack<String> stack;

    protected void setUp() {
        stack = new Stack<String>();
    }

    public void testPushPop() {
        stack.push("foo");
        stack.push("bar");

        assertFalse(stack.isEmpty());
        assertEquals("bar", stack.pop());
        assertEquals("foo", stack.pop());
        assertTrue(stack.isEmpty());
    }

    public void testPopEmpty() {
        try {
            stack.pop();
            fail("Expecting an IllegalStateException to be thrown!");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    public void testPushPeek() {
        stack.push("foo");
        stack.push("bar");

        assertFalse(stack.isEmpty());
        assertEquals("bar", stack.peek());
        assertEquals("bar", stack.pop());
        assertEquals("foo", stack.peek());
        assertEquals("foo", stack.pop());
        assertTrue(stack.isEmpty());
    }

    public void testPushPeekEmpty() {
        try {
            stack.peek();
            fail("Expecting an IllegalStateException to be thrown!");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    public void testToString() {
        stack.push("foo");
        stack.push("bar");
        stack.push("baz");

        StringBuilder content = new StringBuilder();

        content.append('\n');
        content.append("  ");
        content.append("baz");
        content.append('\n');
        content.append("  ");
        content.append("bar");
        content.append('\n');
        content.append("  ");
        content.append("foo");
        content.append('\n');

        assertEquals(String.format("Stack[%s]", content), stack.toString());
    }

    public void testToStringEmpty() {
        assertEquals("Stack[EMPTY]", stack.toString());
    }
}

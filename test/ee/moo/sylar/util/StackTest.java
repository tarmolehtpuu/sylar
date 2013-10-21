package ee.moo.sylar.util;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * User: tarmo
 * Date: 10/15/13
 * Time: 11:22 PM
 */
public class StackTest {

    private Stack<String> stack;

    @Before
    public void setUp() {
        stack = new Stack<String>();
    }

    @Test
    public void testPushPop() {
        stack.push("foo");
        stack.push("bar");

        assertFalse(stack.isEmpty());
        assertEquals("bar", stack.pop());
        assertEquals("foo", stack.pop());
        assertTrue(stack.isEmpty());
    }

    @Test(expected = IllegalStateException.class)
    public void testPopEmpty() {
        stack.pop();
    }

    @Test
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

    @Test(expected = IllegalStateException.class)
    public void testPushPeekEmpty() {
        stack.peek();
    }

    @Test
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

    @Test
    public void testToStringEmpty() {
        assertEquals("Stack[EMPTY]", stack.toString());
    }
}

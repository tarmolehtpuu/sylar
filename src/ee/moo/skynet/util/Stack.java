package ee.moo.skynet.util;

/**
 * User: tarmo
 * Date: 3/23/13
 * Time: 11:49 PM
 */
public class Stack<T> {

    private class Node<T> {

        private Node<T> next;

        private T data;

        public Node(T data) {
            this.data = data;
        }
    }

    private Node<T> first;

    public boolean isEmpty() {
        return this.first == null;
    }

    public void push(T item) {

        Node<T> node = new Node<T>(item);

        if (!isEmpty()) {
            node.next = this.first;
        }

        this.first = node;
    }

    public T pop() {

        if (isEmpty()) {
            throw new IllegalStateException("Tried to call pop() on an empty stack.");
        }

        T data = this.first.data;

        // removes the 1st element
        this.first = this.first.next;

        return data;
    }

    public T peek() {

        if (isEmpty()) {
            throw new IllegalStateException("Tried to call peek() on an empty stack.");
        }

        return this.first.data;
    }

    public String toString() {

        if (isEmpty()) {
            return "Stack[EMPTY]";
        }

        StringBuilder builder = new StringBuilder();

        builder.append("\n");

        Node<T> node = this.first;

        while (node != null) {

            builder.append(' ');
            builder.append(' ');
            builder.append(node.data);

            node = node.next;

            builder.append("\n");
        }

        return String.format("Stack[%s]", builder.toString());

    }
}

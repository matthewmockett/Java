/**
 * A generic class representing a doubly linked stack
 *
 * @param <T> The type of elements stored in the stack
 */
public class DLStack<T> implements DLStackADT<T> {

  // Instance variables required
    private DoubleLinkedNode<T> top;
    private int numItems;

    /**
     * Constructor for DLStack initializes the top to null and sets numItems to 0
     */
    public DLStack() {
        top = null;
        numItems = 0;
    }

    /**
     * 		Adds an item to the top of the stack.
     * @param dataItem the item to be added
     */
    @Override
    public void push(T dataItem) {
        // Adds the dataItem to the top of the stack.
        if (top == null) {
            top = new DoubleLinkedNode<T>(dataItem);
        } else {
            DoubleLinkedNode<T> newNode = new DoubleLinkedNode<T>(dataItem);
            newNode.setPrevious(top);
            top.setNext(newNode);
            top = newNode;
            newNode.setNext(null);
        }
        numItems++;
    }

    /**
     * Returns the top of the stack and deletes it from the stack
     * @return the top of the stack
     * @throws EmptyStackException if stack is empty
     */
    @Override
    public T pop() throws EmptyStackException {
        if (top == null) {
            throw new EmptyStackException("Stack is empty");
        } else {
            T itemPopped = top.getElement();
            if (top.getPrevious() == null) {
                top = null;
            } else {
                top = top.getPrevious();
                top.setNext(null);
            }
            numItems--;
            return itemPopped;
        }
    }

    /**
     * Returns the top of the stack without removing it
     * @return  top of the stack, and null if stack is empty
     * @throws EmptyStackException if the stack is empty
     */
    @Override
    public T peek() throws EmptyStackException {
        if (numItems == 0) {
            throw new EmptyStackException("Stack is empty");
        }
        return top.getElement();
    }

    /**
     * Returns the element at the specified index and removes it from stack
     * @param k the index of the element to be returned
     * @return the element at the specified index
     * @throws InvalidItemException if the index is invalid
     */
    @Override
    public T pop(int k) throws InvalidItemException {
        T itemPopped;
        if (k > numItems || k <= 0) {
            throw new InvalidItemException("Invalid item");
        } else if (k == 1) {
            itemPopped = pop();
        } else {
            DoubleLinkedNode<T> current = top;
            for (int i = 1; i < k - 1; i++) {
                current = current.getPrevious();
            }
            itemPopped = current.getPrevious().getElement();
            if (current.getPrevious().getPrevious() != null) {
                current.getPrevious().getPrevious().setNext(current);
                current.setPrevious(current.getPrevious().getPrevious());
            } else {
                current.setPrevious(null);
            }
        }
        numItems--;
        return itemPopped;
    }

    /**
     * Checks if stack is empty
     * @return true if stack is empty and false if not
     */
    @Override
    public boolean isEmpty() {
        return numItems == 0;
    }

    /**
     * Returns the quantity of elements in the stack
     * @return the number of elements in the stack
     */
    @Override
    public int size() {
        return numItems;
    }

    /**
     * Return top of the stack as a DoubleLinkedNode
     * @return top of the stack as a DoubleLinkedNode
     */
    @Override
    public DoubleLinkedNode<T> getTop() {
        return top;
    }

    /**
     * Returns a string where data1 is the data item at the top of the stack
     * and data is the data item at the bottom of the stack.

     * @return the string representation of the stack.
     */
    public String toString() {
        String result = "[";
        DoubleLinkedNode<T> current = top;
        while (current != null) {
            result += current.getElement();
            current = current.getPrevious();
            if (current != null) {
                result += " ";
            }
        }
        result += "]";
        return result;
    }
}

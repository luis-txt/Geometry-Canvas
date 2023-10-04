package datascructures;

/**
 * An object of this class represents a node of a MyList instance
 */
public class Node<T> {


    //Object variables
    private Node<T> next;
    private final T content;


    //Constructor
    public Node(T content) {
        this.content = content;
    }


    //Setter and Getter

    /**
     * Sets the successor (node) of the object
     *
     * @param next represents the next node object
     */
    public void setNext(Node<T> next) {
        this.next = next;
    }

    /**
     * Returns the successor (node) of this object
     *
     * @return the successor
     */
    public Node<T> getNext() {
        return this.next;
    }

    /**
     * Returns the content (value) of the node-object
     *
     * @return content (value) of the node-object
     */
    public T getContent() {
        return this.content;
    }
}

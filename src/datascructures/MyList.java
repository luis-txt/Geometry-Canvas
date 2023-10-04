package datascructures;

/**
 * Represents a list structure where each node-element only knows its successor
 */
public class MyList<T> {


    //Object variables
    private Node<T> root;
    private Node<T> currentlyLast;
    private int size = 0;


    //Constructors
    public MyList() {
    }

    public MyList(T value) {

        Node<T> root = new Node<>(value); //Root node being initialized

        this.root = root;
        this.currentlyLast = root;
        this.size++;
    }


    //Object methods

    /**
     * Adds a node with a given value at the current end of the list
     *
     * @param value the value of the new node
     */
    public void add(T value) {

        Node<T> newNode = new Node<>(value);
        this.size++;

        if (this.root == null) { //if list is empty new node becomes root

            this.root = newNode;
        } else { //new node is attached to list

            this.currentlyLast.setNext(newNode);
        }

        this.currentlyLast = newNode;
    }

    /**
     * Returns the value of the node at the desired index of the list. When there is no node with the given index null
     * is returned.
     *
     * @param i represents the index of the list
     * @return the value of the node at the desired index
     */
    public T get(int i) {

        if (i < 0 || i > this.size - 1) { //index out of bounds
            return null;
        }

        Node<T> searchNode = root;

        if (i != 0) { //when not searching for root

            while (i > 0) {

                searchNode = searchNode.getNext();
                i--;
            }
        }
        return searchNode.getContent();
    }

    /**
     * Removes the node, which contains the given integer value from the list. Returns a boolean whether the value was
     * successfully removed.
     *
     * @param value the given value which the removed node must have
     * @return true if successfully deleted node and else false
     */
    public boolean remove(T value) {

        Node<T> currentSearch = root;
        Node<T> lastNode = null;

        do {
            if (currentSearch.getContent() == value) { //if currently viewed element contains the value

                removeNode(lastNode, currentSearch);
                return true;

            } else { //point at next node

                lastNode = currentSearch;
                currentSearch = currentSearch.getNext();
            }

        } while (currentSearch != null); //check next node

        return false; //if nothing found
    }

    /**
     * Returns whether the list is empty or not
     *
     * @return true if the list is empty and false otherwise
     */
    public boolean isEmpty() {
        return root == null;
    }


    //Helping methods

    /**
     * Removes a given node of the MyList instance.
     *
     * @param lastNode   represents the predecessor of the node to be removed
     * @param removeNode the node to be removed
     */
    private void removeNode(Node<T> lastNode, Node<T> removeNode) {

        if (lastNode == null) { //when root is to be deleted, only pointer must be changed to new root
            root = root.getNext();
        } else { //pointer from predecessor must be changed to successor
            lastNode.setNext(removeNode.getNext());
        }
        //Update the head, if it is affected (only if the current element also is the head)
        if (currentlyLast == root) {
            root = lastNode;
        }

        size--; //size is being updated
    }
}
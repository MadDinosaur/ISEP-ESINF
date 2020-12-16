import java.util.*;

public class BinaryTree<E> {

    /**
     * Nested static class for a binary search tree node.
     */

    protected static class Node<E> {
        private E element;          // an element stored at this node
        private Node<E> left;       // a reference to the left child (if any)
        private Node<E> right;      // a reference to the right child (if any)

        /**
         * Constructs a node with the given element and neighbors.
         *
         * @param e          the element to be stored
         * @param leftChild  reference to a left child node
         * @param rightChild reference to a right child node
         */
        public Node(E e, Node<E> leftChild, Node<E> rightChild) {
            element = e;
            left = leftChild;
            right = rightChild;
        }

        // accessor methods
        public E getElement() {
            return element;
        }

        public Node<E> getLeft() {
            return left;
        }

        public Node<E> getRight() {
            return right;
        }

        // update methods
        public void setElement(E e) {
            element = e;
        }

        public void setLeft(Node<E> leftChild) {
            left = leftChild;
        }

        public void setRight(Node<E> rightChild) {
            right = rightChild;
        }
    }

    //----------- end of nested Node class -----------

    protected Node<E> root = null;     // root of the tree

    private Comparator comparator;

    /* Constructs an empty binary search tree. */
    public BinaryTree(Comparator compare) {
        root = null;

        this.comparator = compare;
    }

    /*
     * @return root Node of the tree (or null if tree is empty)
     */
    protected Node<E> root() {
        return root;
    }

    /*
     * Verifies if the tree is empty
     * @return true if the tree is empty, false otherwise
     */
    public boolean isEmpty() {
        return (root == null);
    }

    /*
     * Inserts an element in the tree.
     */
    public void insert(E element) {
        root = insert(element, root);
    }

    private Node<E> insert(E element, Node<E> node) {
        if (node == null) {
            return new Node<>(element, null, null);
        }
        boolean isLesser = comparator.compare(element, node.element) < 0;
        boolean isGreater = comparator.compare(element, node.element) > 0;

        if (isLesser) {
            node.setLeft(insert(element, node.left));
        } else if (isGreater) {
            node.setRight(insert(element, node.right));
        } else {
            node.setElement(element);
        }
        return node;
    }

    /**
     * Removes an element from the tree maintaining its consistency as a Binary Search Tree.
     */
    public void remove(E element) {
        root = remove(element, root());
    }

    private Node<E> remove(E element, Node<E> node) {

        if (node == null) {
            return null;    //throw new IllegalArgumentException("Element does not exist");
        }
        if (comparator.compare(element, node.element) == 0) {
            // node is the Node to be removed
            if (node.getLeft() == null && node.getRight() == null) { //node is a leaf (has no childs)
                return null;
            }
            if (node.getLeft() == null) {   //has only right child
                return node.getRight();
            }
            if (node.getRight() == null) {  //has only left child
                return node.getLeft();
            }
            E min = smallestElement(node.getRight());
            node.setElement(min);
            node.setRight(remove(min, node.getRight()));
        } else if (comparator.compare(element, node.element) < 0)
            node.setLeft(remove(element, node.getLeft()));
        else
            node.setRight(remove(element, node.getRight()));

        return node;
    }

    /*
     * Returns the number of nodes in the tree.
     * @return number of nodes in the tree
     */
    public int size() {
        return size(root);
    }

    private int size(Node<E> node) {
        if (node == null)
            return 0;
        else
            return (size(node.left) + 1 + size(node.right));
    }

    /*
     * Returns the height of the tree
     * @return height
     */
    public int height() {
        return height(root);
    }

    /*
     * Returns the height of the subtree rooted at Node node.
     * @param node A valid Node within the tree
     * @return height
     */
    protected int height(Node<E> node) {
        if (node == null) return -1;
        else return Math.max(height(node.left), height(node.right)) + 1;
    }

    /**
     * Returns the smallest element within the tree.
     *
     * @return the smallest element within the tree
     */
    public E smallestElement() {
        return smallestElement(root);
    }

    protected E smallestElement(Node<E> node) {
        if (node == null) {
            return null;
        }

        while (node.left != null) {
            node = node.left;
        }
        return node.getElement();
    }

    /**
     * Returns the Node containing a specific Element, or null otherwise.
     *
     * @param element the element to find
     * @return the Node that contains the Element, or null otherwise
     * <p>
     * This method despite not being essential is very useful.
     * It is written here in order to be used by this class and its
     * subclasses avoiding recoding.
     * So its access level is protected
     */
    protected Node<E> find(Node<E> node, E element) {
        if (node == null) return null;
        if (comparator.compare(node.element, element) == 0) {
            return node;
        } else if (comparator.compare(node.element, element) > 0) {
            return find(node.left, element);
        } else {
            return find(node.right, element);
        }
    }

    protected Node<E> findMax(Node<E> node, E element) {
        if (node == null) return null;
        if (comparator.compare(node.element, element) == 0) return node;
        else if (comparator.compare(node.element, element) > 0) {
            if (findMax(node.left, element) == null) return node;
            else return null;
        } else return findMax(node.right, element);
    }

    protected Node<E> findMin(Node<E> node, E element) {
        if (node == null) return null;
        if (comparator.compare(node.element, element) == 0) return node;
        else if (comparator.compare(node.element, element) > 0) {
            if (findMin(node.left, element) == null) return node;
            else return null;
        } else return findMin(node.right, element);
    }

    /*
     * Returns an iterable collection of elements of the tree, reported in in-order.
     * @return iterable collection of the tree's elements reported in in-order
     */
    public Iterable<E> inOrder() {
        List<E> snapshot = new ArrayList<>();
        if (root != null)
            inOrderSubtree(null, snapshot, root);   // fill the snapshot recursively
        return snapshot;
    }

    /**
     * Adds elements of the subtree rooted at Node node to the given
     * snapshot using an in-order traversal
     *
     * @param nodeMax  Node serving as the root of a subtree
     * @param snapshot a list to which results are appended
     */
    protected void inOrderSubtree(Node<E> nodeMax, List<E> snapshot, Node<E> nodeMin) {
        if (nodeMin == nodeMax || nodeMin == null)
            return;
        inOrderSubtree(nodeMax, snapshot, nodeMin.right);
        snapshot.add(nodeMin.element);
        inOrderSubtree(nodeMax, snapshot, nodeMin.left);
    }

    /**
     * Returns an iterable collection of elements of the tree, reported in pre-order.
     *
     * @return iterable collection of the tree's elements reported in pre-order
     */
    public Iterable<E> preOrder() {
        List<E> snapshot = new ArrayList<>();
        preOrderSubtree(root, snapshot);
        return snapshot;
    }

    /**
     * Adds elements of the subtree rooted at Node node to the given
     * snapshot using an pre-order traversal
     *
     * @param node     Node serving as the root of a subtree
     * @param snapshot a list to which results are appended
     */
    private void preOrderSubtree(Node<E> node, List<E> snapshot) {
        if (node == null) return;
        snapshot.add(node.element);
        preOrderSubtree(node.left, snapshot);
        preOrderSubtree(node.right, snapshot);
    }

    /**
     * Returns an iterable collection of elements of the tree, reported in post-order.
     *
     * @return iterable collection of the tree's elements reported in post-order
     */
    public Iterable<E> posOrder() {
        List<E> snapshot = new ArrayList<>();
        posOrderSubtree(root, snapshot);
        return snapshot;
    }

    /**
     * Adds positions of the subtree rooted at Node node to the given
     * snapshot using an post-order traversal
     *
     * @param node     Node serving as the root of a subtree
     * @param snapshot a list to which results are appended
     */
    private void posOrderSubtree(Node<E> node, List<E> snapshot) {
        if (node == null) return;
        posOrderSubtree(node.left, snapshot);
        posOrderSubtree(node.right, snapshot);
        snapshot.add(node.element);
    }

    /*
     * Returns a map with a list of nodes by each tree level.
     * Time complexity of O(n^2) in worst case.
     * @return a map with a list of nodes by each tree level
     */
    public Map<Integer, List<E>> nodesByLevel() {
        Map<Integer, List<E>> result = new HashMap<>();
        processBstByLevel(root, result, 0);
        return result;
    }

    private void processBstByLevel(Node<E> node, Map<Integer, List<E>> result, int level) {
        if (node == null) return;

        List<E> nodeList = result.get(level);
        if (nodeList == null) nodeList = new ArrayList<>();

        nodeList.add(node.element);
        result.put(level, nodeList);
        processBstByLevel(node.left, result, level + 1);
        processBstByLevel(node.right, result, level + 1);
    }


//#########################################################################

    /**
     * Returns a string representation of the tree.
     * Draw the tree horizontally
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        toStringRec(root, 0, sb);
        return sb.toString();
    }

    private void toStringRec(Node<E> root, int level, StringBuilder sb) {
        if (root == null)
            return;
        toStringRec(root.getRight(), level + 1, sb);
        if (level != 0) {
            for (int i = 0; i < level - 1; i++)
                sb.append("|\t");
            sb.append("|-------" + root.getElement() + "\n");
        } else
            sb.append(root.getElement() + "\n");
        toStringRec(root.getLeft(), level + 1, sb);
    }

} //----------- end of BinaryTree class -----------

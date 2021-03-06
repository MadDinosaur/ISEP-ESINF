import java.util.*;

/**
 * Classe que modela uma árvore binária de pesquisa (Binary Search Tree), adaptada do material das PLs da cadeira de ESINF.
 *
 * @param <E>: Tipo de dados associado à árvore.
 */
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
    /**
     * Raíz da árvore.
     */
    protected Node<E> root = null;     // root of the tree
    /**
     * Comparador que define o modo de ordenação dos Nodes.
     */
    protected Comparator<E> comparator;

    //------------------------------- Construtores ---------------------------------
    /* Constructs an empty binary search tree. */
    public BinaryTree(Comparator<E> compare) {
        root = null;

        this.comparator = compare;
    }

    //------------------------------- Getters e Setters ---------------------------------
    /*
     * @return root Node of the tree (or null if tree is empty)
     */
    protected Node<E> root() {
        return root;
    }

    //------------------------------- Métodos Pré-Definidos (PL6) ---------------------------------
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

    /*
     * Returns an iterable collection of elements of the tree, reported in in-order.
     * @return iterable collection of the tree's elements reported in in-order
     */
    public List<E> inOrder() {
        List<E> snapshot = new ArrayList<>();
        if (root != null)
            inOrderSubtree(root, snapshot);   // fill the snapshot recursively
        return snapshot;
    }

    /**
     * Adds elements of the subtree rooted at Node node to the given
     * snapshot using an in-order traversal
     *
     * @param node     Node serving as the root of a subtree
     * @param snapshot a list to which results are appended
     */
    protected void inOrderSubtree(Node<E> node, List<E> snapshot) {
        if (node == null)
            return;

        inOrderSubtree(node.left, snapshot);
        snapshot.add(node.element);
        inOrderSubtree(node.right, snapshot);
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

    private int subTreeHeight(Node<E> node, List<Map.Entry<E, Integer>> auxList) {
        if (node == null) {
            return 0;
        } else if (node.left == null && node.right == null) {
            auxList.add(new AbstractMap.SimpleEntry<>(node.element, 0));
        }

        int leftHeight = height(node.left);
        int rightHeight = height(node.right);

        int leftDiameter = subTreeHeight(node.left, auxList);
        int rightDiameter = subTreeHeight(node.right, auxList);
        int nodeDiameter = Math.max(leftDiameter, rightDiameter) + 1;

        int maxDistance = Math.max(leftHeight + rightHeight + 1, nodeDiameter);
        auxList.get(auxList.size() - 1).setValue(maxDistance);

        return maxDistance;
    }

    //------------------------------- Métodos Gerados ---------------------------------

    /**
     * Método recursivo que procura e adiciona a @param snapshot todos os nodes que se encontrem no intervalo
     * [nodeMin, nodeMax].
     *
     * @param node
     * @param nodeMin
     * @param nodeMax
     * @param snapshot
     */
    protected void findInterval(Node<E> node, E nodeMin, E nodeMax, List<E> snapshot) {
        if (node == null)
            return;

        findInterval(node.left, nodeMin, nodeMax, snapshot);
        if (comparator.compare(node.element, nodeMin) >= 0 && comparator.compare(node.element, nodeMax) <= 0)
            snapshot.add(node.element);
        if (comparator.compare(node.element, nodeMax) > 0)
            return;
        findInterval(node.right, nodeMin, nodeMax, snapshot);
    }

    /**
     * Método recursivo que procura e retorna o node mais próximo do valor @param element, mas que seja inferior ao mesmo.
     * Retorna null caso não exista nenhum elemento menor que @param element
     *
     * @param node
     * @param element
     * @return
     */
    protected Node<E> findMax(Node<E> node, E element) {
        if (node == null) return null;
        Node<E> maxNode;

        if (comparator.compare(node.element, element) > 0) {
            maxNode = findMax(node.left, element);
            if (maxNode == null) return comparator.compare(node.element, element) > 0 ? null : node;
        } else {
            maxNode = findMax(node.right, element);
            if (maxNode == null) return comparator.compare(node.element, element) > 0 ? null : node;
        }
        return comparator.compare(maxNode.element, element) > 0 ? null : maxNode;
    }

    /**
     * Método recursivo que procura e retorna o node mais próximo do valor @param element, mas que seja superior ao mesmo.
     * Retorna null caso não exista nenhum elemento maior que @param element
     *
     * @param node
     * @param element
     * @return
     */
    protected Node<E> findMin(Node<E> node, E element) {
        if (node == null) return null;
        Node<E> minNode;

        if (comparator.compare(node.element, element) > 0) {
            minNode = findMin(node.left, element);
            if (minNode == null) return comparator.compare(node.element, element) < 0 ? null : node;
        } else {
            minNode = findMin(node.right, element);
            if (minNode == null) return comparator.compare(node.element, element) < 0 ? null : node;
        }
        return comparator.compare(minNode.element, element) < 0 ? null : minNode;
    }

    /**
     * Método que retorna a distância entre nodes - o número de ramos que distam um do outro - bem como os
     * respetivos nodes, na lista @param endNodes.
     *
     * @param endNodes
     * @return
     */
    public int maxDistance(List<E> endNodes) {
        List<Map.Entry<E, Integer>> potentialEndNodes = new ArrayList<>();
        subTreeHeight(root, potentialEndNodes);

        if (potentialEndNodes.isEmpty()) return 0;

        Map.Entry<E, Integer> min = new AbstractMap.SimpleEntry<>(null, height());
        Map.Entry<E, Integer> max = new AbstractMap.SimpleEntry<>(null, height());

        int maxValue = max.getValue();

        Map<Integer, List<E>> levels = nodesByLevel();
        boolean heightDiff = levels.get(height()).size() == 1;
        for (Map.Entry<E, Integer> node : potentialEndNodes) {
            if (node.getValue() < min.getValue()) {
                max = min;
                min = node;
            } else if (node.getValue() <= max.getValue() && node.getValue() != min.getValue()) {
                max = node;
            }

            if (node.getValue() > maxValue) maxValue = node.getValue();
        }

        endNodes.add(min.getKey());
        endNodes.add(max.getKey());
        return min.getValue() + maxValue;
    }

    /**
     * Gera uma árvore completa com os elementos presentes na lista @param nodeList.
     *
     * @param nodeList
     */
    public void completeTree(List<E> nodeList) {
        root = completeTree(nodeList, root, 0);
    }

    // Function to insert nodes in level order
    private Node<E> completeTree(List<E> nodeList, Node<E> node, int i) {
        // Base case for recursion
        if (i < nodeList.size()) {
            node = new Node<E>(nodeList.get(i), null, null);

            // insert left child
            node.setLeft(completeTree(nodeList, node.left, 2 * i + 1));

            // insert right child
            node.setRight(completeTree(nodeList, node.right, 2 * i + 2));
        }
        return node;
    }

    /*public void completeTree(BinaryTree<E> insertionList) {
        int i = 0;
        int limit = height();
        Map<Integer, List<E>> levels = nodesByLevel();

        List<E> level;
        List<E> prevLevel = levels.get(i);
        i++;

        while (levels.containsKey(i)) {
            level = levels.get(i);

            int missingNodes = (int) (Math.pow(2, i) - level.size());
            if (missingNodes == 0) {
                prevLevel = level;
                i++;
                continue;
            } else {
                for (E element : prevLevel) {
                    Node<E> node = find(root, element);
                    if (i == limit &&
                            ((node.right != null && node.right.element.equals(level.get(level.size() - 1))) ||
                                    (node.left != null && node.left.element.equals(level.get(level.size() - 1)))))
                        break;

                    if (node.left == null) {
                        Node<E> insertLeft = insertionList.findMax(insertionList.root, element);
                        try {
                            node.setLeft(insertLeft);
                            insertionList.remove(insertLeft.element);

                        //Update level
                        levels = nodesByLevel();
                        level = levels.get(i);
                        } catch (NullPointerException e) {System.out.println("Not inserted");}
                    }

                    if (node.right == null)  {
                        Node<E> insertRight = insertionList.findMin(insertionList.root, element);
                        try {
                            node.setRight(insertRight);
                            insertionList.remove(insertRight.element);

                            //Update level
                            levels = nodesByLevel();
                            level = levels.get(i);
                        } catch (NullPointerException e) {System.out.println("Não inserido");}
                    }
                }
            }
            prevLevel = level;
            i++;
        }
    }*/

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

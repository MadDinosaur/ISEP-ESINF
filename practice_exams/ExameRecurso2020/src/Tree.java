import java.util.Collections;
import java.util.Set;

public class Tree<E extends Comparable<E>> extends BST<E> {
    public E findLCA(Set<E> elements) {
        Node<E> node = root;
        while (node != null) {
            if (node.getElement().compareTo(Collections.max(elements)) > 0) node = node.getLeft();
            else if (node.getElement().compareTo(Collections.min(elements)) < 0) node = node.getRight();
            else return node.getElement();
        }
        return null;
    }
}

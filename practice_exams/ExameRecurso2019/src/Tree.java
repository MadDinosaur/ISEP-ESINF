import isep.BST;

import java.util.LinkedList;
import java.util.List;

public class Tree<E extends Comparable<E>> extends BST<E> {
    public List<E> inverseByLevel() {
        LinkedList<E> result = new LinkedList<>();
        inverseByLevel(root, result, new LinkedList<>());
        return result;
    }

    private void inverseByLevel(Node<E> node, LinkedList<E> visited, LinkedList<Node<E>> toVisit) {
        if (visited.size() == size()) return;

        if (node == null) {
            inverseByLevel(toVisit.pop(), visited, toVisit);
            return;
        }

        //Add to first position
        visited.push(node.getElement());
        //Add to last position - next up nodes to visit
        toVisit.add(node.getRight());
        toVisit.add(node.getLeft());

        //Move to next node
        inverseByLevel(toVisit.pop(), visited, toVisit);

    }
}


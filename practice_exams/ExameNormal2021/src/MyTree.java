import isep.BST;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MyTree extends BST<Integer> {
    public boolean isSymmetric() {
        if (size() % 2 == 0) return false;

        Map<Integer, List<Integer>> levels = nodesByLevel();
        for (int level = 1; level < levels.size(); level++) {
            if (!checkLevel(levels.get(level))) return false;
        }

        return true;
    }

    private boolean checkLevel(List<Integer> level) {
        if (level.isEmpty()) return true;

        if (level.size() % 2 != 0) return false;

        try {
            for (int i = 0; i < level.size()/2; i++) {
                if (level.get(i) + level.get(level.size() - (i + 1)) != 0) return false;
            }
        } catch (IndexOutOfBoundsException e) {
            return false;
        }

        return true;
    }
}

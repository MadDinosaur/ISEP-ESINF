import isep.Entry;
import isep.HeapPriorityQueue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyHeapPriorityQueue<K extends Comparable, V> extends HeapPriorityQueue<K, V>{
    public MyHeapPriorityQueue(K[] keys, V[] values) {
        super(keys, values);
    }

    public List<Entry<K,V>> getCommonPathElements(int idx1, int idx2) {
        List<Entry<K,V>> common = new ArrayList<>();
        Set<Integer> path1 = new HashSet<>();
        Set<Integer> path2 = new HashSet<>();

        while (idx1 > 0) {
            idx1 = getParent(idx1);
            path1.add(idx1);
        }

        while (idx2 > 0) {
            idx2 = getParent(idx2);
            path2.add(idx2);
        }

        path1.retainAll(path2);

        for (Integer idx : path1) {
            common.add(heap.get(idx));
        }

        return common;
    }

    private int getParent(int idx) {
        return (idx - ((idx + 1) % 2 + 1)) / 2;
    }
}


import isep.Entry;
import isep.HeapPriorityQueue;
import javafx.util.Pair;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Exercício 1:");
        Pais p1 = new Pais("p1");
        Pais p2 = new Pais("p2");
        Pais p3 = new Pais("p3");
        Pais p4 = new Pais("p4");
        Pais p5 = new Pais("p5");
        Pais p6 = new Pais("p6");
        Pair<Pais, Pais> pp1 = new Pair<>(p1, p2);
        Pair<Pais, Pais> pp2 = new Pair<>(p2, p3);
        Pair<Pais, Pais> pp3 = new Pair<>(p3, p4);
        Pair<Pais, Pais> pp4 = new Pair<>(p5, p3);
        Pair<Pais, Pais> pp5 = new Pair<>(p5, p6);
        List<Pair<Pais, Pais>> list = new ArrayList<>();
        list.add(pp1);
        list.add(pp2);
        list.add(pp3);
        list.add(pp4);
        list.add(pp5);
        Map<Integer, ArrayList<Pais>> map = numBorders(list);
        for (Map.Entry<Integer, ArrayList<Pais>> entry : map.entrySet()) {
            System.out.printf("%d -> %s\n", entry.getKey(), Arrays.toString(entry.getValue().toArray()));
        }

        System.out.println("Exercício 3:");
        MyTree tree = new MyTree();
        tree.insert(0);
        tree.insert(-1);
        tree.insert(1);
        tree.insert(-10);
        tree.insert(10);
        tree.insert(-20);
        tree.insert(-5);
        tree.insert(5);
        tree.insert(20);
        tree.insert(-7);
        tree.insert(7);
        System.out.println(tree.isSymmetric());

        System.out.println("Exercício 5:");
        String[] s = {"A", "B", "B", "C", "D", "E", "D", "E", "F"};
        MyHeapPriorityQueue<String, String> heap = new MyHeapPriorityQueue<>(s, s);
        for (Entry<String, String> entry :heap.getCommonPathElements(7, 8)) {
            System.out.printf("%s ", entry.getKey());
        }



    }

    public static Map<Integer, ArrayList<Pais>> numBorders(List<Pair<Pais, Pais>> lf) {
        Map<Integer, ArrayList<Pais>> numBorders = new TreeMap<>(Comparator.reverseOrder());
        Map<Pais, Integer> numBordersAux = new HashMap<>();

        for (Pair<Pais, Pais> border : lf) {
            Pais[] p = {border.getKey(), border.getValue()};

            for (Pais p1 : p) {
                if (!numBordersAux.containsKey(p1)) numBordersAux.put(p1, 0);

                int p1Borders = numBordersAux.get(p1);
                numBordersAux.put(p1, p1Borders + 1);
            }
        }

        for (Map.Entry<Pais, Integer> entry : numBordersAux.entrySet()) {
            Pais p = entry.getKey();
            int borders = entry.getValue();

            if (!numBorders.containsKey(borders)) numBorders.put(borders, new ArrayList<>());

            numBorders.get(borders).add(p);
        }

        return numBorders;
    }
}

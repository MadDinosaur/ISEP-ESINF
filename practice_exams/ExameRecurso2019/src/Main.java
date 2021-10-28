import isep.Graph;
import isep.HeapPriorityQueue;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Double[] pesos = {4.0, 8.0, 2.0, 1.0, 7.0, 6.0, 1.0, 4.0, 5.0, 2.0};
        Map<Integer, LinkedList<Double>> paletes = new HashMap<>();
        System.out.println("Exercício 1 :");
        System.out.printf("Taxa média de ocupação = %.2f\n", packing(10, Arrays.asList(pesos), paletes));
        for (Map.Entry<Integer, LinkedList<Double>> palete : paletes.entrySet()) {
            System.out.printf("Palete " + palete.getKey() + " --> " + Arrays.toString(palete.getValue().toArray()) + "\n");
        }

        System.out.printf("\nExercício 2 :\n");
        System.out.println("(Hello!, Hello) --> " + mistery("Hello!", "Hello"));
        System.out.println("(Hello, World) --> " + mistery("Hello", "World"));
        System.out.println("(Oh, Hello!, Hello) --> " + mistery("Oh, Hello!", "Hello"));

        System.out.printf("\nExercício 3 : \n");
        Tree<Integer> tree = new Tree<>();
        tree.insert(60);
        tree.insert(32);
        tree.insert(80);
        tree.insert(19);
        tree.insert(34);
        tree.insert(89);
        tree.insert(1);
        System.out.println(Arrays.toString(tree.inverseByLevel().toArray()));

        System.out.println("Exercício 4: ");
        Graph<String, Integer> graph = new Graph<>();
        graph.insertVertex("A1");
        graph.insertVertex("A2");
        graph.insertVertex("A3");
        graph.insertVertex("A4");
        graph.insertVertex("A5");
        graph.insertVertex("A6");
        graph.insertVertex("A7");
        graph.insertVertex("B1");
        graph.insertVertex("B2");
        graph.insertVertex("B3");
        graph.insertVertex("B4");
        graph.insertVertex("D1");
        graph.insertVertex("D2");
        graph.insertVertex("D3");
        graph.insertEdge("A1", "A2", 1);
        graph.insertEdge("A1", "A5", 1);
        graph.insertEdge("A2", "A4", 1);
        graph.insertEdge("A2", "A3", 1);
        graph.insertEdge("A3", "A7", 1);
        graph.insertEdge("A3", "A4", 1);
        graph.insertEdge("A4", "A5", 1);
        graph.insertEdge("A4", "A6", 1);
        graph.insertEdge("A5", "A6", 1);
        graph.insertEdge("A6", "A7", 1);
        graph.insertEdge("A7", "B1", 1);
        graph.insertEdge("A7", "B2", 1);
        graph.insertEdge("B1", "B2", 1);
        graph.insertEdge("B1", "B3", 1);
        graph.insertEdge("B1", "B4", 1);
        graph.insertEdge("B2", "B4", 1);
        graph.insertEdge("B3", "B4", 1);
        graph.insertEdge("B3", "D1", 1);
        graph.insertEdge("D1", "D2", 1);
        graph.insertEdge("D1", "D3", 1);
        graph.insertEdge("D2", "D3", 1);
        graph.insertEdge("A3", "D2", 1);

        Map<String, String> map = new HashMap<>();
        map.put("A1", "A");
        map.put("A2", "A");
        map.put("A3", "A");
        map.put("A4", "A");
        map.put("A5", "A");
        map.put("A6", "A");
        map.put("A7", "A");
        map.put("B1", "B");
        map.put("B2", "B");
        map.put("B3", "B");
        map.put("B4", "B");
        map.put("D1", "D");
        map.put("D2", "D");
        map.put("D3", "D");

        Graph newGraph = GraphSummary(graph, map);

        System.out.println("\nExercício 5: ");
        HeapPriorityQueue heap = new HeapPriorityQueue<>();
        heap.insert(1, 0);
        heap.insert(2, 0);
        heap.insert(2, 0);
        heap.insert(3, 0);
        heap.insert(4, 0);
        heap.insert(5, 0);
        heap.insert(4, 0);
        heap.insert(5, 0);
        heap.insert(6, 0);
        System.out.println(NumbElemsLastLevel(heap));
    }

    public static double packing(double capac, List<Double> pesos, Map<Integer, LinkedList<Double>> paletes) {
        Collections.sort(pesos, Comparator.reverseOrder());
        Map<Integer, Double> paletesCapac = new HashMap<>();

        paletes.put(1, new LinkedList<>());
        paletesCapac.put(1, 0.0);

        for (double peso : pesos) {
            for (int i = 1; i <= paletes.size(); i++) {
                int paleteID = i;
                double currentCapac = paletesCapac.get(paleteID);

                if (capac >= currentCapac + peso) {
                    paletes.get(paleteID).add(peso);
                    paletesCapac.put(paleteID, currentCapac + peso);
                    break;
                } else if (!paletes.containsKey(paleteID + 1)) {
                    paletes.put(paleteID + 1, new LinkedList<>());
                    paletesCapac.put(paleteID + 1, 0.0);
                }
            }
        }

        double sum = 0;
        for (double occupiedCapac : paletesCapac.values()) {
            sum += occupiedCapac;
        }
        return sum / capac / paletes.size();
    }

    public static int mistery(String tt, String pp) {

        for (int i = 0; i <= tt.length() - pp.length(); i++) {
            int j = 0;
            while (j < pp.length() && tt.charAt(i + j) == pp.charAt(j)) {
                j++;
            }
            if (j == pp.length())
                return i;
        }
        return -1;
    }

    public static <V, E> Graph<V, Integer> GraphSummary(Graph<V, E> g, Map<V, V> groupverts) {
        Graph<V, Integer> summary = new Graph<>();
        LinkedList<V> visited = new LinkedList<>();

        Iterator<V> i = g.vertices().iterator();
        V prevVertex = null;
        while (i.hasNext()) {
            V vertex = i.next();
            V group = groupverts.get(vertex);
            //Assuming graph doesn't allow duplicate vertices
            summary.insertVertex(group);
            for (E edge : g.outgoingEdges(vertex)) {
                List<V> endVertices = g.endVertices(edge);
                endVertices.remove(vertex);

                V adjVertex = endVertices.get(0);
                V adjGroup = groupverts.get(adjVertex);

                Integer adjEdge = summary.getEdge(group, adjGroup);
                if (!visited.contains(adjVertex))
                    summary.insertEdge(group, adjGroup, adjEdge == null ? 1 : adjEdge + 1);
            }
            visited.add(vertex);
        }
        return summary;
    }

    public static <K, V extends Comparable<V>> int NumbElemsLastLevel(HeapPriorityQueue<K, V> heap) {
        //O nº de elementos de cada nível de uma heap são expressos em potências de 2
        //Nível 1 -> 2^0 = 1
        //Nível 2 -> 2^1 = 2
        //etc...
        int lastLevel = (int) (Math.log(heap.size()) / Math.log(2)) + 1; //log2(nº elem) + 1
        //ou
        //Integer.toBinaryString(heap.size()).length

        int lastLevelIndex = (int) Math.pow(2, lastLevel - 1) - 1; //Índice onde começa o último nível

        return heap.size() - lastLevelIndex; //Nº de elementos até ao fim do array
    }
}


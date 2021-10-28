import java.util.LinkedList;

public class Ferrovia {
    private Graph<String, Double> g = new Graph<>(true);

    public LinkedList<String> alternativePath(String stOrig, String stDest, String stManut) {
        Iterable<Edge<String, Double>> manutEdges = g.outgoingEdges(stManut);
        g.removeVertex(stManut);

        LinkedList<String> alternativePath = shortestPath(g, stOrig, stDest, new LinkedList<String>());

        g.insertVertex(stManut);
        for (Edge<String, Double> edge : manutEdges) {
            g.insertEdge(edge.getVOrig(), edge.getVDest(), edge.getValue, edge.getValue);
        }

        return alternativePath;
    }

    private LinkedList<String> shortestPath(Graph<String, Double> g, String vOrig, String vDest, LinkedList<String> shortPath) {
        LinkedList<String> visited = new LinkedList<>();


    }
}

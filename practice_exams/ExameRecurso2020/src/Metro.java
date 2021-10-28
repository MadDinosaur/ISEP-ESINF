import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Metro {
    private Graph<String,String> g = new Graph<String, String>();

    public Metro() {
        g.insertVertex("Dragão");
        g.insertVertex("Contumil");
        g.insertVertex("Campanhã");
        g.insertVertex("Trindade");
        g.insertVertex("Faria Guimarães");

        g.insertEdge("Dragão", "Contumil", "Laranja1");

        g.insertEdge("Dragão", "Campanhã", "Azul1");

        g.insertEdge("Trindade", "Campanhã", "Azul2");

        g.insertEdge("Trindade", "Faria Guimarães", "Amarelo1");
    }

    public LinkedList<String> pathSameLine(String stOrig, String stDest, String line) {

        return pathSameLine(stOrig, stDest, line, new int[g.numVertices], new LinkedList<>());
    }

    private LinkedList<String> pathSameLine(String stOrig, String stDest, String line, int[] visited, LinkedList<String> path) {
        path.add(stOrig);
        visited[g.toIndex(stOrig)] = 1;

        if(stOrig.equals(stDest)) return path;

        for (String edge : g.outgoingEdges(stOrig)) {
            if(edge.contains(line)) {
                List<String> endVertices = g.endVertices(edge);
                endVertices.remove(stOrig);

                stOrig = endVertices.get(0);
                if (visited[g.toIndex(stOrig)] != 1) pathSameLine(stOrig, stDest, line, visited, path);
            }
        }

        return path;
    }
}
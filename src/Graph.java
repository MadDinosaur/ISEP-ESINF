import javafx.util.Pair;

import java.util.*;

/**
 * NOTA: Gráfico não direcionado
 *
 * @param <V>
 * @param <E>
 */
public class Graph<V, E> {

    /**
     * Mapa de vértices com o respetivo índice na matriz de adjacências.
     *
     * @param V : Classe do vértice.
     * @param Integer : Índice do vértice na matriz de adjacências.
     * O(1) para obter o índice a partir de um vértice.
     */
    private Map<V, Integer> vertices;
    /**
     * Mapa de arestas com o respetivo índice na matriz de adjacências.
     *
     * @param E : Classe da aresta.
     * @param Pair<Integer, Integer> : Par de coordenadas da aresta na matriz de adjacências.
     * O(1) para obter os coordenadas a partir de uma aresta.
     */
    private Map<E, Pair<Integer, Integer>> edges;
    /**
     * Lista para obter vértice a partir do índice em O(1).
     */
    private List<V> verticesLookup;
    /**
     * Lista para obter aresta a partir das coordenadas em O(1).
     * Outer list : índices das linhas
     * Inner list : índices das colunas
     */
    private ArrayList<ArrayList<E>> edgesLookup;
    /**
     * Matriz de adjacências
     */
    private List<List<Integer>> adj;

    public Graph() {
        adj = new ArrayList<>();
        vertices = new HashMap<>();
        edges = new HashMap<>();
        verticesLookup = new ArrayList<>();
        edgesLookup = new ArrayList<ArrayList<E>>();
    }

    // Returns the number of vertices of the graph
    public int numVertices() {
        return vertices.size();
    }

    // Returns all the vertices of the graph as an iterable collection
    public Set<V> vertices() {
        return vertices.keySet();
    }

    //Returns a vertice, gets a reference as parameter
    public V getVertex(V v_copy) {
        int origIndex = vertices.get(v_copy);
        return verticesLookup.get(origIndex);
    }

    // Returns the number of edges of the graph
    public int numEdges() {
        return edges.size();
    }

    // Returns the information of all the edges of the graph as an iterable collection
    public Set<E> edges() {
        return (HashSet) edges.keySet();
    }

    /* Returns the edge from vorig to vdest, or null if vertices are not adjacent
     * @param vorig
     * @param vdest
     * @return the edge or null if vertices are not adjacent or don't exist
     */
    public E getEdge(V vOrig, V vDest) {
        int vOrigIndex = vertices.get(vOrig);
        int vDestIndex = vertices.get(vDest);

        try {
            return edgesLookup.get(vOrigIndex).get(vDestIndex);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    /* Returns the vertices of edge e as an array of length two
     * If the graph is directed, the first vertex is the origin, and
     * the second is the destination.  If the graph is undirected, the
     * order is arbitrary.
     * @param e
     * @return array of two vertices or null if edge doesn't exist
     */
    public V[] endVertices(E edge) {
        Pair<Integer, Integer> coordenates = edges.get(edge);
        V vOrig = verticesLookup.get(coordenates.getKey());
        V vDest = verticesLookup.get(coordenates.getValue());

        //Adicionar a array
        List endVertices = new ArrayList();
        endVertices.add(vOrig);
        endVertices.add(vDest);

        return (V[]) endVertices.toArray();
    }

    /* Returns the vertex that is opposite vertex v on edge e.
     * @param v
     * @param e
     * @return opposite vertex, or null if vertex or edge don't exist
     */
    public V opposite(V vert, E edge) {
        int vertIndex = vertices.get(vert);
        int oppositeIndex = edges.get(edge).getKey() == vertIndex ? edges.get(edge).getValue() : edges.get(edge).getKey();
        return verticesLookup.get(oppositeIndex);
    }

    /**
     * Returns the number of edges leaving vertex v
     * Since this is an undirected graph, this result is the same as the number of edges entering vertex v (indegree)
     *
     * @param vert
     * @return number of edges leaving vertex v, -1 if vertex doesn't exist
     */
    public int outDegree(V vert) {
        int vertIndex = vertices.get(vert);
        int sum = 0;
        for (int cell : adj.get(vertIndex)) {
            sum += cell;
        }
        return sum;
    }

    /* Returns an iterable collection of edges for which vertex v is the origin
     * Since this is an undirected graph, this result is the same as the edges for which vertex v is the destination (incomingEdges)
     * @param v
     * @return iterable collection of edges, null if vertex doesn't exist
     */
    public Iterable<E> outgoingEdges(V vert) {
        int vertIndex = vertices.get(vert);
        return edgesLookup.get(vertIndex);
    }

    /* Inserts a new vertex with some specific comparable type
     * @param element the vertex contents
     * @return a true if insertion suceeds, false otherwise
     */
    public boolean insertVertex(V newVert) {
        if (!vertices.containsKey(newVert)) {
            vertices.put(newVert, verticesLookup.size());
            verticesLookup.add(newVert);

            //Adicionar uma linha e coluna à matriz de adjacências
            ArrayList<Integer> emptyLine = new ArrayList<>(Collections.nCopies(adj.size() + 1, 0));
            for (List<Integer> lines : adj) {
                lines.add(0);
            }
            adj.add(emptyLine);
            ArrayList<E> emptyEdgeLine = new ArrayList<E>(Collections.nCopies(edgesLookup.size() + 1, null));
            for (List<E> lines : edgesLookup) {
                lines.add(null);
            }
            edgesLookup.add(emptyEdgeLine);

            return true;
        }
        return false;
    }

    /* Adds a new edge between vertices u and v, with some
     * specific comparable type. If vertices u, v don't exist in the graph they
     * are inserted
     * @param vorigInf Information of vertex source
     * @param vdestInf Information of vertex destination
     * @param eInf edge information
     * @param eWeight edge weight
     * @return true if suceeds, or false if an edge already exists between the two verts.
     */
    public boolean insertEdge(V vOrig, V vDest, E edge) {
        if (!validVertex(vOrig))
            insertVertex(vOrig);

        if (!validVertex(vDest))
            insertVertex(vDest);

        if (getEdge(vOrig, vDest) != null)
            return false;

        int fromIndex = vertices.get(vOrig);
        int toIndex = vertices.get(vDest);
        //Adicionar à matriz de adjacências
        adj.get(fromIndex).set(toIndex, 1);
        adj.get(toIndex).set(fromIndex, 1);
        //Adicionar ao mapa @edges e à matriz @edgesLookup
        edges.put(edge, new Pair<>(fromIndex, toIndex));
        edgesLookup.get(fromIndex).set(toIndex, edge);
        edgesLookup.get(toIndex).set(fromIndex, edge);
        return true;
    }

    /* Removes a vertex and all its incident edges from the graph
     * @param vInf Information of vertex source
     */
    public boolean removeVertex(V vert) {
        if (!validVertex(vert))
            return false;

        //Remove all edges that point to vert
        for (E edge : outgoingEdges(vert)) {
            V vAdj = opposite(vert, edge);
            removeEdge(vAdj, vert);
        }

        int vertIndex = vertices.get(vert);
        verticesLookup.remove(vertIndex);
        vertices.remove(vert);

        //Clear line and column from adjency matrix
        adj.remove(vert);
        for (List<Integer> line : adj) {
            line.remove(vertIndex);
        }

        return true;
    }

    /* Removes the edge between two vertices
     *
     * @param vA Information of vertex source
     * @param vB Information of vertex destination
     */
    public boolean removeEdge(V vOrig, V vDest) {
        if (!validVertex(vOrig) || !validVertex(vDest))
            return false;

        E edge = getEdge(vOrig, vDest);
        if (edge == null)
            return false;

        int vorig = vertices.get(vOrig);
        int vdest = vertices.get(vDest);
        edges.remove(edge);
        edgesLookup.get(vorig).remove(vdest);

        //Eliminar da matriz de adjacências
        adj.get(vorig).set(vdest, 0);
        return true;
    }

    public List<V> getAdjacentVertices(V v) {
        int index = vertices.get(v);
        List<V> result = new ArrayList<>();
        for (int i = 0; i < adj.get(index).size(); i++) {
            if (adj.get(index).get(i) == 1) {
                result.add(verticesLookup.get(i));
            }
        }
        return result;
    }

    public boolean validVertex(V vert) {

        if (vertices.get(vert) == null)
            return false;

        return true;
    }

    //implementar BFS

    public LinkedList<V> BreadthFirstSearch(V vert) {
        if (!validVertex(vert)) {
            return null;
        }

        LinkedList<V> qbfs = new LinkedList<>();
        LinkedList<V> qaux = new LinkedList<>();

        boolean[] visited = new boolean[numVertices()];

        qbfs.add(vert);
        qaux.add(vert);

        int vKey = vertices.get(vert);
        visited[vKey] = true;

        while (!qaux.isEmpty()) {
            vert = qaux.remove();

            for (V vAdj : getAdjacentVertices(vert)) {
                vKey = vertices.get(vAdj);
                if (!visited[vKey]) {
                    qbfs.add(vAdj);
                    qaux.add(vAdj);
                    visited[vKey] = true;
                }
            }
        }

        return qbfs;
    }

    //Algortimo de Dijkstra que retorna o nº mínimo de arestas entre 2 vértices em O(n)
    public int shortestPath(V vOrig, V vDest) {
        if (!validVertex(vOrig) || !validVertex(vDest)) {
            return 0;
        }
        int fromIndex = vertices.get(vOrig);
        int toIndex = vertices.get(vDest);
        return (int) shortestDistance(fromIndex, toIndex, false);
    }

    //Algortimo de Dijkstra que retorna o caminho (vértices a percorrer) mais curto entre 2 vértices em O(n)
    private List<V> shortestPath(int vOrig, int vDest, boolean isWeighted) {
        boolean[] visited = new boolean[numVertices()];
        List<V> previous = new ArrayList<V>(numVertices());
        double[] distance = new double[numVertices()];
        distance[vOrig] = 0;
        PriorityQueue<Pair<Integer, Double>> pq = new PriorityQueue();
        pq.add(new Pair<>(vOrig, 0.0));
        while (pq.size() != 0) {
            Pair<Integer, Double> p = pq.poll();
            int index = p.getKey();
            double minValue = p.getValue();
            visited[index] = true;
            if (distance[index] < minValue) continue;
            for (int edge : adj.get(index)) {
                if (visited[edge]) continue;
                double newDist = distance[index] + (double) (isWeighted ? edgesLookup.get(index).get(edge) : 1);
                if (newDist < distance[edge]) {
                    previous.set(edge, verticesLookup.get(index));
                    distance[edge] = newDist;
                    pq.add(new Pair<>(edge, newDist));
                }
            }
            if (index == vDest) return previous;
        }
        return null;
    }

    private double shortestDistance(int vOrig, int vDest, boolean isWeighted) {
        boolean[] visited = new boolean[numVertices()];
        double[] distance = new double[numVertices()];
        distance[vOrig] = 0;
        PriorityQueue<Pair<Integer, Double>> pq = new PriorityQueue();
        pq.add(new Pair<>(vOrig, 0.0));
        while (pq.size() != 0) {
            Pair<Integer, Double> p = pq.poll();
            int index = p.getKey();
            double minValue = p.getValue();
            visited[index] = true;
            if (distance[index] < minValue) continue;
            for (int edge : adj.get(index)) {
                if (visited[edge]) continue;
                double newDist = distance[index] + (double) (isWeighted ? edgesLookup.get(index).get(edge) : 1);
                if (newDist < distance[edge]) {
                    distance[edge] = newDist;
                    pq.add(new Pair<>(edge, newDist));
                }
            }
            if (index == vDest) return distance[index];
        }
        return -1;
    }
}
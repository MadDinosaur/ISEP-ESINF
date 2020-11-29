import javafx.util.Pair;

import java.util.*;
import java.util.stream.Collectors;

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

    // Returns the number of edges of the graph
    public int numEdges() {
        return edges.size();
    }

    // Returns the information of all the edges of the graph as an iterable collection
    public Set<E> edges() {
        return edges.keySet();
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

        List<E> outEdges = (List<E>) edgesLookup.get(vertIndex).clone();
        outEdges.removeAll(Collections.singleton(null));

        return outEdges;
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
            return false;

        if (!validVertex(vDest))
            return false;

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
        if (vert == null) return false;
        if (vertices.get(vert) == null) return false;

        return true;
    }

    public boolean isConnected() {
        List<V> connectedVertices = breadthFirstSearch(verticesLookup.get(0));
        return connectedVertices.containsAll(vertices());
    }

    public int longestPath() {
        /*
         * First BFS to find an endpoint of the longest path and
         * second BFS from this endpoint to find the actual longest path.
         */
        //Gets furthest vertex from initial vertex (index 0)
        V firstSearch = breadthFirstSearch(verticesLookup.get(0)).getLast();
        //Gets furthest vertex from the vertex returned above - garantees
        V secondSearch = breadthFirstSearch(firstSearch).getLast();

        return (int) shortestDistance(vertices.get(firstSearch), vertices.get(secondSearch), false);
    }

    private LinkedList<V> breadthFirstSearch(V vert) {
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

    public LinkedList<V> searchByLayers(V vert, int n) {
        if (!validVertex(vert)) {
            return null;
        }

        LinkedList<V> qbfs = new LinkedList<>();
        LinkedList<V> qaux = new LinkedList<>();
        boolean[] visited = new boolean[numVertices()];
        int[] level = new int[numVertices()];
        qaux.add(vert);
        int vKey = vertices.get(vert);
        visited[vKey] = true;
        level[vKey] = 0;

        while (!qaux.isEmpty()) {
            vert = qaux.remove();
            for (V vAdj : getAdjacentVertices(vert)) {
                vKey = vertices.get(vAdj);
                if (!visited[vKey]) {
                    qaux.add(vAdj);
                    visited[vKey] = true;
                    level[vKey] = level[vertices.get(vert)] + 1;
                }
                if (level[vKey] <= n) {
                    qbfs.add(verticesLookup.get(vKey));
                } else {
                    return qbfs;
                }
            }
        }
        return qbfs;
    }

    //Algortimo de Dijkstra que retorna o nº mínimo de arestas entre 2 vértices em O(n)
    public double shortestDistance(int vOrig, int vDest, boolean isWeighted) {
        boolean[] visitedVertices = new boolean[numVertices()];
        double[] distance = new double[numVertices()];
        Arrays.fill(distance, -1);
        distance[vOrig] = 0;
        PriorityQueue<Pair<Integer, Double>> pq = new PriorityQueue<>(Comparator.comparing(Pair::getValue));
        pq.add(new Pair<>(vOrig, 0.0));
        while (pq.size() != 0) {
            Pair<Integer, Double> p = pq.poll();
            int vIndex = p.getKey();
            double minDistance = p.getValue();
            visitedVertices[vIndex] = true;
            for (int adjIndex = 0; adjIndex < adj.get(vIndex).size(); adjIndex++) {
                if (adj.get(vIndex).get(adjIndex) == 0 || visitedVertices[adjIndex]) continue;
                double edgeWeight;
                if (isWeighted) {
                    edgeWeight = (Double) edgesLookup.get(vIndex).get(adjIndex);
                } else {
                    edgeWeight = 1;
                }
                double newDist = minDistance + edgeWeight;
                if (distance[adjIndex] == -1 || newDist < distance[adjIndex]) {
                    distance[adjIndex] = newDist;
                    pq.add(new Pair<>(adjIndex, newDist));
                }
            }
            if (vIndex == vDest) return distance[vIndex];

        }
        return -1;
    }

    private double pathLength(List<V> path) {
        double length = 0;
        Iterator<V> i = path.listIterator();
        V vOrig = i.next();
        while (i.hasNext()) {
            V vDest = i.next();
            length += (Double) getEdge(vOrig, vDest);
            vOrig = vDest;
        }
        return length;
    }

    public Pair<List<V>, Double> getPathAcrossAllVertices(List<V> vertexList) {
        V vOrig = vertexList.remove(0);
        V vDest = vertexList.remove(vertexList.size() - 1);
        //Remover cidades duplicadas
        vertexList = vertexList.stream()
                .distinct()
                .collect(Collectors.toList());
        vertexList.remove(vOrig);
        vertexList.remove(vDest);
        //Adicionar os vértices de origem e destino novamente na lista
        vertexList.add(0, vOrig);
        vertexList.add(vDest);

        List<V> shortestPath = new ArrayList<>();
        for (List<V> path : allPaths(vOrig, vDest)) {
            if (path.containsAll(vertexList) && (shortestPath.isEmpty() || pathLength(path) < pathLength(shortestPath))) {
                shortestPath = path;
            }
        }
        return new Pair<List<V>, Double>(shortestPath, pathLength(shortestPath));
    }

    private List<List<V>> allPaths(V vOrig, V vDest) {
        List<List<V>> allPaths = new ArrayList<>();
        depthFirstSearch(vOrig, vDest, new HashSet<>(), new LinkedList<>(), allPaths);
        return allPaths;
    }

    private void depthFirstSearch(V vOrig, V vDest, Set visited, List<V> pathList, List<List<V>> allPaths) {
        if (vOrig.equals(vDest)) {
            //Criar cópia da pathList para impedir alterações
            List<V> pathListCopy = new ArrayList<>(pathList);
            pathListCopy.add(vOrig);
            //Adicionar a allPaths
            allPaths.add(pathListCopy);
            return;
        }

        if (!visited.contains(vOrig)) {
            visited.add(vOrig);
            pathList.add(vOrig);
            Iterable<V> neighbours = getAdjacentVertices(vOrig);
            Iterator<V> i = neighbours.iterator();
            while (i.hasNext()) {
                depthFirstSearch(i.next(), vDest, visited, pathList, allPaths);
            }
            pathList.remove(vOrig);
            visited.remove(vOrig);
        }
        return;
    }
}
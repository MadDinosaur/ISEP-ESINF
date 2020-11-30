import javafx.util.Pair;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Classe que modela um grafo não direcionado, com vértices e arestas.
 *
 * @param <V>: Tipo de dados dos vértices.
 * @param <E>: Tipo de dados nas arestas.
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
    //------------------------------- Construtores ---------------------------------

    /**
     * Construtor da classe Graph
     */
    public Graph() {
        adj = new ArrayList<>();
        vertices = new HashMap<>();
        edges = new HashMap<>();
        verticesLookup = new ArrayList<>();
        edgesLookup = new ArrayList<ArrayList<E>>();
    }
    //------------------------------- Métodos do grafo ---------------------------------

    /**
     * @return número de vértices do grafo.
     */
    public int numVertices() {
        return vertices.size();
    }

    /**
     * @return conjunto com todos os vértices do grafo.
     */
    public Set<V> vertices() {
        return vertices.keySet();
    }

    /**
     * @return número de arestas do grafo.
     */
    public int numEdges() {
        return edges.size();
    }

    /**
     * @return conjunto com todas as arestas do grafo.
     */
    public Set<E> edges() {
        return edges.keySet();
    }

    /**
     * Método para obter a aresta de @vOrig a @vDest.
     *
     * @param vOrig: vértice de origem.
     * @param vDest: vértice de destino.
     * @return a aresta entre @vOrig e @vDest ou null se os vértices não forem adjacentes ou não existerem.
     */
    public E getEdge(V vOrig, V vDest) {
        if (vOrig == null || vDest == null) {
            return null;
        }
        int vOrigIndex = vertices.get(vOrig);
        int vDestIndex = vertices.get(vDest);

        try {
            return edgesLookup.get(vOrigIndex).get(vDestIndex);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    /**
     * Método para obter o vértice oposto ao @vert, pela aresta @edge.
     *
     * @param vert: vértice de origem.
     * @param edge: aresta entre os vértices.
     * @return vértice oposto, ou null se o vértice ou aresta não existirem.
     */
    public V opposite(V vert, E edge) {
        if (vert == null || edge == null) {
            return null;
        }
        int vertIndex = vertices.get(vert);
        int oppositeIndex = edges.get(edge).getKey() == vertIndex ? edges.get(edge).getValue() : edges.get(edge).getKey();
        return verticesLookup.get(oppositeIndex);
    }

    /**
     * Método para obter o número de arestas que saem do vértice @vert.
     * Sendo este um grafo não direcionado, o resultado é o mesmo que o nº de arestas que entram no vértice @vert (indegree).
     *
     * @param vert
     * @return número de arestas do vértice ou -1 se o vértice não existir.
     */
    public int outDegree(V vert) {
        if (vert == null) return 0;

        int vertIndex = vertices.get(vert);
        int sum = 0;
        for (int cell : adj.get(vertIndex)) {
            sum += cell;
        }
        return sum;
    }

    /**
     * Método para obter as arestas que saem do vértice @vert.
     * Sendo este um grafo não direcionado, o resultado é o mesmo que as arestas que entram no vértice @vert (indegree).
     *
     * @param vert
     * @return lista de arestas do vértice ou null se o vértice não existir.
     */
    public Iterable<E> outgoingEdges(V vert) {
        int vertIndex = vertices.get(vert);

        List<E> outEdges = (List<E>) edgesLookup.get(vertIndex).clone();
        outEdges.removeAll(Collections.singleton(null));

        return outEdges;
    }

    /**
     * Retorna os vértices adjacentes ao vértice @v.
     * Os vértices adjacentes são todos aqueles aos quais @v está ligado por uma aresta.
     *
     * @param v: vértice de origem.
     * @return lista com os vértices adjacentes.
     */
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

    /**
     * Insere um vértice no grafo.
     *
     * @param newVert: conteúdo do novo vértice.
     * @return true se a inserção for bem sucedida, false caso contrário.
     */
    public boolean insertVertex(V newVert) {
        if (!vertices.containsKey(newVert)) {
            vertices.put(newVert, verticesLookup.size());
            verticesLookup.add(newVert);

            //Adiciona uma linha e coluna à matriz de adjacências
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

    /**
     * Insere uma aresta entre dois vértices.
     *
     * @param vOrig: vértice de origem.
     * @param vDest: vértice de destino.
     * @param edge:  conteúdo da aresta.
     * @return true se a inserção for bem sucedida, false caso contrário (se um dos vértices não existir ou se já existir uma aresta entre eles).
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

    /**
     * Elimina um vértice e todas as suas arestas incidentes do grafo.
     *
     * @param vert: vértice a remover.
     * @return true se a remoção for bem sucedida, false caso contrário.
     */
    public boolean removeVertex(V vert) {
        if (!validVertex(vert))
            return false;

        //Remove todas as arestas de @vert
        for (E edge : outgoingEdges(vert)) {
            V vAdj = opposite(vert, edge);
            removeEdge(vAdj, vert);
        }

        int vertIndex = vertices.get(vert);
        verticesLookup.remove(vertIndex);
        vertices.remove(vert);

        //Elimina a linha e coluna da matriz de adjacências correspondente ao vértice
        adj.remove(vert);
        for (List<Integer> line : adj) {
            line.remove(vertIndex);
        }

        return true;
    }

    /**
     * Elimina uma aresta entre dois vértices.
     *
     * @param vOrig: vértice de origem.
     * @param vDest: vértice de destino.
     * @return true se a remoção for bem sucedida, false caso contrário.
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

    /**
     * Verifica se um vértice existe ou é nulo.
     *
     * @param vert: vértice a verificar.
     * @return true se o vértice for válido, false caso não exista ou seja null.
     */
    public boolean validVertex(V vert) {
        if (vert == null) return false;
        if (vertices.get(vert) == null) return false;

        return true;
    }

    /**
     * Verifica se o grafo é conexo.
     *
     * @return true, se o grafo é conexo, false caso contrário.
     */
    public boolean isConnected() {
        List<V> connectedVertices = breadthFirstSearch(verticesLookup.get(0));
        return connectedVertices.containsAll(vertices());
    }
    //------------------------------- Métodos de pesquisa ---------------------------------

    /**
     * Algoritmo de Dijkstra que retorna a distância (ou o nº de arestas) entre 2 vértices em O(n).
     *
     * @param vOrig:      vértice de origem.
     * @param vDest:      vértice de destino.
     * @param isWeighted: true, caso as arestas tenham valores númericos (sejam ponderadas), false caso contrário.
     * @return a distância ou o número de arestas mínimas entre dois vértices.
     */
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

    /**
     * Percorre todos os vértices do grafo através círculos concêntricos (ou camadas).
     *
     * @param vert: vértice de ínicio.
     * @return lista com os vértices percorridos, pela ordem em que foram acedidos.
     */
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

    /**
     * Efetua uma @breadthFirstSearch, até um dado número de camada.
     *
     * @param vert: vértice de início.
     * @param n:    camada de paragem.
     * @return lista com os vértices percorridos, pela ordem em que foram acedidos.
     */
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

    /**
     * Percorre todos os vértices do grafo em profundidade, de forma recursiva, originando caminhos.
     *
     * @param vOrig:    vértice de origem.
     * @param vDest:    vértice de destino.
     * @param visited:  lista de vértices visitados.
     * @param pathList: lista de vértices de um dado caminho.
     * @param allPaths: lista de todos os caminhos possíveis.
     */
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

    /**
     * Obtém todos os caminhos possíves entre o vértice de origem e de destino.
     *
     * @param vOrig: vértice de origem.
     * @param vDest: vértice de destino.
     * @return lista de todos os caminhos entre o vértice de origem e de destino.
     */
    private List<List<V>> allPaths(V vOrig, V vDest) {
        List<List<V>> allPaths = new ArrayList<>();
        depthFirstSearch(vOrig, vDest, new HashSet<>(), new LinkedList<>(), allPaths);
        return allPaths;
    }

    /**
     * Obtém o caminho mais curto que passe em todos os vértices de @vertexList, com origem no primeiro índice da lista e destino no último.
     * Apenas são considerados caminhos em que os vértices origem, destino e intermédios são todos distintos.
     *
     * @param vertexList: lista de vértices a percorrer.
     * @return: Pair, com a key: lista de vértices no caminho mais curto, e com o value: distância total do caminho.
     */
    public Pair<List<V>, Double> getPathAcrossAllVertices(List<V> vertexList) {
        if (vertexList.size() < 2) return new Pair<List<V>, Double>(new ArrayList<>(), 0.0);

        V vOrig = vertexList.remove(0);
        V vDest = vertexList.remove(vertexList.size() - 1);

        if (vOrig.equals(vDest)) return new Pair<List<V>, Double>(new ArrayList<>(), 0.0);

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

    /**
     * Obtém o caminho mais curto entre os vértices mais afastados do grafo.
     * O primeiro BFS encontra o vértice limite do caminho mais longo a partir do vértice inicial (índice 0).
     * O segundo BFS encontra o vértice limite do caminho mais longo a partir da primeira busca para encontrar o verdadeiro caminho mais longo.
     *
     * @return o número de arestas do caminho mais comprido do grafo.
     */
    public int longestPath() {
        //firstSearch -> vértice mais distante do vértice inicial
        V firstSearch = breadthFirstSearch(verticesLookup.get(0)).getLast();
        //secondSearch -> vértice mais distante do vértice obtido acima
        V secondSearch = breadthFirstSearch(firstSearch).getLast();

        return (int) shortestDistance(vertices.get(firstSearch), vertices.get(secondSearch), false);
    }

    /**
     * Calcula a distância de um dado caminho.
     * Apenas se aplica a grafos com valores ponderados nas arestas.
     *
     * @param path: caminho de vértices.
     * @return distância total de @path.
     */
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
}
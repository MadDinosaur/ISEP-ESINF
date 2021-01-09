import javafx.util.Pair;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Classe que modela uma Tabela Periódica, recorrendo ao uso de uma árvore binária de pesquisa (AVL).
 */
public class PeriodicTable extends BalancedTree<ChemicalElement> {
    /**
     * Estados dos elementos.
     * Key -> Nome do estado.
     * Value -> Índice na coluna de apresentação de resultados.
     */
    final static Map<String, Integer> PHASES = new HashMap<>();

    //------------------------------- Construtores ---------------------------------

    /**
     * Construtor da classe PeriodicTable.
     *
     * @param compare: Critério de comparação dos elementos da tabela para inserção na árvore binário.
     */
    public PeriodicTable(Comparator<ChemicalElement> compare) {
        super(compare);
        PHASES.put("artificial", 0);
        PHASES.put("gas", 1);
        PHASES.put("liq", 2);
        PHASES.put("solid", 3);
    }
    //------------------------------- Métodos ---------------------------------

    /**
     * Pesquisa de elementos da table periódica através de objetos incompletos.
     *
     * @param searched Objeto que pode conter apenas o atributo alvo de compração.
     * @return Objeto na árvore binária para o qual a comparação com o parâmetro searched retorna zero,
     * ou null caso não exista o elemento na tabela.
     */
    public ChemicalElement find(ChemicalElement searched) {
        Node<ChemicalElement> searchResult = find(root, searched);
        return searchResult == null ? null : searchResult.getElement();
    }

    /**
     * Pesquisa por intervalos de valores.
     * Os parâmetros poderão ser objetos incompletos, tendo apenas de possuir o(s) atributo(s) para o qual o Comparador faz a comparação.
     *
     * @param min
     * @param max
     * @return Lista de todos os elementos no intervalor [min, max].
     */
    public List<ChemicalElement> searchByInterval(ChemicalElement min, ChemicalElement max) {

        List<ChemicalElement> listAux = new ArrayList<>();
        findInterval(root, min, max, listAux);

        return listAux;
    }

    /**
     * Ordena a @param listAux por Discoverer (ordem crescente) e, em caso de serem iguais, por Ano de Descoberta (ordem decrescente).
     *
     * @param listAux
     */
    public void orderByDiscovererAndYear(List<ChemicalElement> listAux) {
        Comparator byDiscovererAndYear = new Comparator<ChemicalElement>() {
            @Override
            public int compare(ChemicalElement o1, ChemicalElement o2) {
                if (o1.getDiscoverer().equals(o2.getDiscoverer())) {
                    return -Integer.compare(o1.getYearOfDiscovery(), o2.getYearOfDiscovery());
                } else {
                    return o1.getDiscoverer().compareTo(o2.getDiscoverer());
                }
            }
        };

        listAux.sort(byDiscovererAndYear);
    }

    /**
     * Ordena a @param listAux por Type e, em caso de serem iguais, por Phase.
     * Da lista ordenada obtém-se uma matriz (um array de pares de valores) onde consta a soma de elementos em que cada estado físico (colunas) para cada tipo de elemento (linhas)
     *
     * @param listAux
     * @return
     */
    public List<Pair<String, List<Integer>>> groupByTypeAndPhase(List<ChemicalElement> listAux) {
        Comparator<ChemicalElement> comparatorByTypeAndPhase = new Comparator<ChemicalElement>() {
            @Override
            public int compare(ChemicalElement o1, ChemicalElement o2) {
                if (o1.getType().equals(o2.getType())) {
                    return o1.getPhase().compareTo(o2.getPhase());
                } else {
                    return o1.getType().compareTo(o2.getType());
                }
            }
        };

        listAux.sort(comparatorByTypeAndPhase);

        //Criação da matriz resumo
        List<Pair<String, List<Integer>>> matrix = new ArrayList<>();
        List<Integer> newLine = new ArrayList<>(Collections.nCopies(PHASES.size(), 0));

        //Criação de uma linha de zeros e um prevElement vazio
        List<Integer> line = new ArrayList<>(newLine);
        ChemicalElement prevElement = listAux.remove(0);
        int sum = 1;
        //Iteração por todos os elementos da lista e adição das somas à matriz
        for (ChemicalElement element : listAux) {
            if (element.getType().equals(prevElement.getType())) {
                if (element.getPhase().equals(prevElement.getPhase())) sum++;
                else {
                    line.set(PHASES.get(prevElement.getPhase()), sum);
                    sum = 1;
                }
            } else {
                line.set(PHASES.get(prevElement.getPhase()), sum);
                matrix.add(new Pair<>(prevElement.getType(), new ArrayList<>(line)));
                Collections.copy(line, newLine);
                sum = 1;
            }
            prevElement = element;
        }
        line.set(PHASES.get(prevElement.getPhase()), sum);
        matrix.add(new Pair<>(prevElement.getType(), new ArrayList<>(line)));
        return matrix;
    }

    /**
     * Verifica a existência de repetição de padrões na configuração electrónica dos elementos.
     * Pode retornar apenas os padrões com um número mínimo de repetições (inclusivo).
     *
     * @return
     */
    private Map<String, Integer> getPatterns() {
        Map<String, Integer> patterns = new HashMap<>();
        for (ChemicalElement element : inOrder()) {
            StringBuilder configBuilder = new StringBuilder();
            for (String config : element.electronConfiguration.split(" ")) {
                configBuilder.append(" ").append(config);
                patterns.putIfAbsent(configBuilder.toString().trim(), 0);
            }

        }
        patterns.remove("");

        for (ChemicalElement element : inOrder()) {
            StringBuilder elementBuilder = new StringBuilder();
            for (String config : element.electronConfiguration.split(" ")) {
                elementBuilder.append(" ").append(config);

                if (patterns.containsKey(elementBuilder.toString().trim())) {
                    patterns.put(elementBuilder.toString().trim(), patterns.get(elementBuilder.toString().trim()) + 1);
                }
            }
        }
        return patterns;
    }

    public Map<String, Integer> getPatterns(int minRepetitions) {

        Map<String, Integer> sortedPatterns =
                getPatterns().entrySet().stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .filter(entry -> entry.getValue() >= minRepetitions)
                        .collect(Collectors.toMap(
                                Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        return sortedPatterns;
    }

    /**
     * Constrói uma AVL inserindo por ordem decrescente as configurações eletrónicas recebidas por parâmetro.
     *
     * @param mapAux: Key -> Configuração eletrónica; Value -> Nº de repetições.
     * @return
     */
    public BalancedTree<String> generateElectronConfigTree(Map<String, Integer> mapAux) {
        BalancedTree<String> bstElectronConfig = new BalancedTree<>(Comparator.comparing(String::toString));

        for (Map.Entry<String, Integer> entry : mapAux.entrySet()) {
            bstElectronConfig.insert(entry.getKey());
        }

        return bstElectronConfig;
    }
}

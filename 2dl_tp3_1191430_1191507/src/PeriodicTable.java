import java.util.*;

public class PeriodicTable extends BinaryTree<ChemicalElement> {

    final static Map<String, Integer> PHASES = new HashMap<>();

    public PeriodicTable(Comparator<ChemicalElement> compare) {
        super(compare);
        PHASES.put("artificial", 0);
        PHASES.put("gas", 1);
        PHASES.put("liq", 2);
        PHASES.put("solid", 3);
    }

    public ChemicalElement find(ChemicalElement searched) {
        Node<ChemicalElement> searchResult = find(root, searched);
        return searchResult == null ? null : searchResult.getElement();
    }

    public List<ChemicalElement> searchByInterval(ChemicalElement min, ChemicalElement max) {
        Node<ChemicalElement> minNode = findMin(root, min);
        Node<ChemicalElement> maxNode = findMax(root, max);

        List<ChemicalElement> listAux = new ArrayList<>();
        listAux.add(minNode.getElement());

        inOrderSubtree(maxNode, listAux, minNode);

        return listAux;
    }

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

    public List<List<Integer>> groupByTypeAndPhase(List<ChemicalElement> listAux) {
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
        List<List<Integer>> matrix = new ArrayList<>();
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
                matrix.add(new ArrayList<>(line));
                Collections.copy(line, newLine);
                sum = 1;
            }
            prevElement = element;
        }
        line.set(PHASES.get(prevElement.getPhase()), sum);
        matrix.add(new ArrayList<>(line));
        return matrix;
    }


}

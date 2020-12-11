import java.util.*;

public class PeriodicTable extends BinaryTree <ChemicalElement> {

    Map<String,Integer> phasesMap = new HashMap<>();

    public PeriodicTable(Comparator compare) {
        super(compare);
        phasesMap.put("artificial",0);
        phasesMap.put("gas",1);
        phasesMap.put("liq",2);
        phasesMap.put("solid",3);
    }

    public ChemicalElement find (ChemicalElement searched)
    {
        Node<ChemicalElement> searchResult = find(root,searched);

        if(searchResult != null)
        {
            return searchResult.getElement();
        }

        else
            return null;
    }

    public List<ChemicalElement> searchByInterval(ChemicalElement min, ChemicalElement max)
    {
        Node<ChemicalElement> minNode = find(root,min);
        Node<ChemicalElement> maxNode = find(root,max);

        List<ChemicalElement> listAux =  new ArrayList<ChemicalElement>();
        listAux.add(minNode.getElement());

        inOrderSubtree(maxNode,listAux,minNode);

        return listAux;
    }

    public void orderByDiscovererAndYear(List<ChemicalElement> listAux)
    {
        Comparator<ChemicalElement> comparatorByDiscovererAndYear = new Comparator<ChemicalElement>() {
            @Override
            public int compare(ChemicalElement o1, ChemicalElement o2) {
                if(o1.getDiscoverer().equals(o2.getDiscoverer()))
                {
                    return -Integer.compare(o1.getYearOfDiscovery(),o2.getYearOfDiscovery());
                }

                else
                {
                    return o1.getDiscoverer().compareTo(o2.getDiscoverer());
                }
            }
        };

        Collections.sort(listAux,comparatorByDiscovererAndYear);

    }

    public ArrayList<ArrayList<Integer>> groupByTypeAndPhase(List<ChemicalElement> listAux)
    {
        Comparator<ChemicalElement> comparatorByTypeAndPhase = new Comparator<ChemicalElement>() {
            @Override
            public int compare(ChemicalElement o1, ChemicalElement o2) {
                if(o1.getType().equals(o2.getType()))
                {
                    return o1.getPhase().compareTo(o2.getPhase());
                }

                else
                {
                    return o1.getType().compareTo(o2.getDiscoverer());
                }
            }
        };

        Collections.sort(listAux,comparatorByTypeAndPhase);

        ArrayList<ArrayList<Integer>> matrix = new ArrayList<>();

        Iterator<ChemicalElement> i = listAux.listIterator();

        ChemicalElement elementAux = i.next();

        String typeAux = elementAux.getType();
        String phaseAux = elementAux.getPhase();

        int contador = matrix.get(0).get(phasesMap.get(phaseAux));
        contador +=1;
        matrix.get(0).set(phasesMap.get(phaseAux),contador);

        int line = 0;

        while(i.hasNext())
        {
            ChemicalElement next = i.next();

            while(next.getType().equals(typeAux))
            {
                contador = 0;

                while(next.getPhase().equals(phaseAux))
                {
                    contador++;
                }

                matrix.get(line).set(phasesMap.get(phaseAux),contador);

            }
            line++;
        }

        return matrix;
    }


}

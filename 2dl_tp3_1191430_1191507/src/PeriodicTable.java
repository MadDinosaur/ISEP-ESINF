import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PeriodicTable extends BinaryTree <ChemicalElement> {

    public PeriodicTable(Comparator compare) {
        super(compare);
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


}

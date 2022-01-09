import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Testes unitários à classe BinaryTree (apenas aqueles que foram criados no âmbito do presente trabalho).
 */
public class BinaryTreeTest {
    //Dois tipos de Binary Tree - Periodic Table e Balanced Tree
    PeriodicTable emptyInstance = new PeriodicTable(null);
    PeriodicTable bigInstance = Main.periodicTable;

    BinaryTree<String> smallInstance;

    public BinaryTreeTest() throws FileNotFoundException {
        Main.readFile();
        smallInstance = Main.periodicTable.generateElectronConfigTree(Main.periodicTable.getPatterns(3));
    }

    @org.junit.Test
    public void isEmpty() {
        boolean actual = bigInstance.isEmpty();
        assertFalse(actual);

        smallInstance.isEmpty();
        assertFalse(actual);

        actual = emptyInstance.isEmpty();
        assertTrue(actual);
    }

    @org.junit.Test
    public void size() {
        int expected = 118;
        int actual = bigInstance.size();
        assertEquals(expected, actual);

        expected = 15;
        actual = smallInstance.size();
        assertEquals(expected, actual);

        expected = 0;
        actual = emptyInstance.size();
        assertEquals(expected, actual);
    }

    @org.junit.Test
    public void height() {
        int expected = 8;
        int actual = bigInstance.height();
        assertEquals(expected, actual);

        expected = 4;
        actual = smallInstance.height();
        assertEquals(expected, actual);

        expected = -1;
        actual = emptyInstance.height();
        assertEquals(expected, actual);
    }

    @org.junit.Test
    public void find() throws FileNotFoundException {
        ChemicalElement expected = readNFirstLines(1).get(0);

        ChemicalElement element = new ChemicalElement("Hydrogen");
        ChemicalElement actual = bigInstance.find(element);

        assertEquals(expected, actual);
    }

    @org.junit.Test
    public void findInterval() throws FileNotFoundException {
        List<String> actual = new ArrayList<>();
        smallInstance.findInterval(smallInstance.root, "[Ar]", "[He]", actual);

        List<String> expected = Arrays.asList("[Ar]", "[Ar] 3d10", "[Ar] 3d10 4s2", "[He]");

        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @org.junit.Test
    public void findMax() {
        BinaryTree.Node<String> actual = smallInstance.findMax(smallInstance.root, "[Z]");
        BinaryTree.Node<String> expected = smallInstance.find(smallInstance.root, "[Xe] 4f14 5d10 6s2");

        assertEquals(expected, actual);


        actual = smallInstance.findMax(smallInstance.root, "[A]");
        expected = null;

        assertEquals(expected, actual);
    }

    @org.junit.Test
    public void findMin() {
        BinaryTree.Node<String> actual = smallInstance.findMin(smallInstance.root, "[A]");
        BinaryTree.Node<String> expected = smallInstance.find(smallInstance.root, "[Ar]");

        assertTrue(actual.equals(expected));

        actual = smallInstance.findMin(smallInstance.root, "[Z]");
        expected = null;

        assertEquals(actual, expected);
    }

    @org.junit.Test
    public void inOrder() {
        List<String> actual = (List<String>) smallInstance.inOrder();
        List<String> expected = Arrays.asList("[Ar]", "[Ar] 3d10", "[Ar] 3d10 4s2", "[He]", "[He] 2s2", "[Kr]", "[Kr] 4d10", "[Kr] 4d10 5s2", "[Ne]", "[Ne] 3s2", "[Rn]", "[Xe]", "[Xe] 4f14", "[Xe] 4f14 5d10", "[Xe] 4f14 5d10 6s2");

        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @org.junit.Test
    public void nodesByLevel() {
        Map<Integer, List<String>> actual = smallInstance.nodesByLevel();

        Map<Integer, List<String>> expected = new HashMap<>();
        expected.put(0, Arrays.asList("[Kr] 4d10"));
        expected.put(1, Arrays.asList("[He]", "[Xe]"));
        expected.put(2, Arrays.asList("[Ar] 3d10", "[Kr]", "[Ne] 3s2", "[Xe] 4f14 5d10"));
        expected.put(3, Arrays.asList("[Ar]", "[Ar] 3d10 4s2", "[He] 2s2", "[Ne]", "[Rn]", "[Xe] 4f14", "[Xe] 4f14 5d10 6s2"));
        expected.put(4, Arrays.asList("[Kr] 4d10 5s2"));

        assertArrayEquals(expected.keySet().toArray(), actual.keySet().toArray());
        assertArrayEquals(expected.values().toArray(), actual.values().toArray());
    }

    @org.junit.Test
    public void maxDistance() {
        List<String> actualNodes = new ArrayList<>();
        int actual = smallInstance.maxDistance(actualNodes);

        List<String> expectedNodes = Arrays.asList("[Ar]", "[Kr] 4d10 5s2");
        int expected = 7;

        assertEquals(expected, actual);
        assertTrue(expectedNodes.containsAll(actualNodes));
    }

    @org.junit.Test
    public void completeTree() {
        BalancedTree<String> completeBstElectronConfig = new BalancedTree<>(Comparator.comparing(String::toString));
        completeBstElectronConfig.completeTree(smallInstance.inOrder());

        for (Map.Entry<Integer, List<String>> entry : completeBstElectronConfig.nodesByLevel().entrySet()) {
            if (entry.getKey() == completeBstElectronConfig.height()) break;
            assertEquals((int) Math.pow(2, entry.getKey()), entry.getValue().size());
        }
    }

    private List<ChemicalElement> readNFirstLines(int n) throws FileNotFoundException {
        Scanner reader = new Scanner(new File(Main.FILE_NAME), "ISO-8859-1");
        List<ChemicalElement> list = new ArrayList<>();
        //Descartar linha do cabeçalho
        reader.nextLine();
        for (int i = 0; i < n; i++) {
            String[] line = reader.nextLine().split(";");

            ChemicalElement element = new ChemicalElement(line[0], line[1], line[2], line[3], line[4], line[5], line[6], line[7], line[8], line[9], line[10], line[11], line[12], line[13], line[14], line[15], line[16], line[17], line[18], line[19], line[20], line[21], line[22], line[23]);
            list.add(element);
        }
        reader.close();
        return list;
    }
}
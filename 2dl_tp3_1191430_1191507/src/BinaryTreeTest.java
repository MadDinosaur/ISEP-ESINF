import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.*;

public class BinaryTreeTest {
    PeriodicTable emptyInstance = new PeriodicTable(null);
    PeriodicTable instance = Main.periodicTable;

    public BinaryTreeTest() throws FileNotFoundException {
        Main.readFile();
    }

    @org.junit.Test
    public void isEmpty() {
        boolean actual = instance.isEmpty();
        assertFalse(actual);

        actual = emptyInstance.isEmpty();
        assertTrue(actual);
    }

    @org.junit.Test
    public void size() {
        int expected = 118;
        int actual = instance.size();
        assertEquals(expected, actual);

        expected = 0;
        actual = emptyInstance.size();
        assertEquals(expected, actual);
    }

    @org.junit.Test
    public void height() {
        int expected = 8;
        int actual = instance.height();
        assertEquals(expected, actual);

        expected = -1;
        actual = emptyInstance.height();
        assertEquals(expected, actual);
    }

    @org.junit.Test
    public void find() throws FileNotFoundException {
        ChemicalElement expected = readNFirstLines(1).get(0);

        ChemicalElement element = new ChemicalElement("Hydrogen");
        ChemicalElement actual = instance.find(element);

        assert (expected.equals(actual));
    }

    @org.junit.Test
    public void findInterval() throws FileNotFoundException {
        ChemicalElement min = new ChemicalElement(1);
        ChemicalElement max = new ChemicalElement(3);
        List<ChemicalElement> actual = new ArrayList<>();
        instance.findInterval(instance.root, min, max, actual);

        List<ChemicalElement> expected = readNFirstLines(3);

        assert (expected.containsAll(actual));
        assert (actual.containsAll(expected));
    }

    @org.junit.Test
    public void findMax() {
    }

    @org.junit.Test
    public void findMin() {
    }

    @org.junit.Test
    public void inOrder() {
    }

    @org.junit.Test
    public void nodesByLevel() {
    }

    @org.junit.Test
    public void maxDistance() {
    }

    @org.junit.Test
    public void completeTree() {
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
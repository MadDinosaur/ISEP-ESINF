import javafx.util.Pair;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Testes unitários à classe PeriodicTable
 */

public class PeriodicTableTest {
    PeriodicTable instance = Main.periodicTable;
    PeriodicTable instance2 = Main.atomicNumbers;

    public PeriodicTableTest() throws FileNotFoundException {
        Main.readFile();
    }

    @Test
    public void find() throws FileNotFoundException {
        ChemicalElement actual = instance.find(new ChemicalElement("Hydrogen"));
        ChemicalElement expected = readNFirstLines(1).get(0);

        assertEquals(expected, actual);

        actual = instance.find(new ChemicalElement("Não existe"));
        expected = null;

        assertEquals(expected, actual);
    }

    @Test
    public void searchByInterval() throws FileNotFoundException {
        ChemicalElement min = new ChemicalElement(1);
        ChemicalElement max = new ChemicalElement(3);
        List<ChemicalElement> actual = instance2.searchByInterval(min, max);
        List<ChemicalElement> expected = readNFirstLines(3);

        assertArrayEquals(expected.toArray(), actual.toArray());

    }

    @Test
    public void orderByDiscovererAndYear() throws FileNotFoundException {
        List<ChemicalElement> actual = readNFirstLines(3);
        instance.orderByDiscovererAndYear(actual);

        List<ChemicalElement> expected = readNFirstLines(3);
        Collections.rotate(expected, 1);

        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    public void groupByTypeAndPhase() throws FileNotFoundException {
        List<Pair<String, List<Integer>>> actual = instance.groupByTypeAndPhase(readNFirstLines(3));

        List<Pair<String, List<Integer>>> expected = new ArrayList<>();
        expected.add(new Pair<>("Alkali Metal", Arrays.asList(0, 0, 0, 1)));
        expected.add(new Pair<>("Noble Gas", Arrays.asList(0, 1, 0, 0)));
        expected.add(new Pair<>("Nonmetal", Arrays.asList(0, 1, 0, 0)));

        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < actual.size(); i++) {
            assertEquals(expected.get(i).getKey(), actual.get(i).getKey());
            assertArrayEquals(expected.get(i).getValue().toArray(), actual.get(i).getValue().toArray());
        }
    }

    @Test
    public void getPatterns() {
        Map<String, Integer> actual = instance.getPatterns(18);

        String[] expectedKeys = {"[Xe]", "[Kr]", "[Ar]"};
        Integer[] expectedValues = {32, 18, 18};

        assertArrayEquals(expectedKeys, actual.keySet().toArray());
        assertArrayEquals(expectedValues, actual.values().toArray());
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
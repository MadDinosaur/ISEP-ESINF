import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    static String FILE_NAME = "2dl_tp3_1191430_1191507/Periodic Table of Elements.csv";

    //Criação das árvores binárias de pesquisa
    static PeriodicTable atomicNumbers = new PeriodicTable(ChemicalElement.getByAtomicNumber());
    static PeriodicTable symbols = new PeriodicTable(ChemicalElement.getBySymbol());
    static PeriodicTable atomicMasses = new PeriodicTable(ChemicalElement.getByAtomicMass());
    static PeriodicTable periodicTable = new PeriodicTable(ChemicalElement.getByElement());

    public static void main(String[] args) throws FileNotFoundException {
        readFile();
        //Testes
        searchbyAtomicNumber(2);
        searchbyElement("Helium");
        searchbySymbol("He");
        searchbyAtomicMass(4.0026f);

        searchAtomicMassInterval(20, 65);

        getElectronConfigurations();
    }

    public static void readFile() throws FileNotFoundException {
        Scanner reader = new Scanner(new File(FILE_NAME), "utf-8");

        //Descartar linha do cabeçalho
        reader.nextLine();

        while (reader.hasNextLine()) {
            String[] line = reader.nextLine().split(";");
            ChemicalElement element = new ChemicalElement(line[0], line[1], line[2], line[3], line[4], line[5], line[6], line[7], line[8], line[9], line[10], line[11], line[12], line[13], line[14], line[15], line[16], line[17], line[18], line[19], line[20], line[21], line[22], line[23]);
            periodicTable.insert(element);
            atomicNumbers.insert(element);
            symbols.insert(element);
            atomicMasses.insert(element);
        }
        reader.close();
    }

    //1. a)
    public static void searchbyAtomicNumber(int number) {
        ChemicalElement searchResult = atomicNumbers.find(new ChemicalElement(number));
        System.out.println(searchResult);
    }

    public static void searchbyElement(String element) {
        ChemicalElement searchResult = periodicTable.find(new ChemicalElement(element));
        System.out.println(searchResult);
    }

    public static void searchbySymbol(String symbol) {
        ChemicalElement searchResult = symbols.find(new ChemicalElement(symbol));
        System.out.println(searchResult);
    }

    public static void searchbyAtomicMass(float number) {
        ChemicalElement searchResult = atomicMasses.find(new ChemicalElement(number));
        System.out.println(searchResult);
    }

    //1. b)
    public static void searchAtomicMassInterval(float minAtomicMass, float maxAtomicMass) {
        List<ChemicalElement> interval = atomicMasses.searchByInterval(new ChemicalElement(minAtomicMass), new ChemicalElement(maxAtomicMass));

        atomicMasses.orderByDiscovererAndYear(interval);
        System.out.println("| Atomic Number | Element | Symbol | Atomic Mass | Phase | Type | Discoverer | Year of Discovery |");
        for (ChemicalElement e : interval) {
            System.out.printf("| %d | %s | %s | %.2f | %s | %s | %s | %d |\n", e.getAtomicNumber(), e.getElement(), e.getSymbol(), e.getAtomicMass(), e.getPhase(), e.getType(), e.getDiscoverer(), e.getYearOfDiscovery());
        }

        List<List<Integer>> summary = atomicMasses.groupByTypeAndPhase(interval);
        System.out.println("artificial    gas    liq    solid");
        for (List<Integer> l : summary) {
            System.out.println(Arrays.toString(l.toArray()));
        }
    }

    //2. a)
    public static void getElectronConfigurations() {
        periodicTable.getPatterns().forEach((k, v) -> System.out.printf("%d %s\n", v, k));
    }


    //2. b)


    //2. c)

    public static void getFurthestElectronConfig()
    {
        BinaryTree<Map.Entry<String,Integer>> bstElectronConfig = periodicTable.generateElectronConfigTree(periodicTable.getPatterns());

        Map<Integer,List<Map.Entry<String,Integer>>> mapAux = bstElectronConfig.nodesByLevel();

        int maxLvl = Collections.max(mapAux.keySet());

        if(mapAux.get(maxLvl).size() == 2)
        {
            
        }
    }

}

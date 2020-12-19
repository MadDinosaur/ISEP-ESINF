import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    static String FILE_NAME_FABIO = "2dl_tp3_1191430_1191507/Periodic Table of Elements.csv";
    static String FILE_NAME = "Periodic Table of Elements.csv";

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
        getFurthestElectronConfig();
    }

    public static void readFile() throws FileNotFoundException {
        Scanner reader = new Scanner(new File(FILE_NAME), "ISO-8859-1");

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
        System.out.println("=======================================================================================================");
        ChemicalElement searchResult = atomicNumbers.find(new ChemicalElement(number));
        System.out.println(searchResult);
        System.out.println("=======================================================================================================");
    }

    public static void searchbyElement(String element) {
        System.out.println("=======================================================================================================");
        ChemicalElement searchResult = periodicTable.find(new ChemicalElement(element));
        System.out.println(searchResult);
        System.out.println("=======================================================================================================");
    }

    public static void searchbySymbol(String symbol) {
        System.out.println("=======================================================================================================");
        ChemicalElement searchResult = symbols.find(new ChemicalElement(symbol));
        System.out.println(searchResult);
        System.out.println("=======================================================================================================");
    }

    public static void searchbyAtomicMass(float number) {
        System.out.println("=======================================================================================================");
        ChemicalElement searchResult = atomicMasses.find(new ChemicalElement(number));
        System.out.println(searchResult);
        System.out.println("=======================================================================================================");
    }

    //1. b)
    public static void searchAtomicMassInterval(float minAtomicMass, float maxAtomicMass) {
        List<ChemicalElement> interval = atomicMasses.searchByInterval(new ChemicalElement(minAtomicMass), new ChemicalElement(maxAtomicMass));

        atomicMasses.orderByDiscovererAndYear(interval);
        String[] headliner = {"Atomic Number", "Element", "Symbol", "Atomic Mass", "Phase", "Type", "Discoverer", "Year of Discovery"};
        System.out.println("==================================================================================================================================================");
        System.out.printf("|%-15s| %-15s| %-10s| %-15s| %-10s| %-25s| %-20s| %-20s| %n", headliner[0], headliner[1], headliner[2], headliner[3], headliner[4], headliner[5], headliner[6], headliner[7]);
        System.out.println("==================================================================================================================================================");
        for (ChemicalElement e : interval) {

            System.out.printf("|%-15s| %-15s| %-10s| %-15s| %-10s| %-25s| %-20s| %-20s| %n", e.getAtomicNumber(), e.getElement(), e.getSymbol(), e.getAtomicMass(), e.getPhase(), e.getType(), e.getDiscoverer().trim(), e.getYearOfDiscovery());

        }

        System.out.println("================================================================================================================================================== \n");

        System.out.println("=============================================================================");
        String[] headliner2 = {"Alkali Metal", "Alkaline Earth Metal", "Halogen", "Metal", "Metalloid", "Noble Gas", "Nonmetal", "Transition Metal"};
        int pos = 0;
        List<List<Integer>> summary = atomicMasses.groupByTypeAndPhase(interval);
        System.out.printf("%-21s %-13s %-10s %-10s %-10s %-10s%n", " ", "artificial", "gas", "liq", "solid", "TOTAL  |");
        System.out.println("=============================================================================");

        for (List<Integer> l : summary) {
            Integer artificial = l.get(0);
            Integer gas = l.get(1);
            Integer liq = l.get(2);
            Integer solid = l.get(3);

            Integer total = artificial + gas + liq + solid;

            System.out.printf("%-21s %-13s %-10s %-10s %-10s %-7s|%n", headliner2[pos], artificial.toString(), gas.toString(), liq.toString(), solid.toString(), total.toString());

            pos++;
        }
        System.out.println("=============================================================================");
    }


    //2. a)
    public static void getElectronConfigurations() {
        periodicTable.getPatterns().forEach((k, v) -> System.out.printf("%d %s\n", v, k));
    }

    //2. c)

    public static void getFurthestElectronConfig() {
        List<String> auxList = new ArrayList<>();
        BinaryTree<String> bstElectronConfig = periodicTable.generateElectronConfigTree(periodicTable.getPatterns());
        System.out.printf("A distância entre as duas configurações eletrónicas mais distantes é: %d.\n" +
                        "Possível combinação de configurações eletrónicas mais distantes: %s.\n",
                bstElectronConfig.maxDistance(auxList), Arrays.toString(auxList.toArray()));
    }
}

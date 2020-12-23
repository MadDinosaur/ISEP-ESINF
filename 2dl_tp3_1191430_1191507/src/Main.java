import javafx.scene.chart.ScatterChart;
import javafx.util.Pair;

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

    Scanner sc = new Scanner(System.in);
    String menu = "Aqui estão as funcionalidades disponíveis: \n" +
                    "1. Pesquisa de Elementos \n" +
                    "2. Pesquisar por Intervalo \n" +
                    "3. Configurações eletrónicas e suas repetições \n" +
                    "4. Nova BST de configurações eletrónicas \n" +
                    "5. Configurações eletrónicas mais distantes \n" +
                    "6. BST Completa \n" +
                    "0. Opção de saída \n";

    System.out.printf("Bem-vindo à Tabela Períodica!\n" + menu);

    try {
        int op = sc.nextInt();
            while (op != 0)
            {
                switch (op)
                {
                    case 1:

                        System.out.println("\nDeseja pesquisar de que forma?");
                        System.out.println("1. Atomic Number");
                        System.out.println("2. Symbol");
                        System.out.println("3. AtomicMasses");
                        System.out.println("4. Element");

                        int escolha = sc.nextInt();
                        sc.nextLine();

                        switch (escolha)
                        {
                            case 1:
                                System.out.println("\nIntroduza o número atómico do elemento");
                                int numeroAtomico = sc.nextInt();

                                System.out.println();
                                searchbyAtomicNumber(numeroAtomico);
                                System.out.println(menu);
                                op = sc.nextInt();
                                break;

                            case 2:
                                System.out.println("\nIntroduza o símbolo do elemento");
                                String simbolo = sc.nextLine();
                                System.out.println();
                                searchbySymbol(simbolo);

                                System.out.println(menu);
                                op = sc.nextInt();
                                break;

                            case 3:
                                System.out.println("\nIntroduza a massa atómica do elemento");
                                float massaAtomica = sc.nextFloat();
                                System.out.println();

                                searchbyAtomicMass(massaAtomica);
                                System.out.println(menu);
                                op = sc.nextInt();
                                break;

                            case 4:
                                System.out.println("\nIntroduza o nome do elemento");
                                String nomeElemento = sc.nextLine();
                                System.out.println();

                                searchbyElement(nomeElemento);
                                System.out.println(menu);
                                op = sc.nextInt();
                                break;

                            default:
                                System.out.println("Opção Inválida. Insira opção novamente");
                        }
                    break;

                case 2:
                    System.out.println("Introduza o intervalo que pretende pesquisar");
                    System.out.println("Introduza o valor mínimo: ");

                    float min = sc.nextFloat();

                    System.out.println("Introduza o valor máximo: ");

                    float max = sc.nextFloat();

                    searchAtomicMassInterval(min, max);
                    System.out.println(menu);
                    op = sc.nextInt();
                    break;

                case 3:
                    System.out.println("Aqui está a tabela de configurações eletrónicas e suas repetições: ");
                    getElectronConfigurations();
                    System.out.println(menu);
                    op = sc.nextInt();
                    break;

                case 4:
                    System.out.println("Nova BST: ");
                    BalancedTree<String> bstElectronConfig = periodicTable.generateElectronConfigTree(periodicTable.getPatterns(3));
                    System.out.println(bstElectronConfig);
                    System.out.println(menu);
                    op = sc.nextInt();
                    break;

                case 5:
                    getFurthestElectronConfig();
                    System.out.println(menu);
                    op = sc.nextInt();
                    break;

                case 6:
                    System.out.println("BST completa: ");
                    completeElectronConfigTree();
                    System.out.println(menu);
                    op = sc.nextInt();
                    break;

                default:
                    System.out.println("Opção Inválida. Insira opção novamente.");
                    System.out.println(menu);
                    op = sc.nextInt();
            }
        }

    System.out.println("Obrigado pela sua visita! Volte sempre!");
}
    catch (NullPointerException e)
    {
        e.printStackTrace();
    }
        catch (InputMismatchException p)
        {
            System.out.println("Argumento Inválido. Restart Program.");
        }
    }
        public static void readFile () throws FileNotFoundException {
            Scanner reader = new Scanner(new File(FILE_NAME_FABIO), "ISO-8859-1");

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
        public static void searchbyAtomicNumber ( int number){
            ChemicalElement searchResult = atomicNumbers.find(new ChemicalElement(number));
            if (searchResult == null) {
                System.out.println("Elemento não existe");
            } else {
                System.out.println(searchResult);
            }
            System.out.println();
        }

        public static void searchbyElement (String element){
            ChemicalElement searchResult = periodicTable.find(new ChemicalElement(element));
            if (searchResult == null) {
                System.out.println("Elemento não existe");
            } else {
                System.out.println(searchResult);
            }
            System.out.println();
        }

        public static void searchbySymbol (String symbol){
            ChemicalElement searchResult = symbols.find(new ChemicalElement(symbol));
            if (searchResult == null) {
                System.out.println("Elemento não existe");
            } else {
                System.out.println(searchResult);
            }
            System.out.println();
        }

        public static void searchbyAtomicMass ( float number){
            ChemicalElement searchResult = atomicMasses.find(new ChemicalElement(number));
            if (searchResult == null) {
                System.out.println("Elemento não existe");
            } else {
                System.out.println(searchResult);
            }
            System.out.println();
        }

        //1. b)
        public static void searchAtomicMassInterval ( float minAtomicMass, float maxAtomicMass){
            List<ChemicalElement> interval = atomicMasses.searchByInterval(new ChemicalElement(minAtomicMass), new ChemicalElement(maxAtomicMass));

            atomicMasses.orderByDiscovererAndYear(interval);
            String[] headliner = {"Atomic Number", "Element", "Symbol", "Atomic Mass", "Phase", "Type", "Discoverer", "Year of Discovery"};
            System.out.println("==============================================================================================================================================================");
            System.out.printf("|%-15s| %-15s| %-10s| %-15s| %-12s| %-25s| %-30s| %-20s| %n", headliner[0], headliner[1], headliner[2], headliner[3], headliner[4], headliner[5], headliner[6], headliner[7]);
            System.out.println("==============================================================================================================================================================");
            for (ChemicalElement e : interval) {

                System.out.printf("|%-15s| %-15s| %-10s| %-15s| %-12s| %-25s| %-30s| %-20s| %n", e.getAtomicNumber(), e.getElement(), e.getSymbol(), e.getAtomicMass(), e.getPhase(), e.getType(), e.getDiscoverer().trim(), e.getYearOfDiscovery());

            }

            System.out.println("============================================================================================================================================================== \n");

            System.out.println("=============================================================================");
            List<Pair<String, List<Integer>>> summary = atomicMasses.groupByTypeAndPhase(interval);
            System.out.printf("%-21s %-13s %-10s %-10s %-10s %-10s%n", " ", "artificial", "gas", "liq", "solid", "TOTAL  |");
            System.out.println("=============================================================================");

            for (Pair<String, List<Integer>> l : summary) {
                List<Integer> p = l.getValue();
                String s = l.getKey();
                Integer artificial = p.get(0);
                Integer gas = p.get(1);
                Integer liq = p.get(2);
                Integer solid = p.get(3);

                Integer total = artificial + gas + liq + solid;

                System.out.printf("%-21s %-13s %-10s %-10s %-10s %-7s|%n", s, artificial.toString(), gas.toString(), liq.toString(), solid.toString(), total.toString());

            }
            System.out.println("=============================================================================\n");
        }


        //2. a)
        public static void getElectronConfigurations ()
        {
            Iterator<Map.Entry<String, Integer>> iterator = periodicTable.getPatterns(2).entrySet().iterator();

            int repetitions = 0;

            while (iterator.hasNext()) {

                Map.Entry<String, Integer> entry = iterator.next();

                if (entry.getValue() == repetitions) {
                    System.out.printf(" %-2s", entry.getKey());
                } else {
                    System.out.printf("\n %-2d %s", entry.getValue(), entry.getKey());
                }

                repetitions = entry.getValue();
            }
            System.out.printf("\n\n");
        }

        //2. c)

        public static void getFurthestElectronConfig () {
            List<String> auxList = new ArrayList<>();
            BalancedTree<String> bstElectronConfig = periodicTable.generateElectronConfigTree(periodicTable.getPatterns(3));
            System.out.printf("A distância entre as duas configurações eletrónicas mais distantes é: %d.\n" +
                            "Possível combinação de configurações eletrónicas mais distantes: %s.\n",
                    bstElectronConfig.maxDistance(auxList), Arrays.toString(auxList.toArray()));
        }

        //2. d)
        public static void completeElectronConfigTree () {
            BalancedTree<String> bstElectronConfig = periodicTable.generateElectronConfigTree(periodicTable.getPatterns(3));
            BalancedTree<String> insertionList = periodicTable.generateElectronConfigTree(periodicTable.getPatterns(1, 2));
            bstElectronConfig.completeTree(insertionList);
            bstElectronConfig.inOrder().forEach(x -> System.out.printf("%s; ", x));
            System.out.printf("\n%s\n", bstElectronConfig.toString());
        }

    }

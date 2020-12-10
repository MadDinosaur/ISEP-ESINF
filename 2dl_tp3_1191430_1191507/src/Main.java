import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    static String FILE_NAME = "2dl_tp3_1191430_1191507/Periodic Table of Elements.csv";

    //Criação das árvores binárias de pesquisa
    static PeriodicTable atomicNumbers = new PeriodicTable(ChemicalElement.getByAtomicNumber());
    static PeriodicTable symbols = new PeriodicTable(ChemicalElement.getBySymbol());
    static PeriodicTable atomicMasses = new PeriodicTable(ChemicalElement.getByAtomicMass());
    static PeriodicTable periodicTable = new PeriodicTable(ChemicalElement.getByElement());

    public static void main(String[] args) throws FileNotFoundException {
        readFile();
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
}

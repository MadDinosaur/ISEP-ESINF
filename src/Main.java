import java.io.*;
import java.util.Scanner;

public class Main {

    final int ISO_CODE = 0;
    final int CONTINENT = 1;
    final int LOCATION = 2;
    final int DATE = 3;
    final int TOTAL_CASES = 4;
    final int NEW_CASES = 5;
    final int TOTAL_DEATHS = 6;
    final int 

    public static void main(String[] args) {

    }

    public static void readFile(String fileName) throws IOException {
        Scanner reader = new Scanner(new File(fileName));
        while(reader.hasNextLine()) {
            String[] line = reader.nextLine().split(",");
        }
        reader.close();
    }
}

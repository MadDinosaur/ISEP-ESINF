import javax.print.attribute.standard.MediaSize;
import java.io.*;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {

    static final int ISO_CODE = 0;
    static final int CONTINENT = 1;
    static final int LOCATION = 2;
    static final int DATE = 3;
    static final int TOTAL_CASES = 4;
    static final int NEW_CASES = 5;
    static final int TOTAL_DEATHS = 6;
    static final int NEW_DEATHS = 7;
    static final int NEW_TESTS = 8;
    static final int TOTAL_TESTS = 9;
    static final int POPULATION = 10;
    static final int AGED_65_OLDER = 11;
    static final int CARDIOVASC_DEATH_RATE = 12;
    static final int DIABETES_PREVALENCE = 13;
    static final int FEMALE_SMOKERS = 14;
    static final int MALE_SMOKERS = 15;
    static final int HOSPITAL_BEDS_PER_THOUSAND = 16;
    static final int LIFE_EXPECTANCY = 17;


    public static void main(String[] args) {

    }

    public static void readFile(String fileName) throws IOException {
        Scanner reader = new Scanner(new File(fileName));

        Continent continent;
        Country country;

        //Descarta linha do cabeçalho, se o ficheiro não estiver vazio
        if (reader.hasNextLine()) {
            reader.nextLine();
        }
        //Lê o ficheiro linha a linha e atribuiu os valores das colunas às respetivas classes
        while(reader.hasNextLine()) {
            String[] line = reader.nextLine().split(",");

            if (!(line[CONTINENT] in World.getContinents())){
                continent = new Continent(line[CONTINENT]);
            }

            if (!(line[ISO_CODE] in continent.getCountryCodes())){
                country = new Country(line[ISO_CODE], line[LOCATION]);
                continent.addCountry(country);
            }

            LocalDate date = new LocalDate(line[DATE]);
            Case dailyCases = new Case();
            Death dailyDeaths = new Death();
            Test dailyTests = new Test();
            Smoker dailySmokers = new Smoker();

            dailyCases.addCase(line[NEW_CASES], line[TOTAL_CASES], date);
            dailyDeaths.addDeath(line[NEW_DEATHS], line[TOTAL_DEATHS], date);
            dailyTests.addTest(line[NEW_TESTS], line[TOTAL_TESTS], date);
            dailySmokers.addSmoker(line[FEMALE_SMOKERS],line[MALE_SMOKERS], date);

            country.addData(dailyCases, dailyDeaths, dailyTests, dailySmokers);
        }
        reader.close();
    }
}
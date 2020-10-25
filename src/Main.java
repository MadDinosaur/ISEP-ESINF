import javax.naming.Name;
import java.io.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

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
    static final float PERCENTAGE_SMOKERS = 70f;
    static final int YEAR = 2020;


    public static void main(String[] args) throws IOException {
        readFile("owid-covid-data.csv");
        //printDailyCases("\"Europe\"", 9);
        printMonthlyCasesAndDeaths();
        //printMinDays();
    }

    //ex01

    public static void readFile(String fileName) throws IOException {
        Scanner reader = new Scanner(new File(fileName));

        //Inicialização das classes
        Continent continent = null;
        Country country = null;
        Case dailyCases = null;
        Death dailyDeaths = null;
        Test dailyTests = null;
        Smoker dailySmokers = null;
        LocalDate date;

        //Descarta linha do cabeçalho, se o ficheiro não estiver vazio
        if (reader.hasNextLine()) {
            reader.nextLine();
        }
        //Lê o ficheiro linha a linha e atribuiu os valores das colunas às respetivas classes
        while (reader.hasNextLine()) {
            String[] line = reader.nextLine().split(",");

            if (!World.continentList().contains(line[CONTINENT])) {
                continent = new Continent(line[CONTINENT]);
            } else {
                continent = World.get(line[CONTINENT]);
            }

            if (continent.getCountries().get(line[ISO_CODE]) == null) {
                country = new Country(line[ISO_CODE], line[LOCATION]);
                continent.addCountry(country);

                dailyCases = new Case();
                dailyDeaths = new Death();
                dailyTests = new Test();
                dailySmokers = new Smoker();
            }

            date = LocalDate.parse(line[DATE]);

            dailyCases.addCase(line[NEW_CASES], line[TOTAL_CASES], date);
            dailyDeaths.addDeath(line[NEW_DEATHS], line[TOTAL_DEATHS], date);
            dailyTests.addTest(line[NEW_TESTS], line[TOTAL_TESTS], date);
            dailySmokers.addSmoker(line[FEMALE_SMOKERS], line[MALE_SMOKERS], date);

            country.addData(dailyCases, dailyDeaths, dailyTests, dailySmokers);
        }
        reader.close();
    }

    //ex02

    public static void printMinDays() {
        int numCases = 50000;
        Map<LocalDate, Country> totalCasesByMinDays = new TreeMap<>();
        //ALTERAR
        LocalDate firstDate = LocalDate.of(2020, 1, 1);

        for (Continent continent : World.getContinents()) {
            for (Country country : continent.getCountries().values()) {
                LocalDate casesAchievedDate = country.numCasesReached(numCases);
                if (casesAchievedDate != null) {
                    totalCasesByMinDays.put(country.numCasesReached(numCases), country);
                }
            }
        }
        System.out.printf("%-10s %-15s %-22s %-15s %-15s %-10s\n", "iso_code", "continent", "location", "date", "total_cases", "mindays");
        Iterator printer = totalCasesByMinDays.entrySet().iterator();
        while (printer.hasNext()) {
            Map.Entry<LocalDate, Country> entry = (Map.Entry<LocalDate, Country>) printer.next();
            LocalDate d = entry.getKey();
            int minDays = (int) ChronoUnit.DAYS.between(firstDate, d);
            Country c = entry.getValue();
            System.out.printf("%-10s %-15s %-22s %-15s %-15d %d days\n", c.getIsoCode(), c.getContinent().getName(), c.getLocation(), d, c.getTotalCases(d), minDays);
        }
    }

    // ex03

    public static void printMonthlyCasesAndDeaths() {
        for (Continent continent : World.getContinents()) {
            Map<Integer, Integer> deaths = continent.totalDeathsPerMonth();
            Map<Integer, Integer> cases = continent.totalCasesPerMonth();

            System.out.printf("%-15s %-5s %-10s %-10s\n", "continent", "month", "new_cases", "new_deaths");
            for (int month : cases.keySet()) {
                System.out.printf("%-15s %-5d %-10d %-10d\n", continent.getName(), month, cases.get(month), deaths.get(month));
            }
        }
    }

    //ex04

    public static void printDailyCases(String continent, int month) {
        World.get(continent).newCasesPerMonth(YEAR, month);
    }

    //ex05

    /*public static void printDeathsSmokersByCountry() {
        Map<Integer, String> deaths = new HashMap<>();

        for (Continent continent : World.getContinents()) {
            for (Country country : continent.getCountries().values()) {
                LocalDate date = LocalDate.of(2020, 1, 1);

                float percentage = country.getTotalSmokers(date);

                deaths.put(continent.getDeathsPerSmokerPercentage(percentage).keySet().iterator().next(), country.getLocation());

                System.out.println("[%s, %.2f, %d]\n", deaths.values(), country.getTotalSmokers(date), deaths.keySet().iterator().next());
            }
        }

    }*/
}

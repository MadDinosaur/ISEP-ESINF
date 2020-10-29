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

        Scanner sc = new Scanner(System.in);
        int n = 0;

        do {
            System.out.println("Bem-Vindo à aplicação COVID-19 ANALISER");
            System.out.println("--------------------------------------------------------------x----------------------------------------------------------------------");
            System.out.println("1. Lista de países por ordem crescente do menor números de dias a atingir 50000 casos.");
            System.out.println("2. Informação do número de novos casos positivos e novas mortes em função do continente por mês.");
            System.out.println("3. Lista dos países por ordem decrescente do número de novos casos positivos, para um determinado continente e um determinado mês.");
            System.out.println("4. Informação dos países com mais de 70% de população fumadora, ordenada por ordem decrescente de novas mortes.");
            System.out.println("0. Sair da aplicação.");
            System.out.println("--------------------------------------------------------------x----------------------------------------------------------------------");
            System.out.println("Insira a opção que pretende escolher: ");
            n = sc.nextInt();
            sc.nextLine();

            switch (n) {
                case 0: {
                    break;
                }

                case 1: {
                    printMinDays();
                    break;
                }

                case 2: {
                    printMonthlyCasesAndDeaths();
                    break;
                }

                case 3: {
                    String name;
                    int month;

                    do {
                        System.out.println("Insira o nome do continente que pretende pesquisar:");
                        name = sc.nextLine();

                        System.out.println("Insira o número do mês que pretende pesquisar:");
                        month = sc.nextInt();
                        sc.nextLine();

                    } while (month < 1 || month > 12 && !World.continentList().contains(name));

                    printDailyCases(name, month);
                    break;
                }

                case 4: {
                    printDeathsSmokersByCountry();
                    break;
                }

                default: {
                    System.out.println("Insira uma opção válida!");
                }
            }

        } while (n != 0);
    }

    //ex01
    private static String removeQuotes(String str) {
        return str.replace("\"", "");
    }

    public static void readFile(String fileName) throws IOException {
        Scanner reader = new Scanner(new File(fileName));

        //Inicialização das classes
        Continent continent = null;
        Country country = null;
        Case dailyCases = null;
        Death dailyDeaths = null;
        Smoker dailySmokers = null;
        LocalDate date;

        //Descarta linha do cabeçalho, se o ficheiro não estiver vazio
        if (reader.hasNextLine()) {
            reader.nextLine();
        }
        //Lê o ficheiro linha a linha e atribuiu os valores das colunas às respetivas classes
        while (reader.hasNextLine()) {
            String[] line = reader.nextLine().split(",");

            if (!World.continentList().contains(removeQuotes(line[CONTINENT]))) {
                continent = new Continent(removeQuotes(line[CONTINENT]));
            } else {
                continent = World.get(removeQuotes(line[CONTINENT]));
            }

            if (continent.getCountries().get(removeQuotes(line[ISO_CODE])) == null) {
                country = new Country(removeQuotes(line[ISO_CODE]), removeQuotes(line[LOCATION]));
                continent.addCountry(country);

                dailyCases = new Case();
                dailyDeaths = new Death();
                dailySmokers = new Smoker();
            }

            date = LocalDate.parse(line[DATE]);

            dailyCases.addCase(line[NEW_CASES], line[TOTAL_CASES], date);
            dailyDeaths.addDeath(line[NEW_DEATHS], line[TOTAL_DEATHS], date);
            dailySmokers.addSmoker(line[FEMALE_SMOKERS], line[MALE_SMOKERS], date);

            country.addData(dailyCases, dailyDeaths, dailySmokers);
        }
        reader.close();
    }

    //ex02

    public static void printMinDays() {
        int numCases = 50000;
        List<Country> casesReached = new ArrayList<>();
        LocalDate initialDate = LocalDate.now();
        Comparator<Country> byMinDays = new Comparator<Country>() {
            @Override
            public int compare(Country o1, Country o2) {
                return o1.dateCasesReached(numCases).compareTo(o2.dateCasesReached(numCases));
            }
        };
        for (Continent continent : World.getContinents()) {
            for (Country country : continent.getCountries().values()) {
                if (country.dateCasesReached(numCases) != null) {
                    initialDate = initialDate.compareTo(country.oldestEntry()) < 0 ? initialDate : country.oldestEntry();
                    casesReached.add(country);
                }
            }
        }
        Collections.sort(casesReached, byMinDays);

        //Print text
        System.out.printf("%-10s %-15s %-22s %-15s %-15s %-10s\n", "iso_code", "continent", "location", "date", "total_cases", "mindays");

        for (Country c : casesReached) {
            LocalDate d = c.dateCasesReached(numCases);
            System.out.printf("%-10s %-15s %-22s %-15s %-15d %d days\n", c.getIsoCode(), c.getContinent().getName(),
                    c.getLocation(), d, c.getTotalCases(d), c.numCasesReached(initialDate, numCases));
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
        Continent c = World.get(continent);
        LocalDate d = LocalDate.of(YEAR, month, 1);

        do {
            System.out.printf("Dia %d --> ", d.getDayOfMonth());
            for (Country country : c.newCasesPerDay(d)) {
                System.out.printf("%s (%d)\n", country, country.getNewCases(d));
            }
            d = d.plusDays(1);
        } while (c.newCasesPerDay(d) != null);
    }

    //ex05

    public static void printDeathsSmokersByCountry() {
        ArrayList<Country> countries = new ArrayList<>();
        Comparator<Country> byNewDeaths = new Comparator<Country>() {

            @Override
            public int compare(Country o1, Country o2) {
                return -Integer.compare(o1.getLatestTotalDeaths(), o2.getLatestTotalDeaths());
            }
        };

        for (Continent continent : World.getContinents()) {
            for (Country country : continent.getCountries().values()) {
                if (country.hasMoreSmokersThan(70f)) {
                    countries.add(country);
                }
            }
        }

        Collections.sort(countries, byNewDeaths);

        countries.forEach(country -> System.out.printf("[%s, %.1f, %d]\n", country, country.getSmokerPercentage(), country.getLatestTotalDeaths()));
    }
}


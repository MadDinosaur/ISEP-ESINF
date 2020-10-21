import javafx.util.Pair;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class Country {

    private Continent continent;
    private String isoCode;
    private String location;
    private Map<LocalDate, Integer> totalCases;
    private Map<LocalDate, Integer> newCases;
    private Map<LocalDate, Integer> totalDeaths;
    private Map<LocalDate, Integer> newDeaths;
    private Map<LocalDate, Integer> totalTests;
    private Map<LocalDate, Integer> newTests;
    private Map<LocalDate, Float> totalSmokers;

    public Country(String isoCode, String location) {
        this.isoCode = isoCode;
        this.location = location;
        this.totalCases = new TreeMap<>();
        this.newCases = new TreeMap<>();
        this.totalDeaths = new TreeMap<>();
        this.newDeaths = new TreeMap<>();
        this.totalTests = new TreeMap<>();
        this.newTests = new TreeMap<>();
        this.totalSmokers = new TreeMap<>();
    }

    public String getIsoCode() {
        return isoCode;
    }

    public String getLocation() {
        return location;
    }

    public Continent getContinent() {
        return continent;
    }

    public void setContinent(Continent continent) {
        this.continent = continent;
    }

    public void addData(Case dailyCases, Death dailyDeaths, Test dailyTests, Smoker dailySmokers) {
        this.newCases = dailyCases.getNewCases();
        this.totalCases = dailyCases.getTotalCases();
        this.newDeaths = dailyDeaths.getNewDeaths();
        this.totalDeaths = dailyDeaths.getTotalDeaths();
        this.newTests = dailyTests.getNewTests();
        this.totalTests = dailyTests.getTotalTests();
        this.totalSmokers = dailySmokers.getTotalSmokers();
    }

    /**
     * Retorna a data em que o país atingiu um dado número de casos positivos, assim como o número de dias que demorou
     * a atingir esse número a partir da data inicial de recolha de dados.
     *
     * @param numCases Número de casos positivos a atingir
     * @return Um Pair cuja Key é a data em que o país atingiu o dado número de casos positivos e o Value é o número de
     * dias desde a data inicial de recolha de dados até essa data.
     */
    public Pair<LocalDate, Long> daysUntilXCases(int numCases) {
        Iterator<Map.Entry<LocalDate, Integer>> i = totalCases.entrySet().iterator();

        Map.Entry<LocalDate, Integer> currentEntry = i.next();
        LocalDate initialDate = currentEntry.getKey();

        while (i.hasNext() && currentEntry.getValue() < numCases) {
            currentEntry = i.next();
        }

        LocalDate casesAchievedDate = currentEntry.getKey();
        long numDays = ChronoUnit.DAYS.between(initialDate, casesAchievedDate);

        return new Pair<LocalDate, Long>(casesAchievedDate, numDays);
    }

    @Override
    public boolean equals(Object o) {
        // self check
        if (this == o)
            return true;
        // null check
        if (o == null)
            return false;
        // type check and cast
        if (getClass() != o.getClass())
            return false;
        Country c = (Country) o;
        // field comparison
        return c.getIsoCode().equalsIgnoreCase(this.getIsoCode());
    }

    @Override
    public int hashCode() {
        return getIsoCode().hashCode();
    }
}

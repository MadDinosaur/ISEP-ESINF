import java.time.LocalDate;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class Country {

    private Continent continent;
    private String isoCode;
    private String location;
    private Case caseLists;
    private Death deathLists;
    private Map<LocalDate, Integer> totalTests;
    private Map<LocalDate, Integer> newTests;
    private Map<LocalDate, Float> totalSmokers;

    public Country(String isoCode, String location) {
        this.isoCode = isoCode;
        this.location = location;
        this.caseLists = new Case();
        this.deathLists = new Death();
        this.totalTests = new TreeMap<>();
        this.newTests = new TreeMap<>();
        this.totalSmokers = new TreeMap<>();
    }

    //---------------- Getters ----------------
    public String getIsoCode() {
        return isoCode;
    }

    public String getLocation() {
        return location;
    }

    public Continent getContinent() {
        return continent;
    }

    public Map<LocalDate, Integer> getTotalCases() {
        return caseLists.getTotalCases();
    }

    public int getTotalCases(LocalDate d) {
        return caseLists.getTotalCases().get(d);
    }

    public Map<Integer, Integer> getMonthlyCases() {
        return caseLists.getMonthlyCases();
    }

    public int getMonthlyCases(int month) {
        return caseLists.getMonthlyCases().get(month);
    }

    public float getTotalSmokers(LocalDate d) {
        return totalSmokers.get(d);
    }

    //---------------- Setters ----------------
    public void setContinent(Continent continent) {
        this.continent = continent;
    }

    //---------------- Public update methods ----------------
    public void addData(Case dailyCases, Death dailyDeaths, Test dailyTests, Smoker dailySmokers) {
        this.caseLists = dailyCases;
        this.deathLists = dailyDeaths;
        this.newTests = dailyTests.getNewTests();
        this.totalTests = dailyTests.getTotalTests();
        this.totalSmokers = dailySmokers.getTotalSmokers();
    }

    //---------------- Public statistical methods ----------------
    /**
     * Calcula a data em que o país atingiu um dado número de casos positivos.
     *
     * @param numCases Número de casos positivos a atingir
     * @return Data em que o país atingiu o dado número de casos positivos.
     */

    public LocalDate numCasesReached(int numCases) {
        Iterator<Map.Entry<LocalDate, Integer>> i = caseLists.getTotalCases().entrySet().iterator();

        Map.Entry<LocalDate, Integer> currentEntry = i.next();

        while (i.hasNext() && currentEntry.getValue() < numCases) {
            currentEntry = i.next();
        }

        LocalDate casesAchievedDate;
        if (currentEntry.getValue() >= numCases) {
            casesAchievedDate = currentEntry.getKey();
        } else {
            casesAchievedDate = null;
        }

        return casesAchievedDate;
    }

    public boolean hasMoreSmokers(Float percentage) {
        if (percentage > 70.0) {
            return true;
        }

        return false;
    }
    //---------------- Override methods ----------------
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

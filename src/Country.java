import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class Country {

    private Continent continent;
    private String isoCode;
    private String location;
    private Case caseLists;
    private Death deathLists;
    private TreeMap<LocalDate, Float> totalSmokers;

    public Country(String isoCode, String location) {
        this.isoCode = isoCode;
        this.location = location;
        this.caseLists = new Case();
        this.deathLists = new Death();
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

    public Map<LocalDate, Integer> getNewCases() {
        return caseLists.getNewCases();
    }

    public int getNewCases(LocalDate d) {
        try {
            return caseLists.getNewCases().get(d);
        } catch (NullPointerException e) {
            return 0;
        }
    }

    public Map<Integer, Integer> getMonthlyCases() {
        return caseLists.getMonthlyCases();
    }

    public int getMonthlyCases(int month) {
        return caseLists.getMonthlyCases().get(month);
    }

    public int getLatestTotalDeaths() {
        return deathLists.getLatestDeathTotal();
    }

    public Map<Integer, Integer> getMonthlyDeaths() {
        return deathLists.getMonthlyDeaths();
    }

    public int getMonthlyDeaths(int month) {
        return deathLists.getMonthlyDeaths().get(month);
    }

    public float getSmokerPercentage() {
        return totalSmokers.lastEntry().getValue();
    }

    public LocalDate oldestEntry() {
        try {
            return caseLists.getOldestDate();
        } catch (NullPointerException e) {
            return null;
        }
    }

    //---------------- Setters ----------------
    public void setContinent(Continent continent) {
        this.continent = continent;
    }

    //---------------- Public update methods ----------------
    public void addData(Case dailyCases, Death dailyDeaths, Smoker dailySmokers) {
        this.caseLists = dailyCases;
        this.deathLists = dailyDeaths;
        this.totalSmokers = dailySmokers.getTotalSmokers();
    }

    //---------------- Public statistical methods ----------------

    /**
     * Calcula a data em que o país atingiu um dado número de casos positivos.
     *
     * @param numCases Número de casos positivos a atingir
     * @return Data em que o país atingiu o dado número de casos positivos.
     */

    public LocalDate dateCasesReached(int numCases) {
        //Check para verificar se o mais recente total de casos ultrapassa numCases
        if (caseLists.getLatestCaseTotal() < numCases) {
            return null;
        }

        Iterator<Map.Entry<LocalDate, Integer>> i = caseLists.getTotalCases().entrySet().iterator();

        Map.Entry<LocalDate, Integer> currentEntry = i.next();

        while (i.hasNext() && currentEntry.getValue() < numCases) {
            currentEntry = i.next();
        }

        LocalDate casesAchievedDate = currentEntry.getKey();

        return casesAchievedDate;
    }

    public int numCasesReached(LocalDate initialDate, int numCases) {
        if (initialDate == null || dateCasesReached(numCases) == null) {
            return 0;
        }
        return (int) ChronoUnit.DAYS.between(initialDate, dateCasesReached(numCases));
    }

    public boolean hasMoreSmokersThan(Float percentage) {
        try {
            return percentage <= totalSmokers.lastEntry().getValue();
        } catch (NullPointerException e) {
            return false;
        }
    }

    public LocalDate lastDateOfMonth(Integer month, Integer year) {
        try {
            return caseLists.lastDateOfMonth(month, year);
        } catch (NullPointerException e) {
            return null;
        }
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

    @Override
    public String toString() {
        return this.location;
    }
}

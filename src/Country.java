import javafx.util.Pair;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
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

    public int getTotalCases(LocalDate d) {
        return totalCases.get(d);
    }

    public float getTotalSmokers(LocalDate d) {
        return totalSmokers.get(d);
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
     * Calcula a data em que o país atingiu um dado número de casos positivos.
     *
     * @param numCases Número de casos positivos a atingir
     * @return Data em que o país atingiu o dado número de casos positivos.
     */

    public LocalDate dateUntilXCases(int numCases) {
        Iterator<Map.Entry<LocalDate, Integer>> i = totalCases.entrySet().iterator();

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

    public Map<Integer, Integer> totalCasesPerMonth(Map<Integer, Integer> totalCasesMonth) {
        Map.Entry<LocalDate, Integer> firstDay = totalCases.entrySet().iterator().next();

        LocalDate newDate = firstDay.getKey();

        while (totalCases.get(newDate) != null) {

            int year = newDate.getYear();
            int month = newDate.getMonthValue();

            LocalDate lastDateOfThisMonth = lastDateOfMonth(month, year);

            if (totalCases.get(lastDateOfThisMonth) == null) {
                while (totalCases.get(lastDateOfThisMonth) == null) {
                    lastDateOfThisMonth = lastDateOfThisMonth.minusDays(1);
                    continue;
                }
            }

            int casesByEndMonth = totalCases.get(lastDateOfThisMonth);

            int monthCases = casesByEndMonth - totalCases.get(newDate) + newCases.get(newDate);

            newDate = lastDateOfThisMonth.plusDays(1);

            if (totalCasesMonth.get(month) == null) {
                totalCasesMonth.put(month, monthCases);
            } else {
                totalCasesMonth.put(month, totalCasesMonth.get(month) + monthCases);
            }
        }
        return totalCasesMonth;
    }

    public Map<Integer, Integer> totalDeathsPerMonth(Map<Integer, Integer> totalDeathsMonth) {
        Map.Entry<LocalDate, Integer> firstDay = totalDeaths.entrySet().iterator().next();

        LocalDate newDate = firstDay.getKey();

        while (totalDeaths.get(newDate) != null) {

            int year = newDate.getYear();
            int month = newDate.getMonthValue();

            LocalDate lastDateOfThisMonth = lastDateOfMonth(month, year);

            if (totalDeaths.get(lastDateOfThisMonth) == null) {
                while (totalDeaths.get(lastDateOfThisMonth) == null) {
                    lastDateOfThisMonth = lastDateOfThisMonth.minusDays(1);
                    continue;
                }
            }

            int deathsByEndMonth = totalDeaths.get(lastDateOfThisMonth);

            int monthDeaths = deathsByEndMonth - totalDeaths.get(newDate) + newDeaths.get(newDate);

            newDate = lastDateOfThisMonth.plusDays(1);

            if (totalDeathsMonth.get(month) == null) {
                totalDeathsMonth.put(month, monthDeaths);
            } else {
                totalDeathsMonth.put(month, totalDeathsMonth.get(month) + monthDeaths);
            }
        }
        return totalDeathsMonth;
    }

    public LocalDate lastDateOfMonth(Integer month, Integer year) {
        int lastDayOfMonth = YearMonth.of(year, month).lengthOfMonth();

        return LocalDate.of(year, month, lastDayOfMonth);
    }

    public Boolean hasMoreSmokers(Float percentage) {
        if (percentage > 70.0) {
            return true;
        }

        return false;
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

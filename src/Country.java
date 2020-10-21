import sun.reflect.generics.tree.Tree;

import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

public class Country {

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

    public void addData(Case dailyCases, Death dailyDeaths, Test dailyTests, Smoker dailySmokers) {
        this.newCases = dailyCases.getNewCases();
        this.totalCases = dailyCases.getTotalCases();
        this.newDeaths = dailyDeaths.getNewDeaths();
        this.totalDeaths = dailyDeaths.getTotalDeaths();
        this.newTests = dailyTests.getNewTests();
        this.totalTests = dailyTests.getTotalTests();
        this.totalSmokers = dailySmokers.getTotalSmokers();
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

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

public class Continent {
    private String name;
    private Map<String, Country> countries = new TreeMap<>();

    public Continent(String name) {
        this.name = name;
        World.addContinent(this);
    }

    public String getName() {
        return this.name;
    }

    public Map<String, Country> getCountries() {
        return new TreeMap<>(countries);
    }

    public Set<String> getCountryCodes() {
        return countries.keySet();
    }

    public void addCountry(Country c) {
        countries.put(c.getIsoCode(), c);
        c.setContinent(this);
    }

    public Map<Integer, Integer> totalDeathsPerMonth() {
        Map<Integer, Integer> mapAux = new HashMap<>();

        for (Country c : countries.values()) {
            c.totalDeathsPerMonth(mapAux);
        }

        return mapAux;
    }

    public Map<Integer, Integer> totalCasesPerMonth() {
        Map<Integer, Integer> mapAux = new HashMap<>();

        for (Country c : countries.values()) {
            c.totalCasesPerMonth(mapAux);
        }

        return mapAux;
    }

    public void newCasesPerMonth(int year, int month) {
        int firstDayOfMonth = 1;
        int lastDayOfMonth = YearMonth.of(year, month).lengthOfMonth();
        LocalDate daily = LocalDate.of(year, month, firstDayOfMonth);

        Map<Integer, Country> newCasesPerMonth = new TreeMap<>(Collections.reverseOrder());

        for (int day = firstDayOfMonth; day <= lastDayOfMonth; day++) {
            for (Country c : countries.values()) {
                newCasesPerMonth.put(c.getTotalCases(LocalDate.of(year, month, day)), c);
            }
            System.out.printf("Dia %2d -->    ", day);
            for (Map.Entry entry : newCasesPerMonth.entrySet()) {
                System.out.printf("%s (%d)\n", entry.getValue(), entry.getKey());
            }
            newCasesPerMonth.clear();
        }
    }

    /*public Map<Integer, String> getDeathsPerSmokerPercentage(Float percentage) {

        Map<Integer, String> mapAux = new HashMap<>();

        for (Country country : countries.values()) {
            if (country.hasMoreSmokers(percentage) == true) {

                LocalDate date;

                int totalCasesLastDay = country.getTotalCases(date);

                mapAux.put(totalCasesLastDay, country.getLocation());
            }
        }

        return mapAux;
    }*/

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
        Continent c = (Continent) o;
        // field comparison
        return c.name.equalsIgnoreCase(this.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}

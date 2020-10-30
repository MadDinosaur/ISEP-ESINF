import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

/**
 * Classe que modela um Continente e agrega um conjunto de Países.
 */
public class Continent {
    /**
     * O nome do Continente.
     */
    private String name;
    /**
     * Mapa de países pertencentes ao Continente ordenados por ordem alfabética do seu código ISO.
     */
    private Map<String, Country> countries = new TreeMap<>();

    /**
     * Construtor da classe Continent.
     *
     * @param name O nome do Continente.
     */
    public Continent(String name) {
        this.name = name;
        World.addContinent(this);
    }

    //---------------- Getters ----------------

    /**
     * @return O nome do Continente.
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return Mapa de países pertencentes ao Continente.
     */
    public Map<String, Country> getCountries() {
        return new TreeMap<>(countries);
    }

    /**
     * @return Mapa dos códigos ISO dos países pertencentes ao Continente.
     */
    public Set<String> getCountryCodes() {
        return countries.keySet();
    }

    //---------------- Public update methods ----------------

    /**
     * Adiciona um País ao Continente.
     *
     * @param c O país, em formato Country.
     */
    public void addCountry(Country c) {
        countries.put(c.getIsoCode(), c);
        c.setContinent(this);
    }
    //---------------- Public statistical methods ----------------

    /**
     * @return Mapa dos novos casos positivos do Continente ordenados por mês.
     */
    public Map<Integer, Integer> totalCasesPerMonth() {
        Map<Integer, Integer> totalCasesPerMonth = new HashMap<>();
        for (Country c : countries.values()) {
            for (int month : c.getMonthlyCases().keySet()) {
                if (totalCasesPerMonth.containsKey(month)) {
                    totalCasesPerMonth.put(month, totalCasesPerMonth.get(month) + c.getMonthlyCases(month));
                } else {
                    totalCasesPerMonth.put(month, c.getMonthlyCases(month));
                }
            }
        }
        return totalCasesPerMonth;
    }

    /**
     * @return Mapa das mortes no Continente ordenadas por mês.
     */
    public Map<Integer, Integer> totalDeathsPerMonth() {
        Map<Integer, Integer> totalDeathsPerMonth = new HashMap<>();
        for (Country c : countries.values()) {
            for (int month : c.getMonthlyDeaths().keySet()) {
                if (totalDeathsPerMonth.containsKey(month)) {
                    totalDeathsPerMonth.put(month, totalDeathsPerMonth.get(month) + c.getMonthlyDeaths(month));
                } else {
                    totalDeathsPerMonth.put(month, c.getMonthlyDeaths(month));
                }
            }
        }
        return totalDeathsPerMonth;
    }

    /**
     * @param currentDate O dia, no formato LocalDate.
     * @return Mapa de Countries ordenados de forma crescente pelo número de novos casos registados na data.
     */
    public ArrayList<Country> newCasesPerDay(LocalDate currentDate) {
        ArrayList<Country> newCasesPerDay = new ArrayList<Country>();

        Comparator<Country> byDailyCases = new Comparator<Country>() {
            @Override
            public int compare(Country o1, Country o2) {
                return -Integer.compare(o1.getNewCases(currentDate), o2.getNewCases(currentDate));
            }
        };

        if (currentDate == null) {
            return newCasesPerDay;
        }

        int month = currentDate.getMonthValue();
        int year = currentDate.getYear();
        LocalDate lastOfAllDates = currentDate;

        boolean isInvalidDate = true;

        for (Country c : countries.values()) {
            if (c.lastDateOfMonth(month, year).compareTo(lastOfAllDates) >= 0) {
                lastOfAllDates = c.lastDateOfMonth(month, year);
                isInvalidDate = false;
            }
            newCasesPerDay.add(c);
        }

        if (isInvalidDate) {
            return null;
        }

        Collections.sort(newCasesPerDay, byDailyCases);
        return newCasesPerDay;
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
        Continent c = (Continent) o;
        // field comparison
        return c.name.equalsIgnoreCase(this.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}

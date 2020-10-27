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

    //---------------- Getters ----------------
    public String getName() {
        return this.name;
    }

    public Map<String, Country> getCountries() {
        return new TreeMap<>(countries);
    }

    public Set<String> getCountryCodes() {
        return countries.keySet();
    }

    //---------------- Public update methods ----------------
    public void addCountry(Country c) {
        countries.put(c.getIsoCode(), c);
        c.setContinent(this);
    }

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

    //---------------- Public statistical methods ----------------
    public Map<Integer, Country> newCasesPerDay(LocalDate currentDate) {
        Map<Integer, Country> newCasesPerDay = new TreeMap<>(Collections.reverseOrder());

        int month = currentDate.getMonthValue();
        int year = currentDate.getYear();

        for (Country c : countries.values()) {
            if (currentDate.equals(c.lastDateOfMonth(month, year))) {
                return null;
            }
            newCasesPerDay.put(c.getNewCases(currentDate), c);
        }
        return newCasesPerDay;
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

//---------------- Override methods ----------------
@Override
public boolean equals(Object o){
        // self check
        if(this==o)
        return true;
        // null check
        if(o==null)
        return false;
        // type check and cast
        if(getClass()!=o.getClass())
        return false;
        Continent c=(Continent)o;
        // field comparison
        return c.name.equalsIgnoreCase(this.name);
        }

@Override
public int hashCode(){
        return name.hashCode();
        }
        }

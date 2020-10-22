import sun.reflect.generics.tree.Tree;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Death {

    private Map<LocalDate, Integer> totalDeaths;
    private Map<LocalDate, Integer> newDeaths;

    public Death() {
        totalDeaths = new TreeMap<>();
        newDeaths = new TreeMap<>();
    }

    public void addDeath(String newDeaths, String totalDeaths, LocalDate date) {

        int newDeathsInt;
        int totalDeathsInt;

        if (newDeaths.equalsIgnoreCase("NA")) {
            newDeathsInt = 0;
        } else {
            newDeathsInt = Integer.parseInt(newDeaths);
        }

        if (totalDeaths.equalsIgnoreCase("NA")) {
            totalDeathsInt = 0;
        } else {
            totalDeathsInt = Integer.parseInt(totalDeaths);
        }

        this.newDeaths.put(date, newDeathsInt);
        this.totalDeaths.put(date, totalDeathsInt);
    }

    public Map<Integer,Integer> totalDeathsPerMonth(LocalDate date)
    {
        Map.Entry<LocalDate,Integer> firstDay = totalDeaths.entrySet().iterator().next();

        Map<Integer,Integer> totalDeathsMonth= new HashMap<>();

        LocalDate newDate = firstDay.getKey();

        while(totalDeaths.get(newDate) != null) {

            int year = newDate.getYear();
            int month = newDate.getMonth().getValue();

            int lastDayMonth = YearMonth.of(year,month).lengthOfMonth();

            LocalDate lastDateOfMonth = LocalDate.of(year,month,lastDayMonth);

            if(totalDeaths.get(lastDateOfMonth) == null)
            {
                break;
            }

            int deathsByEndMonth = totalDeaths.get(lastDateOfMonth);

            int monthDeaths = deathsByEndMonth - totalDeaths.get(newDate);

            newDate = lastDateOfMonth.plusDays(1);

            totalDeathsMonth.put(month,monthDeaths);
        }
        return totalDeathsMonth;
    }

    public Map<LocalDate, Integer> getTotalDeaths() {
        return new TreeMap<>(totalDeaths);
    }

    public Map<LocalDate, Integer> getNewDeaths() {
        return new TreeMap<>(newDeaths);
    }
}

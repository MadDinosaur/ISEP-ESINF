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
            if (this.totalDeaths.get(date.minusDays(1)) != null) {
                totalDeathsInt = this.totalDeaths.get(date.minusDays(1));
            }
        } else {
            totalDeathsInt = Integer.parseInt(totalDeaths);
        }

        this.newDeaths.put(date, newDeathsInt);
        this.totalDeaths.put(date, totalDeathsInt);
    }

    public Map<LocalDate, Integer> getTotalDeaths() {
        return new TreeMap<>(totalDeaths);
    }

    public Map<LocalDate, Integer> getNewDeaths() {
        return new TreeMap<>(newDeaths);
    }
}

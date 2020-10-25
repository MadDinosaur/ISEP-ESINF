import sun.reflect.generics.tree.Tree;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Death {

    private Map<LocalDate, Integer> totalDeaths;
    private Map<LocalDate, Integer> newDeaths;
    private Map<Integer, Integer> monthlyDeaths;
    private LocalDate latestDate;

    public Death() {
        totalDeaths = new TreeMap<>();
        newDeaths = new TreeMap<>();
        monthlyDeaths = new HashMap<>();
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

        if (latestDate == null || date.compareTo(latestDate) < 0) {
            this.latestDate = date;
        }
    }

    public Map<LocalDate, Integer> getTotalDeaths() {
        return new TreeMap<>(totalDeaths);
    }

    public Map<LocalDate, Integer> getNewDeaths() {
        return new TreeMap<>(newDeaths);
    }

    public Map<Integer, Integer> getMonthlyDeaths() {
        if (monthlyDeaths.isEmpty()) {
            generateMonthlyDeaths();
        }
        return monthlyDeaths;
    }

    public LocalDate lastDateOfMonth(Integer month, Integer year) {
        int lastDayOfMonth = YearMonth.of(year, month).lengthOfMonth();

        LocalDate lastDateOfMonth = LocalDate.of(year, month, lastDayOfMonth);

        while (totalDeaths.get(lastDateOfMonth) == null && lastDateOfMonth != latestDate) {
            lastDateOfMonth = lastDateOfMonth.minusDays(1);
            continue;
        }

        return lastDateOfMonth;
    }

    private void generateMonthlyDeaths() {
        LocalDate startOfMonth = totalDeaths.keySet().iterator().next();
        LocalDate endOfMonth = lastDateOfMonth(startOfMonth.getMonthValue(), startOfMonth.getYear());
        while (totalDeaths.get(startOfMonth) != null) {

            int deathsByEndOfMonth = totalDeaths.get(endOfMonth);

            int deathsPerMonth = deathsByEndOfMonth - totalDeaths.get(startOfMonth) + newDeaths.get(startOfMonth);

            startOfMonth = endOfMonth.plusDays(1);
            endOfMonth = lastDateOfMonth(startOfMonth.getMonthValue(), startOfMonth.getYear());

            monthlyDeaths.put(startOfMonth.getMonthValue(), deathsPerMonth);
        }
    }
}

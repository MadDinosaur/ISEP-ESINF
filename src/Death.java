import sun.reflect.generics.tree.Tree;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;

public class Death {

    private TreeMap<LocalDate, Integer> totalDeaths;
    private TreeMap<LocalDate, Integer> newDeaths;
    private Map<Integer, Integer> monthlyDeaths;

    public Death() {
        totalDeaths = new TreeMap<>();
        newDeaths = new TreeMap<>();
        monthlyDeaths = new HashMap<>();
    }

    //---------------- Getters ----------------
    public Map<LocalDate, Integer> getTotalDeaths() {
        return totalDeaths;
    }

    public Map<LocalDate, Integer> getNewDeaths() {
        return newDeaths;
    }

    public Map<Integer, Integer> getMonthlyDeaths() {
        if (monthlyDeaths.isEmpty()) {
            generateMonthlyDeaths();
        }
        return monthlyDeaths;
    }

    public int getLatestDeathTotal() {
        return totalDeaths.lastEntry().getValue();
    }

    public int getLatestDeath() {
        return newDeaths.lastEntry().getValue();
    }

    public LocalDate getLatestDate() {
        try {
            return this.totalDeaths.lastKey();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    public LocalDate getOldestDate() {
        return this.totalDeaths.firstKey();
    }

    //---------------- Public update methods ----------------
    public void addDeath(String newCases, String totalCases, LocalDate date) {
        int newCasesInt;
        int totalCasesInt;

        if (newCases.equalsIgnoreCase("NA")) {
            newCasesInt = 0;
        } else {
            newCasesInt = Integer.parseInt(newCases);
        }

        if (totalCases.equalsIgnoreCase("NA")) {
            totalCasesInt = 0;
        } else {
            totalCasesInt = Integer.parseInt(totalCases);
        }

        //Preenche intervalos "vazios", sem datas registadas
        while (getLatestDate() != null && date.compareTo(getLatestDate().plusDays(1)) > 0) {
            this.newDeaths.put(getLatestDate().plusDays(1), 0);
            this.totalDeaths.put(getLatestDate().plusDays(1), getLatestDeathTotal());
        }

        this.newDeaths.put(date, newCasesInt);
        this.totalDeaths.put(date, totalCasesInt);
    }

    //---------------- Private methods ----------------
    private void generateMonthlyDeaths() {
        LocalDate currentDate = newDeaths.firstKey();
        LocalDate endOfMonthDate = lastDateOfMonth(currentDate.getMonthValue(), currentDate.getYear());
        int casesPerMonth = 0;
        while (newDeaths.get(currentDate) != null) {
            while (currentDate.compareTo(endOfMonthDate) <= 0) {
                casesPerMonth += newDeaths.get(currentDate);
                currentDate = currentDate.plusDays(1);
            }

            monthlyDeaths.put(endOfMonthDate.getMonthValue(), casesPerMonth);

            endOfMonthDate = lastDateOfMonth(currentDate.getMonthValue(), currentDate.getYear());
            casesPerMonth = 0;
        }
    }

    private LocalDate lastDateOfMonth(Integer month, Integer year) {
        int lastDayOfMonth = YearMonth.of(year, month).lengthOfMonth();

        LocalDate lastDateOfMonth = LocalDate.of(year, month, lastDayOfMonth);

        if (totalDeaths.get(lastDateOfMonth) == null) {
            return getLatestDate();
        }

        return lastDateOfMonth;
    }
}

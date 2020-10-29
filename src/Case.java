import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;

public class Case {

    private TreeMap<LocalDate, Integer> totalCases;
    private TreeMap<LocalDate, Integer> newCases;
    private Map<Integer, Integer> monthlyCases;

    public Case() {
        totalCases = new TreeMap<>();
        newCases = new TreeMap<>();
        monthlyCases = new HashMap<>();
    }

    //---------------- Getters ----------------
    public Map<LocalDate, Integer> getTotalCases() {
        return totalCases;
    }

    public Map<LocalDate, Integer> getNewCases() {
        return newCases;
    }

    public Map<Integer, Integer> getMonthlyCases() {
        if (monthlyCases.isEmpty()) {
            generateMonthlyCases();
        }
        return monthlyCases;
    }

    public int getLatestCaseTotal() {
        try {
            return totalCases.lastEntry().getValue();
        } catch (NullPointerException e) {
            return 0;
        }
    }

    public int getLatestCase() {
        return newCases.lastEntry().getValue();
    }

    public LocalDate getLatestDate() {
        try {
            return this.totalCases.lastKey();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    public LocalDate getOldestDate() {
        try {
            return this.totalCases.firstKey();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    //---------------- Public update methods ----------------
    public void addCase(String newCases, String totalCases, LocalDate date) {
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
            this.newCases.put(getLatestDate().plusDays(1), 0);
            this.totalCases.put(getLatestDate().plusDays(1), getLatestCaseTotal());
        }

        this.newCases.put(date, newCasesInt);
        this.totalCases.put(date, totalCasesInt);
    }

    public LocalDate lastDateOfMonth(Integer month, Integer year) {
        int lastDayOfMonth = YearMonth.of(year, month).lengthOfMonth();

        LocalDate lastDateOfMonth = LocalDate.of(year, month, lastDayOfMonth);

        if (totalCases.get(lastDateOfMonth) == null) {
            return getLatestDate();
        }

        return lastDateOfMonth;
    }

    //---------------- Private methods ----------------
    private void generateMonthlyCases() {
        LocalDate currentDate = newCases.firstKey();
        LocalDate endOfMonthDate = lastDateOfMonth(currentDate.getMonthValue(), currentDate.getYear());
        int casesPerMonth = 0;
        while (newCases.get(currentDate) != null) {
            while (currentDate.compareTo(endOfMonthDate) <= 0) {
                casesPerMonth += newCases.get(currentDate);
                currentDate = currentDate.plusDays(1);
            }

            monthlyCases.put(endOfMonthDate.getMonthValue(), casesPerMonth);

            endOfMonthDate = lastDateOfMonth(currentDate.getMonthValue(), currentDate.getYear());
            casesPerMonth = 0;
        }
    }
}


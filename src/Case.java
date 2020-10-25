import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Case {

    private Map<LocalDate, Integer> totalCases;
    private Map<LocalDate, Integer> newCases;
    private Map<Integer, Integer> monthlyCases;
    private LocalDate latestDate;

    public Case() {
        totalCases = new TreeMap<>();
        newCases = new TreeMap<>();
        monthlyCases = new HashMap<>();
    }

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
        if (latestDate != null && date.compareTo(latestDate.plusDays(1)) > 0) {
            for (int numDays = 1; numDays < (int) ChronoUnit.DAYS.between(latestDate, date); numDays++) {
                this.newCases.put(latestDate.plusDays(numDays), 0);
                this.totalCases.put(latestDate.plusDays(numDays), this.totalCases.get(latestDate));
            }
        }

        this.newCases.put(date, newCasesInt);
        this.totalCases.put(date, totalCasesInt);

        if (latestDate == null || date.compareTo(latestDate) > 0) {
            this.latestDate = date;
        }
    }

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

    public LocalDate lastDateOfMonth(Integer month, Integer year) {
        int lastDayOfMonth = YearMonth.of(year, month).lengthOfMonth();

        LocalDate lastDateOfMonth = LocalDate.of(year, month, lastDayOfMonth);

        while (totalCases.get(lastDateOfMonth) == null && lastDateOfMonth != latestDate) {
            lastDateOfMonth = lastDateOfMonth.minusDays(1);
            continue;
        }

        return lastDateOfMonth;
    }

    private void generateMonthlyCases() {
        LocalDate currentDate = newCases.keySet().iterator().next();
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


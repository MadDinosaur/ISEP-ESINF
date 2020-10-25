import java.time.LocalDate;
import java.time.YearMonth;
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

        this.newCases.put(date, newCasesInt);
        this.totalCases.put(date, totalCasesInt);

        if (latestDate == null || date.compareTo(latestDate) < 0) {
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
        LocalDate startOfMonth = totalCases.keySet().iterator().next();
        LocalDate endOfMonth = lastDateOfMonth(startOfMonth.getMonthValue(), startOfMonth.getYear());
        while (totalCases.get(startOfMonth) != null) {

            int casesByEndOfMonth = totalCases.get(endOfMonth);

            int casesPerMonth = casesByEndOfMonth - totalCases.get(startOfMonth) + newCases.get(startOfMonth);

            startOfMonth = endOfMonth.plusDays(1);

            monthlyCases.put(startOfMonth.getMonthValue(), casesPerMonth);

            /*if (totalCasesMonth.get(month) == null) {
                totalCasesMonth.put(month, monthCases);
            } else {
                totalCasesMonth.put(month, totalCasesMonth.get(month) + monthCases);
            }*/
        }
    }
}

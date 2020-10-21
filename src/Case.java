import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

public class Case {

    private Map<LocalDate, Integer> totalCases;
    private Map<LocalDate, Integer> newCases;

    public Case() {
        totalCases = new TreeMap<>();
        newCases = new TreeMap<>();
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

        this.getNewCases().put(date, newCasesInt);
        this.getTotalCases().put(date, totalCasesInt);
    }


    public Map<LocalDate, Integer> getTotalCases() {
        return new TreeMap<>(totalCases);
    }

    public Map<LocalDate, Integer> getNewCases() {
        return new TreeMap<>(newCases);
    }
}

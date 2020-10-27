import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

public class Test {

    private Map<LocalDate, Integer> totalTests;
    private Map<LocalDate, Integer> newTests;

    public Test() {
        totalTests = new TreeMap();
        newTests = new TreeMap();
    }

    public void addTest(String newTests, String totalTests, LocalDate date) {

        int newTestsInt;
        int totalTestsInt;

        if (newTests.equalsIgnoreCase("NA")) {
            newTestsInt = 0;
        } else {
            newTestsInt = Integer.parseInt(newTests);
        }

        if (totalTests.equalsIgnoreCase("NA")) {
            totalTestsInt = 0;
        } else {
            totalTestsInt = Integer.parseInt(totalTests);
        }

        this.newTests.put(date, newTestsInt);
        this.totalTests.put(date, totalTestsInt);

    }

    public TreeMap<LocalDate, Integer> getTotalTests() {
        return new TreeMap<>(totalTests);
    }

    public TreeMap<LocalDate, Integer> getNewTests() {
        return new TreeMap<>(newTests);
    }
}

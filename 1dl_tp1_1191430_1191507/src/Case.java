import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;

/**
 * Classe que contém informação acerca dos casos positivos de um país.
 */
public class Case {
    /**
     * Mapa de totais de casos positivos, ordenados por data.
     */
    private TreeMap<LocalDate, Integer> totalCases;
    /**
     * Mapa de novos casos positivos, ordenados por data.
     */
    private TreeMap<LocalDate, Integer> newCases;
    /**
     * Mapa de novos casos positivos, ordenados por mês.
     */
    private Map<Integer, Integer> monthlyCases;

    /**
     * Construtor da classe Case.
     */
    public Case() {
        totalCases = new TreeMap<>();
        newCases = new TreeMap<>();
        monthlyCases = new HashMap<>();
    }

    //---------------- Getters ----------------

    /**
     * @return Mapa de totais de casos positivos, ordenados por data.
     */
    public Map<LocalDate, Integer> getTotalCases() {
        return totalCases;
    }

    /**
     * @return Mapa de novos casos positivos, ordenados por data.
     */
    public Map<LocalDate, Integer> getNewCases() {
        return newCases;
    }

    /**
     * @return Mapa de novos casos positivos, ordenados por mês.
     */
    public Map<Integer, Integer> getMonthlyCases() {
        if (monthlyCases.isEmpty()) {
            generateMonthlyCases();
        }
        return monthlyCases;
    }

    /**
     * @return O total de casos positivos na última data em registo.
     */
    public int getLatestCaseTotal() {
        try {
            return totalCases.lastEntry().getValue();
        } catch (NullPointerException e) {
            return 0;
        }
    }

    /**
     * @return O total de novos casos na última data em registo.
     */
    public int getLatestCase() {
        return newCases.lastEntry().getValue();
    }

    /**
     * @return A última data em registo (mais recente).
     */
    public LocalDate getLatestDate() {
        try {
            return this.totalCases.lastKey();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    /**
     * @return A primeira data em registo (mais antiga).
     */
    public LocalDate getOldestDate() {
        try {
            return this.totalCases.firstKey();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    //---------------- Public update methods ----------------

    /**
     * Adiciona informação acerca dos casos positivos numa dada data.
     *
     * @param newCases   O nº de novos casos, formato String.
     * @param totalCases O total de casos, formato String.
     * @param date       A data, formato LocalDate.
     */
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

    /**
     * @param month O número do mês.
     * @param year  O ano.
     * @return A última data do mês e ano indicado em que existe algum registo.
     */
    public LocalDate lastDateOfMonth(Integer month, Integer year) {
        int lastDayOfMonth = YearMonth.of(year, month).lengthOfMonth();

        LocalDate lastDateOfMonth = LocalDate.of(year, month, lastDayOfMonth);

        if (totalCases.get(lastDateOfMonth) == null) {
            return getLatestDate();
        }

        return lastDateOfMonth;
    }

    //---------------- Private methods ----------------

    /**
     * Gera o mapa de novos casos mensais.
     */
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


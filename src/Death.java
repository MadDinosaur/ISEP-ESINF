import sun.reflect.generics.tree.Tree;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;

/**
 * Classe que contém informação acerca das mortes de um país.
 */
public class Death {
    /**
     * Mapa de totais de mortes, ordenadas por data.
     */
    private TreeMap<LocalDate, Integer> totalDeaths;
    /**
     * Mapa de novas mortes, ordenadas por data.
     */
    private TreeMap<LocalDate, Integer> newDeaths;
    /**
     * Mapa de novas mortes, ordenadas por mês.
     */
    private Map<Integer, Integer> monthlyDeaths;

    /**
     * Construtor da classe Death.
     */
    public Death() {
        totalDeaths = new TreeMap<>();
        newDeaths = new TreeMap<>();
        monthlyDeaths = new HashMap<>();
    }

    //---------------- Getters ----------------

    /**
     * @return Mapa de totais de mortes, ordenadas por data.
     */
    public Map<LocalDate, Integer> getTotalDeaths() {
        return totalDeaths;
    }

    /**
     * @return Mapa de novas mortes, ordenadas por data.
     */
    public Map<LocalDate, Integer> getNewDeaths() {
        return newDeaths;
    }

    /**
     * @return Mapa de novas mortes, ordenadas por mês.
     */
    public Map<Integer, Integer> getMonthlyDeaths() {
        if (monthlyDeaths.isEmpty()) {
            generateMonthlyDeaths();
        }
        return monthlyDeaths;
    }

    /**
     * @return O total de mortes na última data em registo.
     */
    public int getLatestDeathTotal() {
        return totalDeaths.lastEntry().getValue();
    }

    /**
     * @return As novas mortes na última data em registo.
     */
    public int getLatestDeath() {
        return newDeaths.lastEntry().getValue();
    }

    /**
     * @return A última data em registo (mais recente).
     */
    public LocalDate getLatestDate() {
        try {
            return this.totalDeaths.lastKey();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    /**
     * @return A primeira data em registo (mais antiga).
     */
    public LocalDate getOldestDate() {
        return this.totalDeaths.firstKey();
    }

    //---------------- Public update methods ----------------

    /**
     * Adiciona informação acerca das mortes numa dada data.
     *
     * @param newDeaths   O nº de novas mortes, formato String.
     * @param totalDeaths O total de mortes, formato String.
     * @param date        A data, formato LocalDate.
     */
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

        //Preenche intervalos "vazios", sem datas registadas
        while (getLatestDate() != null && date.compareTo(getLatestDate().plusDays(1)) > 0) {
            this.newDeaths.put(getLatestDate().plusDays(1), 0);
            this.totalDeaths.put(getLatestDate().plusDays(1), getLatestDeathTotal());
        }

        this.newDeaths.put(date, newDeathsInt);
        this.totalDeaths.put(date, totalDeathsInt);
    }

    //---------------- Private methods ----------------

    /**
     * Gera o mapa de mortes mensais.
     */
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

    /**
     * @param month O número do mês.
     * @param year  O ano.
     * @return A última data do mês e ano indicado em que existe algum registo.
     */
    private LocalDate lastDateOfMonth(Integer month, Integer year) {
        int lastDayOfMonth = YearMonth.of(year, month).lengthOfMonth();

        LocalDate lastDateOfMonth = LocalDate.of(year, month, lastDayOfMonth);

        if (totalDeaths.get(lastDateOfMonth) == null) {
            return getLatestDate();
        }

        return lastDateOfMonth;
    }
}

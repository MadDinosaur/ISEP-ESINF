import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Classe que representa um país
 */
public class Country {
    /**
     * Continente em que se insere o país
     */
    private Continent continent;
    /**
     * Código ISO do país
     */
    private String isoCode;
    /**
     * Nome do país
     */
    private String location;
    /**
     * Mapa de casos positivos ordenados por data - novos e totais
     */
    private Case caseLists;
    /**
     * Mapa de mortes ordenados por data - novas e totais
     */
    private Death deathLists;
    /**
     * Mapa de testes ordenados por data - novos e totais
     */
    private Tests testLists;
    /**
     * Mapa de percentagens de fumadores ordenados por data
     */
    private TreeMap<LocalDate, Float> totalSmokers;
    /**
     * Mapa de indicadores demográficos ordenados por data
     */
    private DemographicIndicators indicatorLists;
    /**
     * Mapa de fatores de riscos ordenados por data
     */
    private RiskFactors riskFactorLists;

    /**
     * Construtor da classe Country
     *
     * @param isoCode  O código ISO do país
     * @param location O nome do país
     */
    public Country(String isoCode, String location) {
        this.isoCode = isoCode;
        this.location = location;
        this.caseLists = new Case();
        this.deathLists = new Death();
        this.totalSmokers = new TreeMap<>();
    }

    //---------------- Getters ----------------

    /**
     * @return O código ISO.
     */
    public String getIsoCode() {
        return isoCode;
    }

    /**
     * @return O nome do país.
     */
    public String getLocation() {
        return location;
    }

    /**
     * @return O continente a que pertence o país.
     */
    public Continent getContinent() {
        return continent;
    }

    /**
     * @return Mapa de totais diários de casos, indexados por data.
     */
    public Map<LocalDate, Integer> getTotalCases() {
        return caseLists.getTotalCases();
    }

    /**
     * @param d Uma data, no formato LocalDate.
     * @return Número de total de casos na data especificada.
     */
    public int getTotalCases(LocalDate d) {
        return caseLists.getTotalCases().get(d);
    }

    /**
     * @return Mapa de novos casos diários, indexados por data.
     */
    public Map<LocalDate, Integer> getNewCases() {
        return caseLists.getNewCases();
    }

    /**
     * @param d Uma data, no formato LocalDate.
     * @return Número de novos casos na data especificada.
     */
    public int getNewCases(LocalDate d) {
        try {
            return caseLists.getNewCases().get(d);
        } catch (NullPointerException e) {
            return 0;
        }
    }

    /**
     * @return Mapa de novos casos mensais, indexados por número do mês.
     */
    public Map<Integer, Integer> getMonthlyCases() {
        return caseLists.getMonthlyCases();
    }

    /**
     * @param month O número do mês.
     * @return Número de novos casos no mês especificado.
     */
    public int getMonthlyCases(int month) {
        return caseLists.getMonthlyCases().get(month);
    }

    /**
     * @return O total de mortes na última data em registo.
     */
    public int getLatestTotalDeaths() {
        return deathLists.getLatestDeathTotal();
    }

    /**
     * @return Mapa de mortes mensais, indexadas por número do mês.
     */
    public Map<Integer, Integer> getMonthlyDeaths() {
        return deathLists.getMonthlyDeaths();
    }

    /**
     * @param month O número do mês.
     * @return Número de mortes no mês especificado.
     */
    public int getMonthlyDeaths(int month) {
        return deathLists.getMonthlyDeaths().get(month);
    }

    /**
     * @return A percentagem de fumadores na última data em registo.
     */
    public float getSmokerPercentage() {
        return totalSmokers.lastEntry().getValue();
    }

    /**
     * @return A última data em registo.
     */
    public LocalDate oldestEntry() {
        try {
            return caseLists.getOldestDate();
        } catch (NullPointerException e) {
            return null;
        }
    }

    //---------------- Setters ----------------

    /**
     * @param continent O continente a que pertence o país, no formato Continent.
     */
    public void setContinent(Continent continent) {
        this.continent = continent;
    }

    //---------------- Public update methods ----------------

    /**
     * Carrega informação demográfica e dados acerca do Covid19.
     *
     * @param dailyCases      Mapa de casos positivos, formato Case.
     * @param dailyDeaths     Mapa de mortes, formato Death.
     * @param dailyTests      Mapa de testes, formato Tests.
     * @param dailySmokers    Mapa de fumadores, formato Smoker.
     * @param indicatorLists  Mapa de indicadores demográficos, formato DemographicIndicator.
     * @param riskFactorLists Mapa de fatores de risco, formato RiskFactors.
     */
    public void addData(Case dailyCases, Death dailyDeaths, Tests dailyTests, Smoker dailySmokers,
                        DemographicIndicators indicatorLists, RiskFactors riskFactorLists) {
        this.caseLists = dailyCases;
        this.deathLists = dailyDeaths;
        this.testLists = dailyTests;
        this.totalSmokers = dailySmokers.getTotalSmokers();
        this.indicatorLists = indicatorLists;
        this.riskFactorLists = riskFactorLists;
    }

    //---------------- Public statistical methods ----------------

    /**
     * Calcula a data em que o país atingiu um dado número de casos positivos.
     *
     * @param numCases Número de casos positivos a atingir.
     * @return Data em que o país atingiu o dado número de casos positivos.
     */

    public LocalDate dateCasesReached(int numCases) {
        //Check para verificar se o mais recente total de casos ultrapassa numCases
        if (caseLists.getLatestCaseTotal() < numCases) {
            return null;
        }

        Iterator<Map.Entry<LocalDate, Integer>> i = caseLists.getTotalCases().entrySet().iterator();

        Map.Entry<LocalDate, Integer> currentEntry = i.next();

        while (i.hasNext() && currentEntry.getValue() < numCases) {
            currentEntry = i.next();
        }

        LocalDate casesAchievedDate = currentEntry.getKey();

        return casesAchievedDate;
    }

    /**
     * @param initialDate Primeira data em registo.
     * @param numCases    Número de casos positivos a atingir.
     * @return Número de casos positivos na data em que o país ultrapassou o valor em numCases.
     */
    public int numCasesReached(LocalDate initialDate, int numCases) {
        if (initialDate == null || dateCasesReached(numCases) == null) {
            return 0;
        }
        return (int) ChronoUnit.DAYS.between(initialDate, dateCasesReached(numCases));
    }

    /**
     * @param percentage Percentagem de fumadores.
     * @return True se o país tem uma percentagem de fumadores superior, false caso contrário.
     */
    public boolean hasMoreSmokersThan(Float percentage) {
        try {
            return percentage <= totalSmokers.lastEntry().getValue();
        } catch (NullPointerException e) {
            return false;
        }
    }

    /**
     * @param month Número do mês.
     * @param year  Ano.
     * @return A última data do mês e ano indicados em que existem registos.
     */
    public LocalDate lastDateOfMonth(Integer month, Integer year) {
        try {
            return caseLists.lastDateOfMonth(month, year);
        } catch (NullPointerException e) {
            return null;
        }
    }

    //---------------- Override methods ----------------
    @Override
    public boolean equals(Object o) {
        // self check
        if (this == o)
            return true;
        // null check
        if (o == null)
            return false;
        // type check and cast
        if (getClass() != o.getClass())
            return false;
        Country c = (Country) o;
        // field comparison
        return c.getIsoCode().equalsIgnoreCase(this.getIsoCode());
    }

    @Override
    public int hashCode() {
        return getIsoCode().hashCode();
    }

    @Override
    public String toString() {
        return this.location;
    }
}

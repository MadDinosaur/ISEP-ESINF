import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

/**
 * Classe que contém os indicadores demográficos de um país
 */
public class DemographicIndicators {
    /**
     * Mapa de número de habitantes ordenados por data.
     */
    private Map<LocalDate, Integer> population;
    /**
     * Mapa de percentagem de habitantes com idade igual ou superior a 65 anos ordenados por data.
     */
    private Map<LocalDate, Float> agedPeople;
    /**
     * Mapa de número de camas de hospital por 1000 habitantes ordenados por data.
     */
    private Map<LocalDate, Float> hospitalBeds;
    /**
     * Esperança média de vida ordenada por data.
     */
    private Map<LocalDate, Float> lifeExpectancy;

    /**
     * Construtor da classe DemographicIndicators.
     */
    public DemographicIndicators() {
        this.population = new TreeMap<>();
        this.agedPeople = new TreeMap<>();
        this.hospitalBeds = new TreeMap<>();
        this.lifeExpectancy = new TreeMap<>();
    }

    /**
     * Adiciona informação de indicadores demográficos numa dada data.
     *
     * @param population     O nº de habitantes.
     * @param agedPeople     A percentagem de pessoas com 65 ou mais anos.
     * @param hospitalBeds   O nº de camas de hospital por 1000 habitantes.
     * @param lifeExpectancy A esperança média de vida.
     * @param date           A data, no formato LocalDate.
     */
    public void addIndicators(String population, String agedPeople, String hospitalBeds, String lifeExpectancy, LocalDate date) {
        try {
            this.population.put(date, Integer.parseInt(population));
        } catch (NumberFormatException e) {
            this.population.put(date, 0);
        }
        try {
            this.agedPeople.put(date, Float.parseFloat(agedPeople));
        } catch (NumberFormatException e) {
            this.agedPeople.put(date, 0f);
        }
        try {
            this.hospitalBeds.put(date, Float.parseFloat(hospitalBeds));
        } catch (NumberFormatException e) {
            this.hospitalBeds.put(date, 0f);
        }
        try {

            this.lifeExpectancy.put(date, Float.parseFloat(lifeExpectancy));
        } catch (NumberFormatException e) {
            this.lifeExpectancy.put(date, 0f);
        }
    }
}

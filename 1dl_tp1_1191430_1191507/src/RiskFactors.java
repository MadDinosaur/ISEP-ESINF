import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

/**
 * Classe que contém informação acerca dos fatores de riscos da Covid19
 */
public class RiskFactors {
    /**
     * Mapa de taxas de mortalidade cardiovascular ordenadas por data.
     */
    private Map<LocalDate, Float> cardioVascRate;
    /**
     * Mapa de percentagens de habitantes com diabetes ordenadas por data.
     */
    private Map<LocalDate, Float> diabetesPrevalence;

    /**
     * Construtor da classe RiskFactors
     */
    public RiskFactors() {
        this.cardioVascRate = new TreeMap<>();
        this.diabetesPrevalence = new TreeMap<>();
    }

    /**
     * Adiciona informação dos fatores de risco numa dada data.
     *
     * @param cardioVascRate     Taxa de mortalidade cardiovascular.
     * @param diabetesPrevalence Percentagem de habitantes com diabetes.
     * @param date               A data, em formato LocalDate.
     */
    public void addFactors(String cardioVascRate, String diabetesPrevalence, LocalDate date) {
        try {
            this.cardioVascRate.put(date, Float.parseFloat(cardioVascRate));
        } catch (NumberFormatException e) {
            this.cardioVascRate.put(date, 0f);
        }
        try {
            this.diabetesPrevalence.put(date, Float.parseFloat(diabetesPrevalence));
        } catch (NumberFormatException e) {
            this.diabetesPrevalence.put(date, 0f);
        }
    }
}

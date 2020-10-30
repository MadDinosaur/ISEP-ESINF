import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

/**
 * Classe que contém informação acerca dos testes ao Covid19 realizados por um país
 */
public class Tests {
    /**
     * Mapa de novos testes diários ordenados por data.
     */
    private Map<LocalDate, Integer> newTests;
    /**
     * Mapa diária de totais de testes ordenados por data.
     */
    private Map<LocalDate, Integer> totalTests;

    /**
     * Construtor da classe Tests.
     */
    public Tests() {
        this.newTests = new TreeMap<>();
        this.totalTests = new TreeMap<>();
    }

    /**
     * Adiciona informação sobre os testes realizados numa dada data.
     *
     * @param newTests   O nº de novos testes, no formato String.
     * @param totalTests O total de testes, no formato String.
     * @param date       A data, no formato LocalDate.
     */
    public void addTests(String newTests, String totalTests, LocalDate date) {
        this.newTests.put(date, Integer.parseInt(newTests));
        this.totalTests.put(date, Integer.parseInt(totalTests));
    }
}

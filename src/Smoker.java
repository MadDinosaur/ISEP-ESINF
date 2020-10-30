import sun.reflect.generics.tree.Tree;

import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

/**
 * Classe que contém informação acerca da percentagem de fumadores de um país.
 */
public class Smoker {
    /**
     * Mapa de percentagens de fumadores do sexo feminino ordenadas por data.
     */
    Map<LocalDate, Float> femaleSmokers;
    /**
     * Mapa de percentagens de fumadores do sexo masculino ordenados por data.
     */
    Map<LocalDate, Float> maleSmokers;
    /**
     * Mapa de percentagens totais de fumadores ordenados por data.
     */
    private Map<LocalDate, Float> totalSmokers;

    /**
     * Construtor da classe Smoker.
     */
    public Smoker() {
        femaleSmokers = new TreeMap<>();
        maleSmokers = new TreeMap<>();
        totalSmokers = new TreeMap<>();
    }

    //---------------- Getters ----------------

    /**
     * @return Mapa de percentagens totais de fumadores ordenados por data.
     */
    public TreeMap<LocalDate, Float> getTotalSmokers() {
        return new TreeMap<>(totalSmokers);
    }

    //---------------- Public update methods ----------------

    /**
     * Adiciona informação acerca da percentagem de fumadores numa dada data.
     *
     * @param femaleSmokers A % de fumadores do sexo feminino, formato String.
     * @param maleSmokers   A % de fumadores do sexo masculino, formato String.
     * @param date          A data, formato LocalDate.
     */
    public void addSmoker(String femaleSmokers, String maleSmokers, LocalDate date) {

        float femaleSmokersFloat;
        float maleSmokersFloat;
        float totalSmokersFloat;

        if (femaleSmokers.equalsIgnoreCase("NA")) {
            femaleSmokersFloat = 0;
        } else {
            femaleSmokersFloat = Float.parseFloat(femaleSmokers);
        }

        if (maleSmokers.equalsIgnoreCase("NA")) {
            maleSmokersFloat = 0;
        } else {
            maleSmokersFloat = Float.parseFloat(maleSmokers);
        }

        totalSmokersFloat = femaleSmokersFloat + maleSmokersFloat;

        this.femaleSmokers.put(date, femaleSmokersFloat);
        this.maleSmokers.put(date, maleSmokersFloat);
        this.totalSmokers.put(date, totalSmokersFloat);
    }

}

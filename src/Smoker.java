import sun.reflect.generics.tree.Tree;

import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

public class Smoker {
    Map<LocalDate, Float> femaleSmokers;
    Map<LocalDate, Float> maleSmokers;
    private Map<LocalDate, Float> totalSmokers;

    public Smoker() {
        femaleSmokers = new TreeMap<>();
        maleSmokers = new TreeMap<>();
        totalSmokers = new TreeMap<>();
    }

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
        this.getTotalSmokers().put(date, totalSmokersFloat);
    }

    public Map<LocalDate, Float> getTotalSmokers() {
        return new TreeMap<>(totalSmokers);
    }
}

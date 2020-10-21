import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

public class Death {

    Map<LocalDate,Integer> totalDeaths;
    Map<LocalDate,Integer> newDeaths;

    public Death()
    {
        totalDeaths = new TreeMap<>();
        newDeaths = new TreeMap<>();
    }

    public void addDeath(String newDeaths, String totalDeaths, LocalDate date){

        int newDeathsInt;
        int totalDeathsInt;

        if(newDeaths.equalsIgnoreCase("NA"))
        {
            newDeathsInt = 0;
        }

        else
        {
            newDeathsInt = Integer.parseInt(newDeaths);
        }

        if(totalDeaths.equalsIgnoreCase("NA"))
        {
            totalDeathsInt=0;
        }

        else
        {
            totalDeathsInt = Integer.parseInt(totalDeaths);
        }

        this.newDeaths.put(date,newDeathsInt);
        this.totalDeaths.put(date,totalDeathsInt);
    }

}

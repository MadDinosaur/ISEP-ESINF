import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ContinentTest {

    private Country countryPT;
    private Country countrySP;
    private Country countryEN;
    private Continent europa;

    @Before
    public void setUp() throws Exception {

        countryPT = new Country ("PT", "Portugal");
        countrySP = new Country ("ES", "Spain");
        countryEN = new Country("EN", "England");

        Case caseListPT = new Case();
        Death deathListPT = new Death();
        Smoker smokerListPT = new Smoker();

        Case caseListES = new Case();
        Death deathListES = new Death();
        Smoker smokerListES = new Smoker();

        Case caseListEN = new Case();
        Death deathListEN = new Death();
        Smoker smokerListEN = new Smoker();

        caseListPT.addCase("2","2", LocalDate.of(2020, 2, 10));
        caseListES.addCase("3","5", LocalDate.of(2020, 2, 11));
        caseListEN.addCase("4", "9", LocalDate.of(2020, 2,12));

        caseListPT.addCase("2","2", LocalDate.of(2020, 3, 1));
        caseListES.addCase("3","5", LocalDate.of(2020, 3, 2));
        caseListEN.addCase("4","9",LocalDate.of(2020,3,3));

        caseListPT.addCase("2","2", LocalDate.of(2020, 4, 20));
        caseListES.addCase("3","5", LocalDate.of(2020, 4, 21));
        caseListEN.addCase("4","9", LocalDate.of(2020, 4, 22));

        deathListPT.addDeath("2","2", LocalDate.of(2020, 2, 10));
        deathListES.addDeath("3","5", LocalDate.of(2020, 2, 11));
        deathListEN.addDeath("4", "9", LocalDate.of(2020, 2,12));

        deathListPT.addDeath("2","2", LocalDate.of(2020, 3, 1));
        deathListES.addDeath("3","5", LocalDate.of(2020, 3, 2));
        deathListEN.addDeath("4","9",LocalDate.of(2020,3,3));

        deathListPT.addDeath("2","2", LocalDate.of(2020, 4, 20));
        deathListES.addDeath("3","5", LocalDate.of(2020, 4, 21));
        deathListEN.addDeath("4","9", LocalDate.of(2020, 4, 22));

        smokerListPT.addSmoker("20","30", LocalDate.of(2020, 1, 1));
        smokerListES.addSmoker("29","30", LocalDate.of(2020, 1, 1));
        smokerListEN.addSmoker("22","31", LocalDate.of(2020, 1, 1));

        countryPT.addData(caseListPT,deathListPT,smokerListPT);
        countryEN.addData(caseListEN,deathListEN,smokerListEN);
        countrySP.addData(caseListES,deathListES,smokerListES);

        europa = new Continent("Europa");
        europa.addCountry(countryPT);
        europa.addCountry(countrySP);
        europa.addCountry(countryEN);

        World.addContinent(europa);

    }


    @Test
    public void totalCasesPerMonthNoCountries()
    {
        Continent emptyContinent = new Continent("");

        Map<Integer,Integer> result = emptyContinent.totalCasesPerMonth();

        Map<Integer,Integer> expectedResult = new HashMap<>();

        Assert.assertEquals(result,expectedResult);
    }

    @Test
    public void totalCasesPerMonthCorrect()
    {

        Map<Integer,Integer> result = europa.totalCasesPerMonth();

        Map<Integer,Integer> expectedResult = new HashMap<>();

        expectedResult.put(2,9);
        expectedResult.put(3,9);
        expectedResult.put(4,9);

        Assert.assertEquals(expectedResult,result);
    }

    @Test
    public void totalDeathsPerMonthCountries()
    {
        Continent emptyContinent = new Continent("");

        Map<Integer,Integer> result = emptyContinent.totalCasesPerMonth();

        Map<Integer,Integer> expectedResult = new HashMap<>();

        Assert.assertEquals(result,expectedResult);
    }

    @Test
    public void totalDeathsPerMonthCorrect()
    {

        Map<Integer,Integer> result = europa.totalCasesPerMonth();

        Map<Integer,Integer> expectedResult = new HashMap<>();

        expectedResult.put(2,9);
        expectedResult.put(3,9);
        expectedResult.put(4,9);

        Assert.assertEquals(expectedResult,result);
    }

  @Test
    public void newCasesPerDayNoDate()
    {
        ArrayList<Country> result = europa.newCasesPerDay(null);

        ArrayList<Country> expectedResult = new ArrayList<>();

        Assert.assertEquals(result,expectedResult);
    }

    @Test
    public void newCasesPerDayCorrect()
    {
        ArrayList<Country> result = europa.newCasesPerDay(LocalDate.of(2020,3,1));

        ArrayList<Country> expectedResult = new ArrayList<Country>();

        expectedResult.add(countryPT);
        expectedResult.add(countryEN);
        expectedResult.add(countrySP);
        
        Assert.assertEquals(result,expectedResult);
    }

}
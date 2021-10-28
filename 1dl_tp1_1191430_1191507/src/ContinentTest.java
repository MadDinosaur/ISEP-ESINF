import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ContinentTest {
    /**
     * Instância 1 da classe Country para teste.
     * Data: 01/01/2020 | NovosCasos:NA | TotalCasos:NA | NovasMortes:NA | TotalMortes:NA | Fumadores:50%
     * Data: 10/02/2020 | NovosCasos:02 | TotalCasos:02 | NovasMortes:02 | TotalMortes:02 | Fumadores:NA
     * Data: 01/03/2020 | NovosCasos:02 | TotalCasos:04 | NovasMortes:02 | TotalMortes:04 | Fumadores:NA
     * Data: 20/04/2020 | NovosCasos:02 | TotalCasos:06 | NovasMortes:02 | TotalMortes:06 | Fumadores:NA
     */
    private Country countryPT;
    /**
     * Instância 2 da classe Country para teste.
     * Data: 01/01/2020 | NovosCasos:NA | TotalCasos:NA | NovasMortes:NA | TotalMortes:NA | Fumadores:59%
     * Data: 12/02/2020 | NovosCasos:03 | TotalCasos:03 | NovasMortes:03 | TotalMortes:03 | Fumadores:NA
     * Data: 02/03/2020 | NovosCasos:03 | TotalCasos:06 | NovasMortes:03 | TotalMortes:06 | Fumadores:NA
     * Data: 21/04/2020 | NovosCasos:03 | TotalCasos:06 | NovasMortes:03 | TotalMortes:09 | Fumadores:NA
     */
    private Country countrySP;
    /**
     * Instância 3 da classe Country para teste.
     * Data: 01/01/2020 | NovosCasos:NA | TotalCasos:NA | NovasMortes:NA | TotalMortes:NA | Fumadores:53%
     * Data: 13/02/2020 | NovosCasos:04 | TotalCasos:04 | NovasMortes:04 | TotalMortes:04 | Fumadores:NA
     * Data: 03/03/2020 | NovosCasos:04 | TotalCasos:08 | NovasMortes:04 | TotalMortes:08 | Fumadores:NA
     * Data: 22/04/2020 | NovosCasos:04 | TotalCasos:12 | NovasMortes:04 | TotalMortes:12 | Fumadores:NA
     */
    private Country countryEN;
    /**
     * Instância da classe Continent para teste.
     */
    private Continent continent;

    @Before
    public void setUp() throws Exception {
        //---------------- Inicialização da classe Country ----------------
        countryPT = new Country("PT", "Portugal");
        countrySP = new Country("ES", "Spain");
        countryEN = new Country("EN", "England");
        //---------------- Inicialização da classe Case ----------------
        Case caseListPT = new Case();
        Case caseListES = new Case();
        Case caseListEN = new Case();

        caseListPT.addCase("2", "2", LocalDate.of(2020, 2, 10));
        caseListPT.addCase("2", "4", LocalDate.of(2020, 3, 1));
        caseListPT.addCase("2", "6", LocalDate.of(2020, 4, 20));

        caseListES.addCase("3", "3", LocalDate.of(2020, 2, 11));
        caseListES.addCase("3", "6", LocalDate.of(2020, 3, 2));
        caseListES.addCase("3", "9", LocalDate.of(2020, 4, 21));

        caseListEN.addCase("4", "4", LocalDate.of(2020, 2, 12));
        caseListEN.addCase("4", "8", LocalDate.of(2020, 3, 3));
        caseListEN.addCase("4", "12", LocalDate.of(2020, 4, 22));
        //---------------- Inicialização da classe Death ----------------
        Death deathListPT = new Death();
        Death deathListES = new Death();
        Death deathListEN = new Death();

        deathListPT.addDeath("2", "2", LocalDate.of(2020, 2, 10));
        deathListPT.addDeath("2", "4", LocalDate.of(2020, 3, 1));
        deathListPT.addDeath("2", "6", LocalDate.of(2020, 4, 20));

        deathListES.addDeath("3", "3", LocalDate.of(2020, 2, 11));
        deathListES.addDeath("3", "6", LocalDate.of(2020, 3, 2));
        deathListES.addDeath("3", "9", LocalDate.of(2020, 4, 21));

        deathListEN.addDeath("4", "4", LocalDate.of(2020, 2, 12));
        deathListEN.addDeath("4", "8", LocalDate.of(2020, 3, 3));
        deathListEN.addDeath("4", "12", LocalDate.of(2020, 4, 22));
        //---------------- Inicialização da classe Smoker ----------------
        Smoker smokerListPT = new Smoker();
        Smoker smokerListES = new Smoker();
        Smoker smokerListEN = new Smoker();

        smokerListPT.addSmoker("20", "30", LocalDate.of(2020, 1, 1));
        smokerListES.addSmoker("29", "30", LocalDate.of(2020, 1, 1));
        smokerListEN.addSmoker("22", "31", LocalDate.of(2020, 1, 1));
        //---------------- Adição à classe Country ----------------
        countryPT.addData(caseListPT, deathListPT, null, smokerListPT, null, null);
        countryEN.addData(caseListEN, deathListEN, null, smokerListEN, null, null);
        countrySP.addData(caseListES, deathListES, null, smokerListES, null, null);
        //---------------- Inicialização da classe Continent ----------------
        continent = new Continent("Europa");
        continent.addCountry(countryPT);
        continent.addCountry(countrySP);
        continent.addCountry(countryEN);
    }

    /**
     * Null test para o método totalCasesPerMonth.
     */
    @Test
    public void totalCasesPerMonthNoCountries() {
        Continent emptyContinent = new Continent("");

        Map<Integer, Integer> result = emptyContinent.totalCasesPerMonth();

        Map<Integer, Integer> expectedResult = new HashMap<>();

        Assert.assertEquals(result, expectedResult);
    }

    /**
     * Teste para o método totalCasesPerMonth.
     */
    @Test
    public void totalCasesPerMonthCorrect() {

        Map<Integer, Integer> result = continent.totalCasesPerMonth();

        Map<Integer, Integer> expectedResult = new HashMap<>();

        expectedResult.put(2, 9);
        expectedResult.put(3, 9);
        expectedResult.put(4, 9);

        Assert.assertEquals(expectedResult, result);
    }

    /**
     * Null test para o método totalDeathsPerMonth.
     */
    @Test
    public void totalDeathsPerMonthNoCountries() {
        Continent emptyContinent = new Continent("");

        Map<Integer, Integer> result = emptyContinent.totalCasesPerMonth();

        Map<Integer, Integer> expectedResult = new HashMap<>();

        Assert.assertEquals(result, expectedResult);
    }

    /**
     * Teste para o método totalDeathsPerMonth.
     */
    @Test
    public void totalDeathsPerMonthCorrect() {

        Map<Integer, Integer> result = continent.totalCasesPerMonth();

        Map<Integer, Integer> expectedResult = new HashMap<>();

        expectedResult.put(2, 9);
        expectedResult.put(3, 9);
        expectedResult.put(4, 9);

        Assert.assertEquals(expectedResult, result);
    }

    /**
     * Null test para o método newCasesPerDay.
     */
    @Test
    public void newCasesPerDayNoDate() {
        ArrayList<Country> result = continent.newCasesPerDay(null);

        ArrayList<Country> expectedResult = new ArrayList<>();

        Assert.assertEquals(result, expectedResult);
    }

    /**
     * Teste para o método newCasesPerDay.
     */
    @Test
    public void newCasesPerDayCorrect() {
        ArrayList<Country> result = continent.newCasesPerDay(LocalDate.of(2020, 3, 1));

        ArrayList<Country> expectedResult = new ArrayList<Country>();

        expectedResult.add(countryPT);
        expectedResult.add(countryEN);
        expectedResult.add(countrySP);

        Assert.assertEquals(result, expectedResult);
    }

}
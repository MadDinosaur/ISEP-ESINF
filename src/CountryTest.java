import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.*;

public class CountryTest {

    private Country countryWithData;
    private Country emptyCountry;

    @Before
    public void setUp() throws Exception {
        emptyCountry = new Country("", "");
        countryWithData = new Country("PRT", "Portugal");

        String population = "10000";
        String agedPeople = "60%";
        String cardiovascular = "50%";
        String diabetes = "90%";
        String beds = "700";
        String life = "80";

        Case caseList = new Case();
        Death deathList = new Death();
        Smoker smokerList = new Smoker();

        caseList.addCase("1", "1", LocalDate.of(2020, 10, 26));
        caseList.addCase("3", "4", LocalDate.of(2020, 11, 5));
        caseList.addCase("5", "9", LocalDate.of(2020, 11, 6));

        deathList.addDeath("0", "0", LocalDate.of(2020, 10, 25));
        deathList.addDeath("2", "2", LocalDate.of(2020, 11, 6));

        smokerList.addSmoker("30", "20", LocalDate.of(2020, 11, 5));

        countryWithData.addData(caseList, deathList, smokerList,population,agedPeople,cardiovascular,diabetes,beds,life);
    }

    @Test
    public void dateCasesReached_NoCases() {
        LocalDate expectedResult = null;
        LocalDate result = emptyCountry.dateCasesReached(1);

        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void dateCasesReached_NotReached() {
        LocalDate expectedResult = null;
        LocalDate result = countryWithData.dateCasesReached(10);

        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void dateCasesReached() {
        LocalDate expectedResult = LocalDate.of(2020, 11, 6);
        LocalDate result = countryWithData.dateCasesReached(5);

        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void numCasesReached_NoCases() {
        int expectedResult = 0;
        int result = emptyCountry.numCasesReached(emptyCountry.oldestEntry(), 1);

        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void numCasesReached_NotReached() {
        int expectedResult = 0;
        int result = countryWithData.numCasesReached(countryWithData.oldestEntry(), 10);

        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void numCasesReached() {
        int expectedResult = 11; //Nยบ de dias entre 26/10 (a primeira data com casos registados) e 05/11 (data em que ultrapassa 5 casos)
        int result = countryWithData.numCasesReached(countryWithData.oldestEntry(), 5);

        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void hasMoreSmokersThan_NoData() {
        boolean expectedResult = false;
        boolean result = emptyCountry.hasMoreSmokersThan(10f);

        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void hasMoreSmokersThan_False() {
        boolean expectedResult = false;
        boolean result = countryWithData.hasMoreSmokersThan(90f);

        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void hasMoreSmokersThan_True() {
        boolean expectedResult = true;
        boolean result = countryWithData.hasMoreSmokersThan(40f);

        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void lastDateOfMonth_NoData() {
        LocalDate expectedResult = null;
        LocalDate result = emptyCountry.lastDateOfMonth(10, 2020);

        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void lastDateOfMonth() {
        LocalDate expectedResult = LocalDate.of(2020, 11, 6);
        LocalDate result = countryWithData.lastDateOfMonth(11, 2020);

        Assert.assertEquals(expectedResult, result);
    }
}
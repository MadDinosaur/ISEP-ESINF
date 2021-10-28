import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.*;

public class CountryTest {
	/**
     * Instância 1 da classe Country para teste.
     * Data: 25/10/2020 | NovosCasos:NA | TotalCasos:NA | NovasMortes:00 | TotalMortes:00 | Fumadores:NA
	 * Data: 26/10/2020 | NovosCasos:01 | TotalCasos:01 | NovasMortes:NA | TotalMortes:NA | Fumadores:NA
     * Data: 05/11/2020 | NovosCasos:03 | TotalCasos:04 | NovasMortes:NA | TotalMortes:NA | Fumadores:50%
     * Data: 06/11/2020 | NovosCasos:05 | TotalCasos:09 | NovasMortes:02 | TotalMortes:02 | Fumadores:NA
     */
    private Country countryWithData;
	/**
     * Instância 1 da classe Country para teste.
	 * Sem dados.
	 */
    private Country emptyCountry;

    @Before
    public void setUp() throws Exception {
		//---------------- Inicialização da classe Country ----------------
        emptyCountry = new Country("", "");
        countryWithData = new Country("PRT", "Portugal");
		//---------------- Inicialização da classe Case ----------------
        Case caseList = new Case();
      
        caseList.addCase("1", "1", LocalDate.of(2020, 10, 26));
        caseList.addCase("3", "4", LocalDate.of(2020, 11, 5));
        caseList.addCase("5", "9", LocalDate.of(2020, 11, 6));
		//---------------- Inicialização da classe Death ----------------
		Death deathList = new Death();
		
        deathList.addDeath("0", "0", LocalDate.of(2020, 10, 25));
        deathList.addDeath("2", "2", LocalDate.of(2020, 11, 6));
		//---------------- Inicialização da classe Smoker ----------------
		Smoker smokerList = new Smoker();
        smokerList.addSmoker("30", "20", LocalDate.of(2020, 11, 5));
		//---------------- Adição à classe Country ----------------
        countryWithData.addData(caseList, deathList, null, smokerList, null, null);
    }
	
	/**
	* Null test para o método dateCasesReached.
	*/
    @Test
    public void dateCasesReached_NoCases() {
        LocalDate expectedResult = null;
        LocalDate result = emptyCountry.dateCasesReached(1);

        Assert.assertEquals(expectedResult, result);
    }
	
	/**
	* Teste para o método dateCasesReached - parâmetro não alcançãvel.
	*/
    @Test
    public void dateCasesReached_NotReached() {
        LocalDate expectedResult = null;
        LocalDate result = countryWithData.dateCasesReached(10);

        Assert.assertEquals(expectedResult, result);
    }
	
	/**
	* Teste para o método dateCasesReached.
	*/
    @Test
    public void dateCasesReached() {
        LocalDate expectedResult = LocalDate.of(2020, 11, 6);
        LocalDate result = countryWithData.dateCasesReached(5);

        Assert.assertEquals(expectedResult, result);
    }
	
	/**
	* Null test para o método numCasesReached.
	*/
    @Test
    public void numCasesReached_NoCases() {
        int expectedResult = 0;
        int result = emptyCountry.numCasesReached(emptyCountry.oldestEntry(), 1);

        Assert.assertEquals(expectedResult, result);
    }

	/**
	* Teste para o método numCasesReached - parâmetro não alcançãvel.
	*/
    @Test
    public void numCasesReached_NotReached() {
        int expectedResult = 0;
        int result = countryWithData.numCasesReached(countryWithData.oldestEntry(), 10);

        Assert.assertEquals(expectedResult, result);
    }
	
	/**
	* Teste para o método numCasesReached.
	*/
    @Test
    public void numCasesReached() {
        int expectedResult = 11; //Nยบ de dias entre 26/10 (a primeira data com casos registados) e 05/11 (data em que ultrapassa 5 casos)
        int result = countryWithData.numCasesReached(countryWithData.oldestEntry(), 5);

        Assert.assertEquals(expectedResult, result);
    }

	/**
	* Null test para o método hasMoreSmokersThan.
	*/
    @Test
    public void hasMoreSmokersThan_NoData() {
        boolean expectedResult = false;
        boolean result = emptyCountry.hasMoreSmokersThan(10f);

        Assert.assertEquals(expectedResult, result);
    }
	
	/**
	* Teste para o método hasMoreSmokersThan - resultado False.
	*/
    @Test
    public void hasMoreSmokersThan_False() {
        boolean expectedResult = false;
        boolean result = countryWithData.hasMoreSmokersThan(90f);

        Assert.assertEquals(expectedResult, result);
    }
	
	/**
	* Teste para o método hasMoreSmokersThan - resultado True.
	*/
    @Test
    public void hasMoreSmokersThan_True() {
        boolean expectedResult = true;
        boolean result = countryWithData.hasMoreSmokersThan(40f);

        Assert.assertEquals(expectedResult, result);
    }
	
	/**
	* Null test para o método lastDateOfMonth.
	*/
    @Test
    public void lastDateOfMonth_NoData() {
        LocalDate expectedResult = null;
        LocalDate result = emptyCountry.lastDateOfMonth(10, 2020);

        Assert.assertEquals(expectedResult, result);
    }

	/**
	* Teste para o método lastDateOfMonth.
	*/
    @Test
    public void lastDateOfMonth() {
        LocalDate expectedResult = LocalDate.of(2020, 11, 6);
        LocalDate result = countryWithData.lastDateOfMonth(11, 2020);

        Assert.assertEquals(expectedResult, result);
    }
}
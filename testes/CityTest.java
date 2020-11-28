import org.junit.Assert;

import static org.junit.jupiter.api.Assertions.*;

class CityTest {

    @org.junit.jupiter.api.Test
    void distanceFrom()
    {
        City firstCity = new City("argentina","americasul","41.67","buenosaires","-34.6131500","-58.3772300");
        City secondCity = new City("bolivia","americasul","9.70","lapaz","-16.5000000","-68.1500000");

        Double expected = firstCity.distanceFrom(secondCity);

        assertEquals(2236888.7384291743,expected);
    }

    @org.junit.jupiter.api.Test
    void distanceFromNull()
    {
        City firstCity = new City("argentina","americasul","41.67","buenosaires","0","0");
        City secondCity = new City("bolivia","americasul","9.70","lapaz","0","0");

        Double expected = firstCity.distanceFrom(secondCity);

        assertEquals(0,expected);
    }
}
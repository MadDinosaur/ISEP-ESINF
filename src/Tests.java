import java.time.LocalDate;

public class Tests
{
    private Integer newTests;
    private Integer totalTests;
    private LocalDate date;

    public Tests(String newTests, String totalTests, LocalDate date)
    {
        this.newTests = Integer.parseInt(newTests);
        this.totalTests = Integer.parseInt(totalTests);
        this.date = date;
    }

    //------------------Getters-------------------------

    public Integer getTests()
    {
        return newTests;
    }

    public Integer getTotalTests()
    {
        return totalTests;
    }

    public LocalDate getDate()
    {
        return date;
    }

}

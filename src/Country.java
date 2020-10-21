public class Country {

    private String isoCode;
    private String location;

    public Country(String isoCode, String location) {
        this.isoCode = isoCode;
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        // self check
        if (this == o)
            return true;
        // null check
        if (o == null)
            return false;
        // type check and cast
        if (getClass() != o.getClass())
            return false;
        Country c = (Country) o;
        // field comparison
        return c.getIsoCode().equalsIgnoreCase(this.getIsoCode());
    }

    @Override
    public int hashCode() {
        return getIsoCode().hashCode();
    }

    public String getIsoCode() {
        return isoCode;
    }

    public String getLocation() {
        return location;
    }
}

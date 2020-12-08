public class ChemicalElement implements Comparable<ChemicalElement> {
    private int atomicNumber;
    private String element;
    private String symbol;
    private float atomicWeight;
    private float atomicMass;
    int period;
    int group;
    String phase;
    String mostStableCrystal;
    String type;
    float ionicRadius;
    float atomicRadius;
    float electronegativity;
    float firstIonizationPotential;
    float density;
    float meltingPoint;
    float boilingPoint;
    int isotopes;
    String discoverer;
    int yearOfDiscovery;
    float specificHeatCapacity;
    String electronConfiguration;
    int displayRow;
    int displayColumn;

    public ChemicalElement(String atomicNumber, String element, String symbol, String atomicWeight, String atomicMass, String period, String group, String phase, String mostStableCrystal, String type, String ionicRadius, String atomicRadius, String electronegativity, String firstIonizationPotential, String density, String meltingPoint, String boilingPoint, String isotopes, String discoverer, String yearOfDiscovery, String specificHeatCapacity, String electronConfiguration, String displayRow, String displayColumn) {
        this.atomicNumber = Integer.parseInt(atomicNumber);
        this.element = element;
        this.symbol = symbol;
        try {this.atomicWeight = Float.parseFloat(atomicWeight);} catch (NumberFormatException e) {this.atomicWeight = 0f;}
        try {this.atomicMass = Float.parseFloat(atomicMass);} catch (NumberFormatException e) {this.atomicMass = 0f;}
        this.period = Integer.parseInt(period);
        this.group = Integer.parseInt(group);
        this.phase = phase;
        this.mostStableCrystal = mostStableCrystal;
        this.type = type;
        try {this.ionicRadius = Float.parseFloat(ionicRadius);}  catch (NumberFormatException e) {this.ionicRadius = 0f;}
        try {this.atomicRadius = Float.parseFloat(atomicRadius);} catch (NumberFormatException e) {this.atomicRadius = 0f;}
        try {this.electronegativity = Float.parseFloat(electronegativity);} catch (NumberFormatException e) {this.electronegativity = 0f;}
        try {this.firstIonizationPotential = Float.parseFloat(firstIonizationPotential);} catch (NumberFormatException e) {this.firstIonizationPotential = 0f;}
        try {this.density = Float.parseFloat(density);} catch (NumberFormatException e) {this.density = 0f;}
        try {this.meltingPoint = Float.parseFloat(meltingPoint);} catch (NumberFormatException e) {this.meltingPoint = 0f;}
        try {this.boilingPoint = Float.parseFloat(boilingPoint);} catch (NumberFormatException e) {this.boilingPoint = 0f;}
        try {this.isotopes = Integer.parseInt(isotopes);} catch (NumberFormatException e) {this.isotopes = 0;}
        this.discoverer = discoverer;
        try {this.yearOfDiscovery = Integer.parseInt(yearOfDiscovery);} catch (NumberFormatException e) {this.yearOfDiscovery = 0;}
        try {this.specificHeatCapacity = Float.parseFloat(specificHeatCapacity);} catch (NumberFormatException e) {this.specificHeatCapacity = 0f;}
        this.electronConfiguration = electronConfiguration;
        this.displayRow = Integer.parseInt(displayRow);
        this.displayColumn = Integer.parseInt(displayColumn);
    }

    @Override
    public int compareTo(ChemicalElement e) {
        return getElement().compareTo(e.getElement());
    }

    public int getAtomicNumber() {
        return atomicNumber;
    }

    public String getElement() {
        return element;
    }

    public String getSymbol() {
        return symbol;
    }

    public float getAtomicWeight() {
        return atomicWeight;
    }

    public float getAtomicMass() {
        return atomicMass;
    }
}

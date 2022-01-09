import java.util.Comparator;

/**
 * Classe que modela um elemento da tabela periódica.
 */
public class ChemicalElement {
    private int atomicNumber;
    private String element;
    private String symbol;
    private float atomicWeight;
    private float atomicMass;
    int period;
    int group;
    private String phase;
    String mostStableCrystal;
    private String type;
    float ionicRadius;
    float atomicRadius;
    float electronegativity;
    float firstIonizationPotential;
    float density;
    float meltingPoint;
    float boilingPoint;
    int isotopes;
    private String discoverer;
    private int yearOfDiscovery;
    float specificHeatCapacity;
    String electronConfiguration;
    int displayRow;
    int displayColumn;
    //------------------------------- Construtores ---------------------------------

    /**
     * Construtor completo da classe ChemicalElement.
     *
     * @param atomicNumber
     * @param element
     * @param symbol
     * @param atomicWeight
     * @param atomicMass
     * @param period
     * @param group
     * @param phase
     * @param mostStableCrystal
     * @param type
     * @param ionicRadius
     * @param atomicRadius
     * @param electronegativity
     * @param firstIonizationPotential
     * @param density
     * @param meltingPoint
     * @param boilingPoint
     * @param isotopes
     * @param discoverer
     * @param yearOfDiscovery
     * @param specificHeatCapacity
     * @param electronConfiguration
     * @param displayRow
     * @param displayColumn
     */
    public ChemicalElement(String atomicNumber, String element, String symbol, String atomicWeight, String atomicMass, String period, String group, String phase, String mostStableCrystal, String type, String ionicRadius, String atomicRadius, String electronegativity, String firstIonizationPotential, String density, String meltingPoint, String boilingPoint, String isotopes, String discoverer, String yearOfDiscovery, String specificHeatCapacity, String electronConfiguration, String displayRow, String displayColumn) {
        this.atomicNumber = Integer.parseInt(atomicNumber);
        this.element = element;
        this.symbol = symbol;
        try {
            this.atomicWeight = Float.parseFloat(atomicWeight);
        } catch (NumberFormatException e) {
            this.atomicWeight = 0f;
        }
        try {
            this.atomicMass = Float.parseFloat(atomicMass);
        } catch (NumberFormatException e) {
            this.atomicMass = 0f;
        }
        this.period = Integer.parseInt(period);
        this.group = Integer.parseInt(group);
        this.phase = phase;
        this.mostStableCrystal = mostStableCrystal;
        this.type = type;
        try {
            this.ionicRadius = Float.parseFloat(ionicRadius);
        } catch (NumberFormatException e) {
            this.ionicRadius = 0f;
        }
        try {
            this.atomicRadius = Float.parseFloat(atomicRadius);
        } catch (NumberFormatException e) {
            this.atomicRadius = 0f;
        }
        try {
            this.electronegativity = Float.parseFloat(electronegativity);
        } catch (NumberFormatException e) {
            this.electronegativity = 0f;
        }
        try {
            this.firstIonizationPotential = Float.parseFloat(firstIonizationPotential);
        } catch (NumberFormatException e) {
            this.firstIonizationPotential = 0f;
        }
        try {
            this.density = Float.parseFloat(density);
        } catch (NumberFormatException e) {
            this.density = 0f;
        }
        try {
            this.meltingPoint = Float.parseFloat(meltingPoint);
        } catch (NumberFormatException e) {
            this.meltingPoint = 0f;
        }
        try {
            this.boilingPoint = Float.parseFloat(boilingPoint);
        } catch (NumberFormatException e) {
            this.boilingPoint = 0f;
        }
        try {
            this.isotopes = Integer.parseInt(isotopes);
        } catch (NumberFormatException e) {
            this.isotopes = 0;
        }
        this.discoverer = discoverer;
        try {
            this.yearOfDiscovery = Integer.parseInt(yearOfDiscovery);
        } catch (NumberFormatException e) {
            this.yearOfDiscovery = 0;
        }
        try {
            this.specificHeatCapacity = Float.parseFloat(specificHeatCapacity);
        } catch (NumberFormatException e) {
            this.specificHeatCapacity = 0f;
        }
        this.electronConfiguration = electronConfiguration;
        this.displayRow = Integer.parseInt(displayRow);
        this.displayColumn = Integer.parseInt(displayColumn);
    }

    /**
     * Construtor parcial da classe ChemicalElement.
     * Deve ser usado apenas para buscas através do método find() de BinaryTree.
     *
     * @param atomicNumber: Número Atómico
     */
    public ChemicalElement(int atomicNumber) {
        this.element = "";
        this.specificHeatCapacity = 0;
        this.yearOfDiscovery = 0;
        this.isotopes = 0;
        this.boilingPoint = 0;
        this.meltingPoint = 0;
        this.density = 0;
        this.firstIonizationPotential = 0;
        this.electronegativity = 0;
        this.atomicRadius = 0;
        this.ionicRadius = 0;
        this.atomicMass = 0;
        this.atomicWeight = 0;
        this.atomicNumber = atomicNumber;
        this.discoverer = "";
        this.displayColumn = -1;
        this.displayRow = -1;
        this.electronConfiguration = "";
        this.group = 0;
        this.phase = "";
        this.symbol = "";
        this.type = "";
    }

    /**
     * Construtor parcial da classe ChemicalElement.
     * Deve ser usado apenas para buscas através do método find() de BinaryTree.
     *
     * @param element: Nome do Elemento ou Símbolo
     */
    public ChemicalElement(String element) {
        if (element.length() <= 3) {
            this.symbol = element;
            this.element = "";
        } else {
            this.element = element;
            this.symbol = "";
        }
        this.specificHeatCapacity = 0;
        this.yearOfDiscovery = 0;
        this.isotopes = 0;
        this.boilingPoint = 0;
        this.meltingPoint = 0;
        this.density = 0;
        this.firstIonizationPotential = 0;
        this.electronegativity = 0;
        this.atomicRadius = 0;
        this.ionicRadius = 0;
        this.atomicMass = 0;
        this.atomicWeight = 0;
        this.atomicNumber = 0;
        this.discoverer = "";
        this.displayColumn = -1;
        this.displayRow = -1;
        this.electronConfiguration = "";
        this.group = 0;
        this.phase = "";
        this.type = "";
    }

    /**
     * Construtor parcial da classe ChemicalElement.
     * Deve ser usado apenas para buscas através do método find() de BinaryTree.
     *
     * @param atomicMass: Massa Atómica
     */
    public ChemicalElement(float atomicMass) {
        this.element = "";
        this.specificHeatCapacity = 0;
        this.yearOfDiscovery = 0;
        this.isotopes = 0;
        this.boilingPoint = 0;
        this.meltingPoint = 0;
        this.density = 0;
        this.firstIonizationPotential = 0;
        this.electronegativity = 0;
        this.atomicRadius = 0;
        this.ionicRadius = 0;
        this.atomicMass = atomicMass;
        this.atomicWeight = 0;
        this.atomicNumber = 0;
        this.discoverer = "";
        this.displayColumn = -1;
        this.displayRow = -1;
        this.electronConfiguration = "";
        this.group = 0;
        this.phase = "";
        this.symbol = "";
        this.type = "";
    }

    /**
     * Construtor vazio da classe ChemicalElement.
     */
    public ChemicalElement() {
        this.element = "";
        this.specificHeatCapacity = 0;
        this.yearOfDiscovery = 0;
        this.isotopes = 0;
        this.boilingPoint = 0;
        this.meltingPoint = 0;
        this.density = 0;
        this.firstIonizationPotential = 0;
        this.electronegativity = 0;
        this.atomicRadius = 0;
        this.ionicRadius = 0;
        this.atomicMass = 0;
        this.atomicWeight = 0;
        this.atomicNumber = 0;
        this.discoverer = "";
        this.displayColumn = -1;
        this.displayRow = -1;
        this.electronConfiguration = "";
        this.group = 0;
        this.phase = "";
        this.symbol = "";
        this.type = "";
    }

    //------------------------------- Getters e Setters ---------------------------------
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

    public static Comparator<ChemicalElement> getByAtomicNumber() {
        return new Comparator<ChemicalElement>() {
            @Override
            public int compare(ChemicalElement o1, ChemicalElement o2) {
                return Integer.compare(o1.getAtomicNumber(), o2.getAtomicNumber());
            }
        };
    }

    public static Comparator<ChemicalElement> getByElement() {
        return new Comparator<ChemicalElement>() {
            @Override
            public int compare(ChemicalElement o1, ChemicalElement o2) {
                return (o1.getElement().compareTo(o2.getElement()));
            }
        };
    }

    public static Comparator<ChemicalElement> getBySymbol() {
        return new Comparator<ChemicalElement>() {
            @Override
            public int compare(ChemicalElement o1, ChemicalElement o2) {
                return (o1.getSymbol()).compareTo(o2.getSymbol());
            }
        };
    }

    public static Comparator<ChemicalElement> getByAtomicMass() {
        return new Comparator<ChemicalElement>() {
            @Override
            public int compare(ChemicalElement o1, ChemicalElement o2) {
                return Float.compare(o1.getAtomicMass(), o2.getAtomicMass());
            }
        };
    }

    public String getDiscoverer() {
        return discoverer;
    }

    public int getYearOfDiscovery() {
        return yearOfDiscovery;
    }

    public String getPhase() {
        return phase;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return String.format("|%d|%s|%s|%.2f|%.2f|%d|%d|%s|%s|%s|%.2f|%.2f|%.2f|%.2f|%.2f|%.2f|%.2f|%d|%s|%d|%.2f|%s|",
                atomicNumber, element, symbol, atomicWeight, atomicMass, period, group, phase, mostStableCrystal, type, ionicRadius, atomicRadius, electronegativity, firstIonizationPotential, density, meltingPoint, boilingPoint, isotopes, discoverer, yearOfDiscovery, specificHeatCapacity, electronConfiguration);
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
        ChemicalElement c = (ChemicalElement) o;
        // field comparison
        return c.atomicNumber == this.atomicNumber && c.element.equals(this.element);
    }
}

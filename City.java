public class City {
    private Point latLon;
    private String name;
    private double area;
    private int population;
    private int populationDensity;
    private double percentOfForeignResidents;
    private double gdp;

    public City() {
        latLon = new Point();
        name = "";
        population = 0;
        area = 0;
        populationDensity = 0;
        percentOfForeignResidents = 0;
        gdp = 0;

    }

    public City(Point p, String n, int pop, double a, int popD, double percentFR, double gdp) {
        this.latLon = p;
        this.name = n;
        this.population = pop;
        this.area = a;
        this.populationDensity = population;
        this.percentOfForeignResidents = percentFR;
        this.gdp = gdp;
    }

    public void setLatLon(double x, double y) {
        latLon.setX(x);
        latLon.setY(y);
    }

    public Point getLatLon() {
        return latLon;

    }

    public void setName(String n) {
        name = n;
    }

    public String getName() {
        return name;

    }

    public void setPopulation(int p) {
        population = p;
    }

    public int getPopulation() {
        return population;

    }

    public void setArea(double a) {
        area = a;
    }

    public double getArea() {
        return area;

    }

    public void setPopulationDensity(int popD) {
        populationDensity = popD;
    }

    public int getPopulationDensity() {
        return populationDensity;

    }

    public void setPercentOfForeignResidents(double percentFR) {
        percentOfForeignResidents = percentFR;
    }

    public double getPercentOfForeignResidents() {
        return percentOfForeignResidents;

    }

    public void setGDP(double g) {
        gdp = g;
    }

    public double getGDP() {
        return gdp;

    }

    public String getDescription() {
        return "City Name:" + this.name + " Lat Lon:" + this.latLon + " Area in sqkm:" + this.area + " Population:"
                + this.population + " Population Density:" + this.populationDensity
                + " Percentage of Foreign Residents:" + this.percentOfForeignResidents + " GDP:" + this.gdp;
    }

}
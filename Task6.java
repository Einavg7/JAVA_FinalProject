import java.sql.*;
import java.awt.Color;

public class Task6 {
    public static void main(String[] args) {
        City[] cities = getCityData();
        Point[] coords = new Point[10];

        for (int g = 0; g < cities.length; g++) {
            coords[g] = cities[g].getLatLon();
        }

        SimpleFrame frame = new SimpleFrame(1250, 1250);
        // First compute the bounding box of the coordinates of your cities
        Rectangle cityBBox = getRectangleBBox(coords);
        // Then decide a padding you want to have applied in your window
        double padding = 50;
        double pointX = cityBBox.getTopLeftPoint().getX() - padding;
        double pointY = cityBBox.getTopLeftPoint().getY() - padding;
        double widthBBox = cityBBox.getWidth() + (2 * padding);
        double heightBBox = cityBBox.getHeight() + (2 * padding);
        Rectangle cityBBoxPadding = new Rectangle(pointX, pointY, widthBBox, heightBBox);

        // scale LatLon of cities
        double scale = 3500 / Math.max(cityBBoxPadding.getHeight(), cityBBoxPadding.getWidth());
        City[] rescaledCities = new City[10];
        Point[] rescaledCoords = new Point[10];
        for (int k = 0; k < cities.length; k++) {
            double rescaledX = scale * cities[k].getLatLon().getX();
            double rescaledY = scale * cities[k].getLatLon().getY();
            cities[k].setLatLon(rescaledX, rescaledY);
            rescaledCities[k] = cities[k];
            rescaledCoords[k] = rescaledCities[k].getLatLon();
        }
        // rescale centroid of bbox
        double centerXBBox = cityBBoxPadding.centroid().getX() * scale;
        double centerYBBox = cityBBoxPadding.centroid().getY() * scale;
        // calculate tanslation
        double xTranslated = 700 - centerXBBox;
        double yTranslated = 700 - centerYBBox;
        City[] translatedCities = new City[10];
        double[] yCoord = new double[10];

        // translate city coordinates
        Point[] translatedCoords = new Point[10];
        double[] diameters = new double[10];
        double[] gdps = new double[10];
        for (int m = 0; m < translatedCities.length; m++) {
            double xCoord = rescaledCoords[m].getX() + xTranslated;
            // double yCoord = rescaledCoords[m].getY() + yTranslated;
            yCoord[m] = rescaledCoords[m].getY() + yTranslated;
            double yFlip = 1000 - yCoord[m];
            rescaledCities[m].setLatLon(xCoord, yFlip);
            translatedCities[m] = rescaledCities[m];
            translatedCoords[m] = translatedCities[m].getLatLon();
            diameters[m] = translatedCities[m].getPopulationDensity() / 100; // population density for 100 skqm
            gdps[m] = translatedCities[m].getGDP();
        }

        // normalize densities for hsb color
        double[] densitiesNormalized = new double[10];
        for (int b = 0; b < translatedCities.length; b++) {
            densitiesNormalized[b] = (diameters[b] - getMin(diameters)) / (getMax(diameters) - getMin(diameters));
        }

        // normalize gdp for hsb color
        double[] gdpsNormalized = new double[10];
        for (int d = 0; d < translatedCities.length; d++) {
            gdpsNormalized[d] = (gdps[d] - getMin(gdps)) / (getMax(gdps) - getMin(gdps));
        }

        // color and add cities to frame
        Color red = new Color(255, 0, 0);
        Color white = new Color(255, 255, 255, 1);
        Color darkGray = new Color(64, 64, 64);
        Color blue = new Color(0, 0, 255);
        Color magenta = new Color(255, 0, 255);
        Color yellow = new Color(255, 255, 0);
        Label[] labels = new Label[10];
        Circle[] buffers = drawBuffers(translatedCoords, diameters);
        Rectangle[] barChartsGDP = new Rectangle[10];
        Label[] gdpLabels = new Label[10];
        Rectangle[] barChartsPopulation = new Rectangle[10];
        Label[] populationLabels = new Label[10];
        Rectangle[] barChartsForeigners = new Rectangle[10];
        Label[] foreignerLabels = new Label[10];
        for (int j = 0; j < cities.length; j++) {
            Label myLabel = new Label();
            myLabel.setPosition(translatedCities[j].getLatLon());
            myLabel.setText(translatedCities[j].getName());
            myLabel.setStrokeColor(blue);
            labels[j] = myLabel;
            frame.addToPlot(labels[j]);
            buffers[j].setFillColor(Color.getHSBColor(0F, convertToFloat(densitiesNormalized[j]), 1F));
            buffers[j].setStrokeColor(darkGray);
            frame.addToPlot(buffers[j]);
            Rectangle myBarChartGDP = new Rectangle();
            Point adjustedBarChartGDP = new Point(translatedCities[j].getLatLon().getX(),
                    translatedCities[j].getLatLon().getY() + 30);
            myBarChartGDP.setTopLeftPoint(adjustedBarChartGDP);
            myBarChartGDP.setHeight(12);
            myBarChartGDP.setWidth(110);
            barChartsGDP[j] = myBarChartGDP;
            barChartsGDP[j].setFillColor(Color.getHSBColor(0.4F, convertToFloat(gdpsNormalized[j]), 1F));
            frame.addToPlot(barChartsGDP[j]);
            Label myGdp = new Label();
            Point adjustedGdp = new Point(myBarChartGDP.centroid().getX() - 50, myBarChartGDP.centroid().getY() + 5);
            myGdp.setPosition(adjustedGdp);
            myGdp.setText("GDP: " + Double.toString(translatedCities[j].getGDP()) + " ($BN)");
            gdpLabels[j] = myGdp;
            frame.addToPlot(gdpLabels[j]);
            Rectangle myBarChartPop = new Rectangle();
            Point adjustedBarChartPop = new Point(translatedCities[j].getLatLon().getX() - 20,
                    translatedCities[j].getLatLon().getY() - 30);
            myBarChartPop.setTopLeftPoint(adjustedBarChartPop);
            myBarChartPop.setHeight(50);
            myBarChartPop.setWidth(10);
            barChartsPopulation[j] = myBarChartPop;
            barChartsPopulation[j].setFillColor(yellow);
            frame.addToPlot(barChartsPopulation[j]);
            Label myPop = new Label();
            Point adjustedLabelPop = new Point(barChartsPopulation[j].getTopLeftPoint().getX() + 10,
                    barChartsPopulation[j].getTopLeftPoint().getY());
            myPop.setPosition(adjustedLabelPop);
            myPop.setText("Total Population: " + Integer.toString(translatedCities[j].getPopulation()));
            populationLabels[j] = myPop;
            frame.addToPlot(populationLabels[j]);
            Rectangle myBarChartForeign = new Rectangle();
            Point adjustedBarChartForeign = new Point(translatedCities[j].getLatLon().getX() - 20,
                    translatedCities[j].getLatLon().getY() + 20);
            myBarChartForeign.setTopLeftPoint(adjustedBarChartForeign);
            myBarChartForeign.setHeight(translatedCities[j].getPercentOfForeignResidents() * 0.5);
            myBarChartForeign.setWidth(10);
            barChartsForeigners[j] = myBarChartForeign;
            barChartsForeigners[j].setFillColor(magenta);
            frame.addToPlot(barChartsForeigners[j]);
            Label myForeign = new Label();
            Point adjustedLabelForeign = new Point(barChartsForeigners[j].getTopLeftPoint().getX() + 13,
                    barChartsForeigners[j].getTopLeftPoint().getY() + 5);
            myForeign.setPosition(adjustedLabelForeign);
            myForeign.setText(
                    "Foreign Population: " + Double.toString(translatedCities[j].getPercentOfForeignResidents()) + "%");
            foreignerLabels[j] = myForeign;
            frame.addToPlot(foreignerLabels[j]);

        }
        // create rectangles for gdp legends
        Rectangle legendRectangleOne = new Rectangle(150, 20, 10, 30);
        Rectangle legendRectangleTwo = new Rectangle(150, 50, 10, 30);
        Rectangle legendRectangleThree = new Rectangle(150, 80, 10, 30);
        legendRectangleOne.setFillColor(Color.getHSBColor(0.4F, convertToFloat(getMin(gdpsNormalized)), 1F));
        legendRectangleTwo.setFillColor(Color.getHSBColor(0.4F, convertToFloat(getMax(gdpsNormalized) / 2), 1F));
        legendRectangleThree.setFillColor(Color.getHSBColor(0.4F, convertToFloat(getMax(gdpsNormalized)), 1F));
        Label labelOneRec = new Label(legendRectangleOne.centroid().getX() + 7, legendRectangleOne.centroid().getY(),
                "Low GDP");
        Label labelTwoRec = new Label(legendRectangleTwo.centroid().getX() + 7, legendRectangleTwo.centroid().getY(),
                "Average GDP");
        Label labelThreeRec = new Label(legendRectangleThree.centroid().getX() + 7,
                legendRectangleThree.centroid().getY(), "High GDP");
        frame.addToPlot(legendRectangleOne);
        frame.addToPlot(legendRectangleTwo);
        frame.addToPlot(legendRectangleThree);
        frame.addToPlot(labelOneRec);
        frame.addToPlot(labelTwoRec);
        frame.addToPlot(labelThreeRec);

        // create circles for density pop legend
        Circle legendCircleOne = new Circle();
        Circle legendCircleTwo = new Circle();
        Circle legendCircleThree = new Circle();
        Point circleOne = new Point(140, 320);
        Point circleTwo = new Point(100, 345);
        Point circleThree = new Point(50, 450);
        legendCircleOne.setTopLeftPoint(circleOne);
        legendCircleOne.setDiameter(getMin(diameters));
        legendCircleOne.setFillColor(Color.getHSBColor(0F, convertToFloat(getMin(densitiesNormalized)), 1F));
        legendCircleTwo.setTopLeftPoint(circleTwo);
        legendCircleTwo.setDiameter(getMax(diameters) / 2);
        legendCircleTwo.setFillColor(Color.getHSBColor(0F, convertToFloat(getMax(densitiesNormalized) / 2), 1F));
        legendCircleThree.setTopLeftPoint(circleThree);
        legendCircleThree.setDiameter(getMax(diameters));
        legendCircleThree.setFillColor(Color.getHSBColor(0F, convertToFloat(getMax(densitiesNormalized)), 1F));
        Label labelOneCirc = new Label(legendCircleOne.centroid().getX() + 2, legendCircleOne.centroid().getY(),
                "Low Population Density");
        Label labelTwoCirc = new Label(legendCircleTwo.centroid().getX() + 2, legendCircleTwo.centroid().getY(),
                "Average Population Density");
        Label labelThreeCirc = new Label(legendCircleThree.centroid().getX() + 2, legendCircleThree.centroid().getY(),
                "High Population Density");
        Point[] line = { legendCircleOne.centroid(), legendCircleTwo.centroid(), legendCircleThree.centroid() };
        Polyline poly = new Polyline(line, 3);

        frame.addToPlot(legendCircleOne);
        frame.addToPlot(legendCircleTwo);
        frame.addToPlot(legendCircleThree);
        frame.addToPlot(labelOneCirc);
        frame.addToPlot(labelTwoCirc);
        frame.addToPlot(labelThreeCirc);
        frame.addToPlot(poly);
        frame.drawAllFeature();

    }

    public static Circle[] drawBuffers(Point[] points, double[] diameters) {
        Circle[] buffers = new Circle[10];
        for (int i = 0; i < buffers.length; i++) {
            Circle myCircle = new Circle();
            double centerX = points[i].getX();
            double centerY = points[i].getY();
            double a = diameters[i] / 2;
            double topLeftX = centerX - a;
            double topLeftY = centerY - a;
            Point topLeftPoint = new Point(topLeftX, topLeftY);
            myCircle.setTopLeftPoint(topLeftPoint);
            myCircle.setDiameter(a * 2);
            buffers[i] = myCircle;
        }
        return buffers;
    }

    public static Rectangle getRectangleBBox(Point[] points) {
        // get top left corner:
        // get min x , max y
        double topLeftX = getMinX(points);
        double topLeftY = getMinY(points);
        // get height, width
        // height ymax - ymin, width xmax - xmin
        double width = getMaxX(points) - topLeftX;
        double height = getMaxY(points) - topLeftY;
        // return Rect
        return new Rectangle(topLeftX, topLeftY, width, height);

    }

    public static double getMaxX(Point[] points) {
        double maxPointX = Double.MIN_VALUE;
        for (int i = 0; i < points.length; i++) {
            if (points[i].getX() > maxPointX) {
                maxPointX = points[i].getX();
            }
        }
        return maxPointX;
    }

    public static double getMaxY(Point[] points) {
        double maxPointY = Double.MIN_VALUE;
        for (int i = 0; i < points.length; i++) {
            if (points[i].getY() > maxPointY) {
                maxPointY = points[i].getY();
            }
        }
        return maxPointY;
    }

    public static double getMinX(Point[] points) {
        double minPointX = Double.MAX_VALUE;
        for (int i = 0; i < points.length; i++) {
            if (points[i].getX() < minPointX) {
                minPointX = points[i].getX();
            }
        }
        return minPointX;
    }

    public static double getMinY(Point[] points) {
        double minPointY = Double.MAX_VALUE;
        for (int i = 0; i < points.length; i++) {
            if (points[i].getY() < minPointY) {
                minPointY = points[i].getY();
            }
        }
        return minPointY;
    }

    // function to get max and min vals to normalize densities
    public static double getMax(double[] max) {
        double maxValue = max[0];
        for (int i = 0; i < max.length; i++) {
            if (max[i] > maxValue) {
                maxValue = max[i];
            }
        }
        return maxValue;
    }

    public static double getMin(double[] min) {
        double minValue = min[0];
        for (int i = 0; i < min.length; i++) {
            if (min[i] < minValue) {
                minValue = min[i];
            }
        }
        return minValue;
    }

    // function to convert to Float for HSB Color
    public static Float convertToFloat(double doubleValue) {
        return (float) doubleValue;
    }

    public static void dropTable(Statement st) {
        try {
            st.execute("DROP TABLE IF EXISTS cities ");
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    public static City[] getCityData() {
        // init cities because of the try catch block

        City[] cities = new City[0];

        try {
            // Connect to database
            String url = "jdbc:postgresql://localhost/";
            Connection conn = DriverManager.getConnection(url, "*****", "*****");
            System.out.println("Connected!");

            // create table
            Statement st = conn.createStatement();
            dropTable(st);
            st.execute(
                    "CREATE TABLE cities(name varchar(40), lat double precision, lon double precision, area real, population integer, population_density real, percent_foreign real, GDP real)");

            // insert data
            st.execute(
                    "INSERT INTO cities VALUES " + "('Berlin', 52.516667, 13.388889, 891.7, 6004857, 4100, 18.4, 130),"
                            + "('Amsterdam', 52.3667, 4.8945, 219.32, 866737, 5135, 50.5, 154),"
                            + "('Athens', 37.9838, 23.7275, 38.964, 664046, 7500, 9, 93.7),"
                            + "('Budapest', 47.4979, 19.0402, 525.2, 1750268, 3388, 4.4, 58.5),"
                            + "('Copenhagen', 55.6761, 12.5683, 178.46, 633021, 3500, 24, 127),"
                            + "('Lisbon', 38.7223, 9.1393, 100.05, 505526, 5500, 4, 72),"
                            + "('Ljubljana', 46.0569, 14.5058, 163.8, 292988, 1712, 9.2, 54.059),"
                            + "('Madrid', 40.4168, 3.7038, 604.3, 3223334, 5300, 17, 248.56),"
                            + "('Paris', 48.8566, 2.3522, 105.4, 2140526, 20000, 26.2, 724),"
                            + "('Rome', 41.9028, 12.4964, 1285, 2872800, 2236, 9.5, 166.8)"

            );

            // fetch the data again
            ResultSet rs = st.executeQuery(
                    "SELECT name, lat, lon, area, population, population_density, percent_foreign, GDP FROM cities ");

            // count the number of cities in table
            Statement stCount = conn.createStatement();
            ResultSet rsCount = stCount.executeQuery("SELECT COUNT(*) AS rowCount FROM Cities");
            rsCount.next();

            int noOfCities = rsCount.getInt("rowCount");
            cities = new City[noOfCities];

            // store city data into city object
            int count = 0;
            while (rs.next()) {
                City tempCity = new City();
                tempCity.setName(rs.getString("name"));
                tempCity.setLatLon(Float.parseFloat(rs.getString("lon")), Float.parseFloat(rs.getString("lat")));
                tempCity.setArea(Double.parseDouble(rs.getString("area")));
                tempCity.setPopulation(Integer.parseInt(rs.getString("population")));
                tempCity.setPopulationDensity(Integer.parseInt(rs.getString("population_density")));
                tempCity.setPercentOfForeignResidents(Double.parseDouble(rs.getString("percent_foreign")));
                tempCity.setGDP(Double.parseDouble(rs.getString("GDP")));

                cities[count++] = tempCity;
            }

            // display city data from city objects
            for (int i = 0; i < noOfCities; i++) {
                City tempCity = new City();
                tempCity = cities[i];
                System.out.print(tempCity.getName() + ", ");

                Point pt = tempCity.getLatLon();
                System.out.print(pt.getY() + " " + pt.getX() + ", ");

                System.out.print(tempCity.getArea() + ", ");
                System.out.print(tempCity.getPopulation() + ", ");
                System.out.print(tempCity.getPopulationDensity() + ", ");
                System.out.print(tempCity.getPercentOfForeignResidents() + ", ");
                System.out.println(tempCity.getGDP());
            }

            rs.close();
            st.close();
            rsCount.close();
            stCount.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return cities;
    }

}

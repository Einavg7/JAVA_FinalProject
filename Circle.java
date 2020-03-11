public class Circle extends Shape {
    private Point topLeftPoint; 
    private double diameter;

    // create constructers
    // empty
    public Circle() {
        topLeftPoint = new Point();
        diameter = 0;
    }

    // with params
    public Circle(double x, double y, double diameter) {
        this.topLeftPoint = new Point(x, y);
        this.diameter = diameter;
    }

    public void setTopLeftPoint(Point point) {
        topLeftPoint = point;
    }

    //fix names
    public Point getTopLeftPoint() {
        return topLeftPoint;
    }

    public void setDiameter(double d) {
        diameter = d;
    }

    public double getDiameter() {
        return diameter;
    }

    public double area() {
        return Math.PI * Math.pow(diameter, 2);
    }

    public double perimeter() {
        return Math.PI * diameter;
    }

    public boolean contains(Point contains) {
        if (contains.getX() > topLeftPoint.getX() && contains.getX() < topLeftPoint.getX() + diameter
                && contains.getY() > topLeftPoint.getY() && contains.getY() < topLeftPoint.getY() + diameter) {
            return true;
        } else {
            return false;
        }
    }

    public Point centroid() {
        double a = this.diameter/2;
        double xCenter = topLeftPoint.getX() + a;
        double yCenter = topLeftPoint.getY() + a;
        Point centroid = new Point(xCenter, yCenter);
        return centroid;
    }

    // toString()
    public String toString() {
        return "Circle (" + this.topLeftPoint.getX() + ", " + this.topLeftPoint.getY() + "), diameter: " + this.diameter;
    }

}

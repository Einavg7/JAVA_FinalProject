public class Rectangle extends Shape {
    private Point topLeftPoint;
    private double width;
    private double height;

    // create constructers
    // empty
    public Rectangle() {
        topLeftPoint = new Point();
        width = 0;
        height = 0;
    }

    // with params
    public Rectangle(double x, double y, double width, double height) {
        this.topLeftPoint = new Point(x, y);
        this.width = width;
        this.height = height;
    }

    public void setTopLeftPoint(Point point) {
        topLeftPoint = point;
    }

    public Point getTopLeftPoint() {
        return topLeftPoint;
    }

    //fix names
    public void setWidth(double w) {
        width = w;
    }

    public double getWidth() {
        return width;
    }

    //fix names
    public void setHeight(double h) {
        height = h;
    }

    public double getHeight() {
        return height;
    }

    public double area() {
        return this.width * this.height;
    }

    public double perimeter() {
        return (2 * this.height) + (2 * this.width);
    }

    public boolean contains(Point contains) {
        if (contains.getX() > topLeftPoint.getX() && contains.getX() < topLeftPoint.getX() + width
                && contains.getY() > topLeftPoint.getY() && contains.getY() < topLeftPoint.getY() + height) {
            return true;
        } else {
            return false;
        }
    }

    public Point centroid() {
        double xCenter = topLeftPoint.getX() + (0.5 * this.width);
        double yCenter = topLeftPoint.getY() + (0.5 * this.height);
        Point centroid = new Point(xCenter, yCenter);
        return centroid;

    }

    // toString()
    public String toString() {
        return "Rectangle (" + this.topLeftPoint.getX() + ", " + this.topLeftPoint.getY() + "), width: " + this.width
                + ", height: " + this.height;
    }
}

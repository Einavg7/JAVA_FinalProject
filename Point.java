public class Point extends Shape {
    private double x;
    private double y;

    // create constructers
    // empty
    public Point() {
        x = 0;
        y = 0;
    }

    // with params
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setX(double xCoord) {
        x = xCoord;
    }

    public double getX() {
        return x;
    }

    public void setY(double yCoord) {
        y = yCoord;
    }

    public double getY() {
        return y;
    }

    // set location
    public void setLocation(double x, double y) {
        setX(x);
        setY(y);
    }

    // set Distance
    public double distance(double x, double y) {
        return Math.sqrt(Math.pow(x - this.x, 2) + Math.pow(y - this.y, 2));
    }

    // toString()
    public String toString() {
        return "Point (" + this.x + ", " + this.y + ")";
    }
}

public class Polyline extends Shape {
    private Point[] points; // [Point,Point,...,Point]
    private int numPoints;

    public Polyline() {
        points = new Point[0];
        numPoints = 0;
    }

    public Polyline(Point[] points, int n) {
        this.points = points;
        this.numPoints = n;
    }

    public void setPoints(Point[] p) {
        points = p;
        setNumPoints(p.length);
    }

    public Point[] getPoints() {
        return points;
    }

    public void setNumPoints(int num) {
        numPoints = num;
    }

    public int getNumPoints() {
        return numPoints;
    }

}
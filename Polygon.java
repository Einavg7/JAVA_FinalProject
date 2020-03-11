public class Polygon extends Shape {
    private Point [] points;
    private int numPoints;

    public Polygon(){
        points = new Point[0];
        numPoints = 0;
    }

    public Polygon(Point [] points, int n){
        this.numPoints = n;
		this.points = points;
    }
 
    public void setPoints(Point[] p){
		points = p;
    }

    public Point [] getPoints(){
        return points;
    }

    public void setNumPoints(int num){
        numPoints = num;
    }

    public int getNumPoints(){
        return numPoints;
    }
}
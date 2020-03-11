import java.awt.Color;
public abstract class Shape {

    private double strokeWidth;
    private Color strokeColor;
    private Color fillColor; //Color fillcolor, Enum = {yellow, blue, black} OR Class
    private double transparency;

    // create an empty constructer
    public Shape() {
        strokeWidth = 3;
        strokeColor = new Color(0,0,0);
        fillColor = new Color(0,0,0);
    }

    // with params
    public Shape(double strokeWidth, Color strokeColor, Color fillColor) {
        this.strokeWidth = strokeWidth;
        this.strokeColor = strokeColor;
        this.fillColor = fillColor;
    }

    // getters and setters
    public void setStrokeWidth(double width) {
        strokeWidth = width;
    }

    public double getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeColor(Color color) {
        strokeColor = color;
    }

    public Color getStrokeColor() {
        return strokeColor;
    }

    public void setFillColor(Color fill) {
        fillColor = fill;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public void setTransperancy(double trans) {
        transparency = trans;
    }

    public double getTransperancy() {
        return transparency;
    }
}
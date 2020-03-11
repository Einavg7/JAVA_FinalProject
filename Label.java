public class Label extends Shape {
    private Point position;
    private String text;

    public Label() {
        position = new Point ();
        text = "";
    }

    public Label(double p1, double p2, String txt){
        this.position = new Point(p1, p2);
        this.text = txt;
    }

    public void setPosition(Point posi) {
        position = posi;
    }

    public Point getPosition() {
        return position;
    }

    public void setText(String tx) {
        text = tx;
    }

    public String getText() {
        return text;
    }
}
import java.awt.*;
public class sightLine {
    double degree;
    double awareness;
    cord[] cords;
    double x;
    double y;
    double xEnd;
    double yEnd;
    Color color;

    public sightLine(double degree, double awareness, double x, double y){
        this.degree = degree;
        this.awareness = awareness;
        this.x = x;
        this.y = y;
        cords = new cord[(int)awareness];
        makeLine();
        color = Color.black;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public cord[] getCords() {
        return cords;
    }

    public void setCords(cord[] cords) {
        this.cords = cords;
    }

    public double getAwareness() {
        return awareness;
    }
    public void setAwareness(double awareness) {
        this.awareness = awareness;
    }
    public double getDegree() {
        return degree;
    }
    public void setDegree(double degree) {
        this.degree = degree;
    }

    public void makeLine(){
        double radian = degree * 0.0174533;
        double xEnd = Math.sin(radian*awareness);
        double yEnd = Math.cos(radian*awareness);
        for (int i  = 0; i < awareness; i ++){
            double yCord = y + (Math.sin(radian)*i);
            double xCord = x + (Math.cos(radian)*i);
            cords[i] = new cord (xCord,yCord);
        }
    }
}

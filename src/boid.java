import java.awt.*;
import java.math.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class boid {
    Random random = new Random();
    Color color;
    double x;
    double y;
    double degree;
    double rLineX;
    double rLineY;
    double lLineX;
    double lLineY;
    int numLines = 15;
    double linesX[] = new double[numLines];
    double linesY[] = new double[numLines];

    public boid (Color color, double x, double y, double degree){
        this.color = color;
        this.x = x;
        this.y = y;
        this.degree = degree;
    }

    public void move(double dist){
        double xOffSet = 0;
        double yOffSet = 0;
        double rad = degree * 0.0174533;
        xOffSet = Math.cos(rad) * dist;
        yOffSet = Math.sin(rad) * dist;
        setY(y+yOffSet);
        setX(x+xOffSet);
    }



    public double separation(boid[] boids, cord[] cords, int myIndex) {
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        double width = size.width;
        int height = size.height;
        double awareness = 70;
        int fov = 90;
        double sep = fov / numLines;
        int pathsAvailable = 0;
        sightLine[] lines = new sightLine[numLines];


        for (int i = 0; i < numLines; i++) {
            double newDegree = degree + ((i * sep)) - (fov/2);
            sightLine prospect = new sightLine(newDegree, awareness, x, y);
            linesX[i] = prospect.cords[(int) awareness - 1].x;
            linesY[i] = prospect.cords[(int) awareness - 1].y;
            boolean collisionCheck1 = (prospect.cords[(int) awareness -1].y < height && prospect.cords[(int)awareness-1].x < width );
            boolean collisionCheck2 = (prospect.cords[(int) awareness -1].y > 0 && prospect.cords[(int)awareness-1].x > 0 );
            boolean canPass1 = collisionCheck1 && collisionCheck2;
            boolean canPass2 = true;

            for (int j = 0; j < cords.length; j++) {
                boolean collisionCheck3 = (cords[j].x < prospect.cords[(int) awareness - 1].x) && (cords[j].x > this.x);
                boolean collisionCheck4 = (cords[j].y < prospect.cords[(int) awareness - 1].y) && (cords[j].y > this.y);
                boolean collisionCheck5 = (cords[j].x > prospect.cords[(int) awareness - 1].x) && (cords[j].x < this.x);
                boolean collisionCheck6 = (cords[j].y > prospect.cords[(int) awareness - 1].y) && (cords[j].y < this.y);
                if (collisionCheck3 && collisionCheck4) {
                    canPass2 = false;
                }
                if (collisionCheck5 && collisionCheck6) {
                    canPass2 = false;
                }

            }
            if (canPass1 && canPass2) {
                lines[pathsAvailable] = prospect;
                pathsAvailable++;
            }
        }
            if (pathsAvailable == 0) {
                return degree;
            } else if (pathsAvailable == numLines) {
                return degree;
            } else {
                double newDeg = weightedRandomDegree(lines, pathsAvailable);
                return newDeg;
            }


    }


    public double weightedRandomDegree(sightLine[] lines, int pathsAvailable){
        double[] diffs = new double [pathsAvailable];
        for (int i = 0; i < pathsAvailable; i++) {
            int diff = Math.abs((int) lines[i].degree - (int) degree);
            diffs[i] = diff;
        }
        Arrays.sort(diffs);
        Random random = new Random();
        double sum = 0;
        for (int i = 0; i <pathsAvailable; i++){
            sum += diffs[i];
        }
        double rand = random.nextDouble() * sum;
        double result = 0;
        for (int i = 0; i < pathsAvailable; i++){
            if (rand>diffs[i]){
                result = diffs[i];
            }
            rand -= i;
        }
        return result;
    }


    public double alignment(boid[] boids){
        double sepFactor= 0.001;
        double degAvg = 0;
        for(int i = 0; i <boids.length; i++){
            degAvg+=boids[i].degree;
        }
        degAvg /= boids.length;
        degAvg %= 360;
        return degAvg;


    }

    public double cohesion(boid[] boids, Point p){

        double coFactor = 0.0001;
        double sumX = 0;
        double sumY = 0;
        for(int i = 0; i <boids.length; i++){
            sumX+=boids[i].x;
            sumY+=boids[i].y;
        }
        sumX/=boids.length;
        sumY/=boids.length;
        double x = p.y - this.y;
        double y = p.x - this.x;
        double X = sumY -this.y;
        double Y = sumX -this.x;
        double followMouse = Math.atan2(x,y);
        double newDeg = Math.atan2(X,Y);

       // double diff = newDeg - this.degree;
       // double diff = followMouse - this.degree;
        return (newDeg);



    }


    public void all3(boid[] boids, Point p, cord[] cords,int myIndex){
        double c = 0.0;
        double a = 0.0;
        double s = 3;
        double cohesion = cohesion(boids, p);
        double alignment = alignment(boids);
        double separation = separation(boids,cords,myIndex);
        setDegree(((separation*s)+(alignment*a)+(cohesion*c))/3);


    }



    public void setDegree(double degree){
        this.degree=degree;
    }
    public void setX(double x){
        this.x = x;
    }
    public void setY(double y){
        this.y = y;
    }

}

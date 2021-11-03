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
    int numLines = 15;
    double linesX[] = new double[numLines];
    double linesY[] = new double[numLines];
    sightLine allLines[];

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
    public double alignment(boid[] boids, int myIndex){
        double sum=0;
        for(int i =0; i<boids.length; i++){
            sum+=boids[i].degree;
        }
        sum/=boids.length;
        sum %=360;
        return sum;

    }

    public double cohesion(boid[] boids, Point p, int myIndex){
        double avgX = 0;
        double avgY = 0;
        for (int i = 0; i < boids.length; i++){
            avgX+=boids[i].x;
            avgY+=boids[i].y;
        }
        avgX/=boids.length;
        avgY/=boids.length;
        double returner = Math.atan2(avgY-y,avgX-x);
        returner*=1/0.0174533;
        return returner;
    }



    public double separation(boid[] boids, cord[] cords, int myIndex) {
        if (boids.length==0){
            return 0;
        }
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        double width = size.width;
        int height = size.height;
        double awareness = 90;
        int fov = 70;
        double sep = fov / numLines;
        int pathsAvailable = 0;
        sightLine[] lines = new sightLine[numLines];
        allLines = new sightLine[numLines];
        for (int i = 0; i < numLines; i++) {
            double newDegree = degree + ((i * sep)) - (fov/2);
            sightLine prospect = new sightLine(newDegree, awareness, x, y);
            allLines[i]=prospect;
            linesX[i] = prospect.cords[(int) awareness - 1].x;
            linesY[i] = prospect.cords[(int) awareness - 1].y;
            boolean collisionCheck1 = (prospect.cords[(int) awareness -1].y < height && prospect.cords[(int)awareness-1].y > 0 );
            boolean collisionCheck2 = (prospect.cords[(int) awareness -1].x < width && prospect.cords[(int)awareness-1].x > 0 );
            boolean canPass1 = collisionCheck1 && collisionCheck2;
            boolean canPass2 = true;

            for (int j = 0; j < cords.length; j++) {
                boolean collisionCheck3 = (cords[j].x < prospect.cords[(int) awareness - 1].x) && (cords[j].x > this.x); // in sight left X
                boolean collisionCheck4 = (cords[j].y < prospect.cords[(int) awareness - 1].y) && (cords[j].y > this.y); // in sight left Y
                boolean collisionCheck5 = (cords[j].x > prospect.cords[(int) awareness - 1].x) && (cords[j].x < this.x); // in sight right X
                boolean collisionCheck6 = (cords[j].y > prospect.cords[(int) awareness - 1].y) && (cords[j].y < this.y); // in sight right Y
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
                int evenOrOdd = random.nextInt(100);
                double returner;
                if(evenOrOdd%2==0){
                    returner = allLines[0].degree;
                } else {
                    returner = allLines[numLines-1].degree;
                }
                return returner;
            } else if (pathsAvailable == numLines) {
                return degree;
            } else {
                double newDeg = weightedRandomDegree(lines, pathsAvailable);

                return newDeg;
            }


    }
    public boid[] getNearest (boid[] allBoids, double radius, int myIndex){
        boid[] nearBoids;
        int counter = 0;
        for (int i = 0; i < allBoids.length; i++){
            if (isInside(x,y,radius,allBoids[i].x,allBoids[i].y) && i!=myIndex){
                counter++;
            }
        }
        nearBoids = new boid[counter];
        counter = 0;
        for (int i = 0; i < allBoids.length; i++){
            if (isInside(x,y,radius,allBoids[i].x,allBoids[i].y ) && i!=myIndex){
                nearBoids[counter] = allBoids[i];
                counter++;
            }
        }
        return nearBoids;

    }

    public static boolean isInside(double x, double y, double radius, double checkX, double checkY){
        if ((checkX - x) * (checkX - x) + (checkY - y) * (checkY - y) <= radius * radius)
            return true;
        else
            return false;
    }


    public double weightedRandomDegree(sightLine[] lines, int pathsAvailable){
        double[] diffs = new double[pathsAvailable];
        for(int i = 0; i < pathsAvailable; i++){
            diffs[i] = Math.abs(degree-lines[i].degree);
        }
        Arrays.sort(diffs);
        return degree-diffs[0];



    }

    public void all3(boid[] boids, Point p, cord[] cords, int myIndex){
        boid[] nearBoids = getNearest(boids,600,myIndex);
        double a = 0.0003;
        double c = 0.2;
        double s = 3.0;
        double alignment = 0;
        double cohesion = 0;
        if (nearBoids!=null){
            alignment = degree + alignment(nearBoids, myIndex);
            cohesion = cohesion(nearBoids, p, myIndex);

        }
        double separation = separation(boids,cords,myIndex);

        double average = ( ( (a*alignment)+
                             (c*cohesion)+
                             (s*separation))/3);
        setDegree(average);
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

    public String toString (){
        double rad = degree*0.0174533;
        return "[ "+x+" , "+y+" , "+degree+" ]";
    }
}

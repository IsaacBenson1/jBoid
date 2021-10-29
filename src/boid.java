import java.awt.*;
import java.math.*;

public class boid {
    Color color;
    double x;
    double y;
    double degree;
    public boid (Color color, double x, double y, double degree){
        this.color = color;
        this.x = x;
        this.y = y;
        this.degree = degree;
    }

    public void move(double dist){
        double xOffSet = 0;
        double yOffSet = 0;
        xOffSet = Math.cos(degree) * dist;
        yOffSet = Math.sin(degree) * dist;
        setY(y+yOffSet);
        setX(x+xOffSet);
    }

    public double separation(boid[] boids){
        return 2.2;
    }

    public double alignment(boid[] boids){
        double sepFactor= 0.001;
        double degAvg = 0;
        for(int i = 0; i <boids.length; i++){
            degAvg+=boids[i].degree;
        }
        degAvg /= boids.length;
        degAvg %= 360;
        setDegree(degAvg/2);
        return degAvg;


    }

    public double cohesion(boid[] boids){
        Point p = MouseInfo.getPointerInfo().getLocation();

        double coFactor = 0.0001;
        double sumX = 0;
        double sumY = 0;
        for(int i = 0; i <boids.length; i++){
            sumX+=boids[i].x;
            sumY+=boids[i].y;
        }
        sumX/=boids.length;
        sumY/=boids.length;
        System.out.println(sumX+" "+sumY);
        double x = p.x-this.x;
        double y = p.y-this.y;
        double X = sumX -this.x;
        double Y = sumY -this.y;
        double followMouse = Math.atan2(y,x);
        double newDeg = Math.atan2(Y,X);

       // double diff = newDeg - this.degree;
       // double diff = followMouse - this.degree;
        return (followMouse);



    }


    public void all3(boid[] boids){
        double c = 1.5;
        double a = 1;
        double s = 1;
        double cohesion = cohesion(boids);
     //   double alignment = alignment(boids);
        setDegree(((c*cohesion)));


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

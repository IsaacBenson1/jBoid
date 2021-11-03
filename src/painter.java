import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Random;

public class painter extends JPanel implements ActionListener {
    Random random = new Random();
    private Timer t = new Timer(1,this);
    Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
    boid myBoid = new boid(Color.black, size.width/2, size.height/2, 0);
    boid [] allBoids = new boid[100];
    Color [] colors = {Color.black,Color.white,Color.blue,Color.cyan,Color.orange,Color.red,Color.green,Color.yellow,Color.magenta};
    Color [] colors2 = {Color.black,Color.red};


    // TODO
    // random colors, random positions
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        this.setBackground(Color.gray);
        for (int i = 0; i < allBoids.length; i++){
            fillTriangle(allBoids[i],g2d, false);
        }
        fillTriangle(myBoid,g2d,false);
        t.start();
    }

    public void populateBoids(){
        for (int i = 0; i < allBoids.length; i++){
            int randColor = random.nextInt(colors.length);
            int randX = random.nextInt(size.width);
            int randY = random.nextInt(size.height);
            int randDeg = random.nextInt(360);
            allBoids[i] = new boid(colors[randColor],randX,randY, randDeg);
        }
    }

    public void actionPerformed(ActionEvent e) {
        Point p = MouseInfo.getPointerInfo().getLocation();
        cord[] cords = getCords(allBoids);
        for (int i = 0; i < allBoids.length; i++){
            allBoids[i].move(0.7);
            allBoids[i].all3(allBoids,p,cords,i);
        }
        myBoid.move(2);
        double y = size.getHeight()/2 - myBoid.y;
        double x = size.getWidth()/2 - myBoid.x;
        double degree = Math.atan2(x,y);
        myBoid.setDegree(2);
        repaint();
    }
    public cord[] getCords(boid[] boids){
        cord [] boidCords = new cord[boids.length];
        for (int i = 0; i < boids.length; i ++){
            boidCords[i] = new cord(boids[i].x,boids[i].y);
        }
        return boidCords;
    }

    public static void fillTriangle(boid boid, Graphics2D g2d, boolean drawSight){
        int scale = 10;
        double x1 = boid.x + (1*scale);
        double y1 = boid.y ;
        double x2 = boid.x - (1* scale);
        double y2 = boid.y + (0.5*scale);
        double x3 = boid.x - (1*scale);
        double y3 = boid.y - (0.5*scale);
        int[] xPoints = {(int)x1,(int)x2,(int)x3,};
        int[] yPoints = {(int)y1,(int)y2,(int)y3};
        Polygon myPoly = new Polygon(xPoints,yPoints,3);
        g2d.setColor(boid.color);
        double rad = boid.degree * 0.0174533;
        g2d.rotate(rad,boid.x,boid.y);
        g2d.fillPolygon(myPoly);
        g2d.rotate(-rad,boid.x,boid.y);
        if (drawSight) {
            for (int i = 0; i < boid.numLines; i++) {
                g2d.drawLine((int) boid.x, (int) boid.y, (int) boid.linesX[i], (int) boid.linesY[i]);
            }
        }
    }




}

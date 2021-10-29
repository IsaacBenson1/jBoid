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
    boid [] allBoids = new boid[1000];
    Color [] colors = {Color.black,Color.white,Color.blue,Color.cyan,Color.orange,Color.red,Color.green,Color.yellow,Color.magenta};

    // TODO
    // random colors, random positions
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        this.setBackground(Color.gray);
        for (int i = 0; i < allBoids.length; i++){
            fillTriangle(allBoids[i],g2d);
        }
        fillTriangle(myBoid,g2d);
        t.start();
    }

    public void populateBoids(){
        for (int i = 0; i < allBoids.length; i++){
            int randColor = random.nextInt(9);
            int randX = random.nextInt(size.width);
            int randY = random.nextInt(size.height);
            int randDeg = random.nextInt(360);
            allBoids[i] = new boid(colors[randColor],randX,randY, randDeg);
        }
    }

    public void actionPerformed(ActionEvent e) {
        Point p = MouseInfo.getPointerInfo().getLocation();
        for (int i = 0; i < allBoids.length; i++){
            allBoids[i].move(0.3);
            allBoids[i].all3(allBoids);
        }
        myBoid.move(0.4);
        double x = p.y - myBoid.y;
        double y = p.x - myBoid.x;
        myBoid.setDegree(Math.atan2(x,y));
        repaint();
    }

    public static void fillTriangle(boid boid, Graphics2D g2d){
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
        g2d.rotate(boid.degree,boid.x,boid.y);
        g2d.fillPolygon(myPoly);
        g2d.rotate(-boid.degree,boid.x,boid.y);




    }




}

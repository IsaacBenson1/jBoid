import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class frame extends JFrame  {
    painter p = new painter();
    frame(){
        p.populateBoids();
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        double height = size.getHeight();
        double width = size.getWidth();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize((int)width,(int)height);
        this.setTitle("jBoid");
        // this.setVisible(true);
    }
    public static void main(String args[]) {
        frame thisFrame = new frame();
        thisFrame.add(thisFrame.p);
        thisFrame.setVisible(true);
    }

}
import net.dzirt.*;

import javax.swing.*;
import java.awt.*;



/**
 * Created by Андрей on 01.06.2016.
 */
public class Main {
    public static void main(String[] args) {
        SnakeCanvas cnv = new SnakeCanvas();
        cnv.setFocusable(true);
        JFrame frame = new JFrame("SNAKE");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(cnv);
        frame.setSize(cnv.getSize());
        frame.setVisible(true);
        frame.pack();

       // cnv.DrawGrid(new Graphics());
    }
}

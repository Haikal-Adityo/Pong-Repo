
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Paddle extends Rectangle {
    static int paddleSpeed = 10;
    int id;

    Color red = new Color(242, 22, 70);
    Color blue = new Color(0, 102, 176);

    public Paddle(int paddleX, int paddleY, int paddleWidth, int paddleHeight, int id) {
        super(paddleX, paddleY, paddleWidth, paddleHeight);
        this.id = id;
    }


    public void draw(Graphics2D g) {

        if (id == 1) {
            g.setColor(blue);
        }
        else {
            g.setColor(red);
        }

        g.fillRect(x, y, width, height);
    }

}

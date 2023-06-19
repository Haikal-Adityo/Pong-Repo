package entity;

import main.KeyHandler;
import main.Main;

import java.awt.*;

public class Paddle extends Rectangle {

    public int paddleSpeed = Main.isFullScreen ? 15 : 10;;
    int id;

    Color red = new Color(242, 22, 70);
    Color blue = new Color(0, 102, 176);

    public Paddle(int paddleX, int paddleY, int paddleWidth, int paddleHeight, int id) {
        super(paddleX, paddleY, paddleWidth, paddleHeight);
        this.id = id;
    }

    public void draw(Graphics2D g) {

        g.setColor(id == 1 ? blue : red);
        g.fillRect(x, y, width, height);
    }

}

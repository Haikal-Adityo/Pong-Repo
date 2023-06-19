package entity;

import main.Main;

import java.awt.*;

public class Paddle extends Rectangle {

    private int paddleSpeed = Main.isFullScreen ? 15 : 10;;
    private final int id;

    Color red = new Color(242, 22, 70);
    Color blue = new Color(0, 102, 176);

    public Paddle(int paddleX, int paddleY, int paddleWidth, int paddleHeight, int id) {
        super(paddleX, paddleY, paddleWidth, paddleHeight);
        this.id = id;
    }

    public int getPaddleSpeed() {
        return paddleSpeed;
    }

    public void setPaddleSpeed(int paddleSpeed) {
        this.paddleSpeed = paddleSpeed;
    }

    public int getId() {
        return id;
    }

    public void draw(Graphics2D g) {

        g.setColor(id == 1 ? blue : red);
        g.fillRect(x, y, width, height);
    }

}

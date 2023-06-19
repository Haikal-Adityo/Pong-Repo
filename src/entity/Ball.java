package entity;

import java.awt.*;
import java.util.*;
import main.*;

public class Ball extends Rectangle {

    Random random;
    public int XVelocity;
    public int YVelocity;
    public int initialSpeed;

    Color white = new Color(254, 241, 209);

    public Ball(int ballX, int ballY, int ballWidth, int ballHeight) {
        super(ballX, ballY, ballWidth, ballHeight);
        random = new Random();

        if (GamePanel.gameDifficulty == GamePanel.easy) {
            initialSpeed = Main.isFullScreen ? 6 : 3;
        }
        if (GamePanel.gameDifficulty == GamePanel.normal) {
            initialSpeed =  Main.isFullScreen ? 10 : 5;
        }
        if (GamePanel.gameDifficulty == GamePanel.hard) {
            initialSpeed =  Main.isFullScreen ? 16 : 8;
        }

        int randomXDirection = random.nextBoolean() ? -1 : 1;
        setXDirection(randomXDirection * initialSpeed);

        int randomYDirection = random.nextBoolean() ? -1 : 1;
        setYDirection(randomYDirection * initialSpeed);

    }

    public void setXDirection(int randomXDirection) {
        XVelocity = randomXDirection;
    }

    public void setYDirection(int randomYDirection) {
        YVelocity = randomYDirection;
    }

    public void move() {
        x += XVelocity;
        y += YVelocity;
    }

    public void draw(Graphics g) {

        g.setColor(white);
        g.fillOval(x, y, width, height);

    }

    public void stop() {
        XVelocity = 0;
        YVelocity = 0;
    }
}

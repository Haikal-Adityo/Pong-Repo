package entity;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import main.*;

public class Ball extends Rectangle {

    Random random;
    public int XVelocity;
    public int YVelocity;
    public static int initialSpeed = Main.isFullScreen ? 6 : 3;

    Color white = new Color(254, 241, 209);
    Color yellow = new Color(255, 255, 51);
    Color red = new Color(225, 61, 12);

    public Ball(int ballX, int ballY, int ballWidth, int ballHeight) {
        super(ballX, ballY, ballWidth, ballHeight);
        random = new Random();

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

package main;

import data.DataStorage;
import data.SaveData;
import entity.Ball;
import entity.Paddle;
import main.Main;

import java.awt.*;
import java.util.Random;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable {

    // * SCREEN SETTINGS
    static int screenWidth = Main.isFullScreen ? Main.frame.getWidth() : 1000;
    static int screenHeight = Main.isFullScreen ? Main.frame.getHeight() : (int) (Main.screenWidth * (0.5555));
    static Dimension screenSize = new Dimension(screenWidth,screenHeight);
    static int ballDiameter = Main.isFullScreen ? 35 : 20;
    static int paddleWidth = Main.isFullScreen ? 40 : 25;
    static int paddleHeight = Main.isFullScreen ? GamePanel.screenHeight / 5 : 100;

    // * FPS
    int FPS = 60;

    // * SYSTEM
    KeyHandler keyH = new KeyHandler(this);
    Thread gameThread;
    Image image;
    Graphics graphics;
    Paddle paddle1;
    Paddle paddle2;
    Ball ball;
    public static Score score;
    GameOver over;
    Pause pause;
    Random random;
    static SaveData saveData = new SaveData();
    Color grey = new Color(17, 17, 17);
    Color green = new Color(125, 183, 86);

    // * GAME STATE
    public int gameState = 0;
    public static int playState = 1;
    public static int pauseState = 2;

    // * call paddle and ball
    public void newPaddle() {

        paddle1 = new Paddle(0, (screenHeight/2)-(paddleHeight/2), paddleWidth, paddleHeight, 1);
        paddle2 = new Paddle(screenWidth-paddleWidth, (screenHeight/2)-(paddleHeight/2), paddleWidth, paddleHeight, 2);
    }

    public void newBall() {
        ball = new Ball((screenWidth / 2) - (ballDiameter / 2), (screenHeight / 2) - (ballDiameter / 2), ballDiameter, ballDiameter);
    }

    public GamePanel() {

        newPaddle();
        newBall();

        gameState = playState;
        score = new Score();
        pause = new Pause(this);

        this.setPreferredSize(screenSize);
        this.setBackground(green);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread() {

//        saveData.load();

        gameThread = new Thread(this);
        gameThread.start();
    }

    public void stopGameThread() {

        History.add(String.valueOf(score));

        saveData.save();
        saveData.load();

        over = new GameOver();
        Main.gameFrame.dispose();

        gameThread = null;
        ball.stop();
        Score.player1 = 0;
        Score.player2 = 0;
    }

    //! 1 UPDATE : update information such as character position
    //! 2 DRAW : draw the screen with updated information

    @Override
    public void run() {
        double drawInterval = 1000000000.0 / FPS;
        double delta = 0.0;
        long lastTime = System.nanoTime();

        while (gameThread != null) {
            long currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1.0) {
                update();
                checkCollision();
                repaint();
                delta--;
            }
        }

    }

    public void update() {

        if (gameState == playState) {

            paddle1.y -= (keyH.WPressed) ? Paddle.paddleSpeed : 0;
            paddle1.y += (keyH.SPressed) ? Paddle.paddleSpeed : 0;
            paddle2.y -= (keyH.upPressed) ? Paddle.paddleSpeed : 0;
            paddle2.y += (keyH.downPressed) ? Paddle.paddleSpeed : 0;

            ball.move();
        }

    }

    public void checkCollision() {
        random = new Random();

        int addedX = random.nextInt(2);

        if (gameState == playState) {

            // * Stops paddle1 from going out of bounds
            paddle1.y = (paddle1.y <= 0) ? 0 : Math.min(paddle1.y, screenHeight - paddleHeight);

            // * Stops paddle2 from going out of bounds
            paddle2.y = (paddle2.y <= 0) ? 0 : Math.min(paddle2.y, screenHeight - paddleHeight);


        // * Ball collision with paddle
            // * Paddle1 w/ ball collision
            if (ball.intersects(paddle1)) {

                playSE(0);
//                ball.XVelocity = Math.abs(ball.XVelocity);
//                ball.XVelocity++; //increase speed
//
//                if (ball.YVelocity > 0) {
//                    ball.YVelocity++; //increase speed
//                } else {
//                    ball.YVelocity--;
//                }
                ball.XVelocity = Math.min(Math.abs(ball.XVelocity) + 1, 8); // Increase speed and limit XVelocity to a maximum of 5

                ball.YVelocity += (ball.YVelocity > 0) ? 1 : -1; // Increase or decrease speed based on YVelocity sign

                ball.setXDirection(ball.XVelocity); //! Normal
//                ball.setXDirection(ball.XVelocity + addedX); //! Hard
                ball.setYDirection(ball.YVelocity);

            }

            // * Paddle2 w/ ball collision
            if (ball.intersects(paddle2)) {

                playSE(0);

                ball.XVelocity = Math.min(Math.abs(ball.XVelocity) + 1, 8); // Increase speed and limit XVelocity to a maximum of 5

                ball.YVelocity += (ball.YVelocity > 0) ? 1 : -1; // Increase or decrease speed based on YVelocity sign

                ball.setXDirection(-ball.XVelocity); //! Normal
//                ball.setXDirection(-ball.XVelocity - addedX); //! Hard
                ball.setYDirection(ball.YVelocity);

            }

            // * Ball collision with top & bottom border
            if (ball.y <= 0 || ball.y >= screenHeight - ballDiameter) {
                playSE(1);
                ball.setYDirection(-ball.YVelocity);
            }

            // * Score update
            if (ball.x < 0 || ball.x > screenWidth - ballDiameter) {
                if (ball.x < 0) {
                    Score.player2++;
                } else {
                    Score.player1++;
                }
                playSE(2);
                newBall();
            }

            // * End the game
            if (Score.player1 == 3 || Score.player2 == 3) {
                GameOver.winnerId = Score.player1 == 3 ? 1 : 2;
                ball.XVelocity = 0;
                ball.YVelocity = 0;
                stopGameThread();
            }
        }

    }

    @Override
    public void paint(Graphics g) {

        image = createImage(getWidth(),getHeight());
        graphics = image.getGraphics();
        draw((Graphics2D) graphics);
        g.drawImage(image,0,0,this);
    }

    public void draw(Graphics2D g) {

        paddle1.draw(g);
        paddle2.draw(g);
        ball.draw(g);
        score.draw(g);
        if (gameState == pauseState) {
            pause.draw(g);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    public void playSE(int i) {

        Main.soundEffect.setFile(i);
        Main.soundEffect.play();
    }

}

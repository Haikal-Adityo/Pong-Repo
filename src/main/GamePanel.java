package main;

import data.SaveData;
import entity.Ball;
import entity.Paddle;

import java.awt.*;
import java.util.Random;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable {

    // * SCREEN SETTINGS
    static int screenWidth = 1000;
    static int screenHeight = (int) (Main.screenWidth * (0.5555));
    Dimension screenSize = new Dimension(screenWidth,screenHeight);
    static int ballDiameter = 20;
    static int paddleWidth = 25;
    static int paddleHeight = 100;

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
    Color green = new Color(125, 183, 86);

    // * GAME STATE
    public int gameState = 0;
    public static int playState = 1;
    public static int pauseState = 2;

    // * GAME DIFFICULTY
    public static int gameDifficulty = 0;
    public static final int easy = 1;
    public static final int normal = 2;
    public static final int hard = 3;

    // * call paddle and ball
    public void newPaddle() {
        paddle1 = new Paddle(0,(screenHeight/2)-(paddleHeight/2),paddleWidth,paddleHeight,1);
        paddle2 = new Paddle(screenWidth-paddleWidth,(screenHeight/2)-(paddleHeight/2),paddleWidth,paddleHeight,2);
    }

    public void newBall() {
        random = new Random();
        ball = new Ball((screenWidth/2)-(ballDiameter/2),random.nextInt(screenHeight-ballDiameter),ballDiameter,ballDiameter);
    }

    public GamePanel() {

        // * SCREEN SETTINGS
        screenWidth = Main.isFullScreen ? Main.frame.getWidth() : 1000;
        screenHeight = Main.isFullScreen ? Main.frame.getHeight() : (int) (Main.screenWidth * (0.5555));
        ballDiameter = Main.isFullScreen ? 30 : 20;
        paddleWidth = Main.isFullScreen ? 30 : 25;
        paddleHeight = Main.isFullScreen ? GamePanel.screenHeight / 7 : 100;

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

        gameThread = new Thread(this);
        gameThread.start();
    }

    public void stopGameThread() {

        History.add(String.valueOf(score));

        saveData.save();
        saveData.load();

        over = new GameOver();
        DifficultyFrame.gameFrame.dispose();

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

            paddle1.y -= (keyH.WPressed) ? paddle1.paddleSpeed : 0;
            paddle1.y += (keyH.SPressed) ? paddle1.paddleSpeed : 0;
            paddle2.y -= (keyH.upPressed) ? paddle2.paddleSpeed : 0;
            paddle2.y += (keyH.downPressed) ? paddle2.paddleSpeed : 0;

            ball.move();
        }

    }

    public void checkCollision() {

        if (gameState == playState) {

            // * Stops paddle1 from going out of bounds
            paddle1.y = (paddle1.y <= 0) ? 0 : Math.min(paddle1.y, screenHeight - paddleHeight);

            // * Stops paddle2 from going out of bounds
            paddle2.y = (paddle2.y <= 0) ? 0 : Math.min(paddle2.y, screenHeight - paddleHeight);


        // * Ball collision with paddle
            // * Paddle1 w/ ball collision
            if (ball.intersects(paddle1)) {
                playSE(0);

                int maxVelocity = switch (gameDifficulty) {
                    case easy -> 5;
                    case normal -> 10;
                    case hard -> Integer.MAX_VALUE;
                    default -> 0; // or handle an invalid game difficulty
                };

                ball.XVelocity = Math.min(Math.abs(ball.XVelocity) + 1, maxVelocity);
                ball.setXDirection(ball.XVelocity);

                ball.YVelocity += (ball.YVelocity > 0) ? 1 : -1;
                ball.setYDirection(ball.YVelocity);

                System.out.println("Ball speed: " + ball.XVelocity);
            }


            // * Paddle2 w/ ball collision
            if (ball.intersects(paddle2)) {
                playSE(0);

                int maxVelocity = switch (gameDifficulty) {
                    case easy -> 5;
                    case normal -> 10;
                    case hard -> Integer.MAX_VALUE;
                    default -> 0; // or handle an invalid game difficulty
                };

                ball.XVelocity = Math.min(Math.abs(ball.XVelocity) + 1, maxVelocity);
                ball.setXDirection(-ball.XVelocity);

                ball.YVelocity += (ball.YVelocity > 0) ? 1 : -1;
                ball.setYDirection(ball.YVelocity);

                System.out.println("Ball speed: " + ball.XVelocity);
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
            if (Score.player1 == 11 || Score.player2 == 11) {
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

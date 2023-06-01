

import java.awt.*;
import java.util.Random;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable {

    // * SCREEN SETTINGS
    static int screenWidth = 1000;
    static int screenHeight = (int)(screenWidth * (0.5555));
    static Dimension screenSize = new Dimension(screenWidth,screenHeight);
    static int ballDiameter = 20;
    static int paddleWidth = 25;
    static int paddleHeight = 100;

    // * FPS
    int FPS = 60;

    // * Obeject
    KeyHandler keyH = new KeyHandler(this);
    Thread gameThread;
    Image image;
    Graphics graphics;
    Paddle paddle1;
    Paddle paddle2;
    Ball ball;
    Score score;
    GameOver over;
    Pause pause;
    Random random;

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

        gameThread = new Thread(this);
        gameThread.start();
    }

    public void stopGameThread() {

        over = new GameOver();
        Main.gameFrame.dispose();

        gameThread = null;
        newBall();
        Score.player1 = 0;
        Score.player2 = 0;
    }

    //! 1 UPDATE : update information such as character position
    //! 2 DRAW : draw the screen with updated information

    @Override // * Gameloop the core of our game (Delta loop)
    public void run() {

        double drawInterval = (double) 1000000000 /FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                checkCollision();
                repaint();
                delta--;
            }

        }
    }

    public void update() {

        if (gameState == playState) {

            if (keyH.WPressed) {
                paddle1.y -= Paddle.paddleSpeed;
            }
            if (keyH.SPressed) {
                paddle1.y += Paddle.paddleSpeed;
            }

            if (keyH.upPressed) {
                paddle2.y -= Paddle.paddleSpeed;
            }
            if (keyH.downPressed) {
                paddle2.y += Paddle.paddleSpeed;
            }

            ball.move();
        }
        if (gameState == pauseState) {

        }

    }

    public void checkCollision() {
        random = new Random();

        int addedX = random.nextInt(2);

        if (gameState == playState) {
            // * stops paddle from going out of bounds
            if (paddle1.y <= 0) {
                paddle1.y = 0;
            }
            if (paddle1.y >= (screenHeight - paddleHeight)) {
                paddle1.y = screenHeight - paddleHeight;
            }

            if (paddle2.y <= 0) {
                paddle2.y = 0;
            }
            if (paddle2.y >= (screenHeight - paddleHeight)) {
                paddle2.y = screenHeight - paddleHeight;
            }

            // * ball collision with paddle

            // * paddle1 w/ ball collision
            if (ball.intersects(paddle1)) {

                playSE(0);
                ball.XVelocity = Math.abs(ball.XVelocity);
                ball.XVelocity += addedX; //increase speed
                if (ball.YVelocity > 0) {
                    ball.YVelocity++; //increase speed
                } else {
                    ball.YVelocity--;
                }
                ball.setXDirection(ball.XVelocity);
                ball.setYDirection(ball.YVelocity);
                ball.hitCounter++;
            }

            // * paddle2 w/ ball collision
            if (ball.intersects(paddle2)) {

                playSE(0);
                ball.XVelocity = Math.abs(ball.XVelocity);
                ball.XVelocity += addedX; //increase speed

                if (ball.YVelocity > 0) {
                    ball.YVelocity++; //increase speed
                } else {
                    ball.YVelocity--;
                }
                ball.setXDirection(-ball.XVelocity);
                ball.setYDirection(ball.YVelocity);
                ball.hitCounter++;
            }

            // * ball collision with top & bottom border
            if (ball.y <= 0) {
                playSE(1);
                ball.setYDirection(-ball.YVelocity);
            }
            if (ball.y >= screenHeight - ball.diameter) {
                playSE(1);
                ball.setYDirection(-ball.YVelocity);
            }

            // * score update
            if (ball.x < 0) {
                Score.player2++;
                playSE(2);
                newBall();
                System.out.println(score);
            }

            if (ball.x > screenWidth - ballDiameter) {
                Score.player1++;
                playSE(2);
                newBall();
                System.out.println(score);
            }

            // * End game
            if (Score.player1 == 3) {
                GameOver.winnerId = 1;
                ball.XVelocity = 0;
                ball.YVelocity = 0;

                stopGameThread();

                System.out.println("==================");
                System.out.println("= Player 1 Wins! =");
                System.out.println("==================");
            }

            if (Score.player2 == 3) {
                GameOver.winnerId = 2;
                ball.XVelocity = 0;
                ball.YVelocity = 0;

                stopGameThread();

                System.out.println("==================");
                System.out.println("= Player 2 Wins! =");
                System.out.println("==================");
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

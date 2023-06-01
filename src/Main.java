
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Main extends JFrame {

    static final int screenWidth = 1000;
    static final int screenHeight = (int)(screenWidth * (0.5555));
    static final Dimension screenSize = new Dimension(screenWidth,screenHeight);
    static GameFrame gameFrame;
    static SettingFrame settingFrame;
    static JFrame frame;

    static Music music = new Music();
    static SoundEffect soundEffect = new SoundEffect();

    static boolean isFullScreen = false;
    static boolean fullScreenClicked = false;

    static Font pixelType;
    Color grey = new Color(17, 17, 17);

    public static void main(String[] args) {
        new Main();
        playMusic(0);
    }

    public Main() {

        try {
            pixelType = Font.createFont(Font.TRUETYPE_FONT, new File("font/Pixeltype.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("font/Pixeltype.ttf")));
        } catch (IOException | FontFormatException ignored) {
        }

        frame = new JFrame();
        frame.setTitle("PONG");
        frame.setSize(screenSize);
        frame.setBackground(grey);

        JPanel gameMenu = new JPanel();
        gameMenu.setBorder(new EmptyBorder(5, 5, 5, 5));
        gameMenu.setLayout(new BorderLayout());

        gameMenu.setBackground(grey);

        JPanel layout = new JPanel(new GridBagLayout());
        layout.setBorder(new EmptyBorder(5, 5, 45, 5));
        layout.setBackground(grey);

        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(grey);

        // * Game Title
        JLabel gameTitle = new JLabel("PONG", SwingConstants.CENTER);
        gameTitle.setForeground(Color.white);
        gameTitle.setFont(new Font(pixelType.getName(), Font.BOLD, 200));
        gameMenu.add(gameTitle, BorderLayout.CENTER);

        // * Start button
        JButton startButton = new JButton("START GAME");
        startButton.setFont(new Font(pixelType.getName(), Font.BOLD, 25));
        startButton.setBackground(grey);
        startButton.setForeground(Color.WHITE);
        startButton.setFocusable(false);
        startButton.addActionListener(actionEvent -> {

            soundEffect.setFile(3);
            soundEffect.play();

            frame.dispose();
            gameFrame = new GameFrame();
            if (isFullScreen) {
                gameFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            }
        });

        // * SETTING MENU BUTTON
        JButton settingButton = new JButton("OPTIONS");
        settingButton.setFont(new Font(pixelType.getName(), Font.BOLD, 25));
        settingButton.setBackground(grey);
        settingButton.setForeground(Color.WHITE);
        settingButton.setFocusable(false);
        settingButton.addActionListener(actionEvent -> {

            soundEffect.setFile(3);
            soundEffect.play();

            frame.dispose();

            settingFrame = new SettingFrame();
            if (isFullScreen) {
                settingFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            }
        });

        // * Quit button
        JButton quitButton = new JButton("QUIT");
        quitButton.setFont(new Font(pixelType.getName(), Font.BOLD, 25));
        quitButton.setBackground(grey);
        quitButton.setForeground(Color.WHITE);
        quitButton.setFocusable(false);
        quitButton.addActionListener(actionEvent -> {
            soundEffect.setFile(3);
            soundEffect.play();

            frame.dispose();
            stopMusic();
        });

        // * fullscreen button
        JButton fullButton = new JButton("FULL SCREEN");
        fullButton.setFont(new Font("Dialog", Font.BOLD, 15));
        fullButton.setBackground(grey);
        fullButton.setForeground(Color.WHITE);
        fullButton.setFocusable(false);
        fullButton.addActionListener(actionEvent -> {
            soundEffect.setFile(3);
            soundEffect.play();

            fullScreenClicked = !fullScreenClicked;

            if (fullScreenClicked) {

                isFullScreen = true;
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

                // * set the gamepanel
                GamePanel.screenWidth = frame.getWidth();
                GamePanel.screenHeight = frame.getHeight();

                GamePanel.ballDiameter = 35;
                GamePanel.paddleWidth = 40;
                GamePanel.paddleHeight = GamePanel.screenHeight / 5;
                Paddle.paddleSpeed = 15;
                Ball.initialSpeed = 100;

                // * size change
                gameTitle.setFont(new Font(pixelType.getName(), Font.BOLD, 200));
                btnPanel.setLayout(new GridLayout(4, 1, 10, 30));
                startButton.setPreferredSize(new Dimension(300, 100));
                quitButton.setPreferredSize(new Dimension(300, 100));
                fullButton.setPreferredSize(new Dimension(300, 100));
            } else { //! Not fullscreen

                isFullScreen = false;
                frame.setSize(screenSize);
                frame.setLocationRelativeTo(null);

                // * set the gamepanel
                GamePanel.screenWidth = 1000;
                GamePanel.screenHeight = (int) (screenWidth * (0.5555));

                GamePanel.ballDiameter = 20;
                GamePanel.paddleWidth = 25;
                GamePanel.paddleHeight = 100;
                Paddle.paddleSpeed = 10;
                Ball.initialSpeed = 50;
                // * size change
                gameTitle.setFont(new Font(pixelType.getName(), Font.BOLD, 100));
                btnPanel.setLayout(new GridLayout(3, 1, 10, 20));
                startButton.setPreferredSize(new Dimension(250, 50));
                quitButton.setPreferredSize(new Dimension(250, 50));
                fullButton.setPreferredSize(new Dimension(250, 50));
            }

        });

        btnPanel.add(startButton);
        btnPanel.add(settingButton);
//        btnPanel.add(fullButton);
        btnPanel.add(quitButton);
        layout.add(btnPanel);

        gameMenu.add(layout, BorderLayout.SOUTH);

        if (isFullScreen) {
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            gameTitle.setFont(new Font(pixelType.getName(), Font.BOLD, 200));
            btnPanel.setLayout(new GridLayout(4, 1, 10, 30));
            startButton.setPreferredSize(new Dimension(300, 100));
            quitButton.setPreferredSize(new Dimension(300, 100));
            fullButton.setPreferredSize(new Dimension(300, 100));
        } else {
            frame.setSize(screenSize);
            gameTitle.setFont(new Font(pixelType.getName(), Font.BOLD, 100));
            btnPanel.setLayout(new GridLayout(3, 1, 10, 20));
            startButton.setPreferredSize(new Dimension(200, 55));
            quitButton.setPreferredSize(new Dimension(200, 55));
            fullButton.setPreferredSize(new Dimension(200, 55));
        }

        frame.add(gameMenu);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    public static void playMusic(int i) {

        music.setFile(i);
        music.play();
        music.loop();


    }

    public void stopMusic() {
        music.stop();
    }

}

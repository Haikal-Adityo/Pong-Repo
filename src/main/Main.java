package main;

import data.DataStorage;
import data.SaveData;
import entity.Ball;
import entity.Paddle;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import static main.GamePanel.saveData;

public class Main extends JFrame {

    public static final int screenWidth = 1000;
    public static final int screenHeight = (int)(screenWidth * (0.5555));
    public static final Dimension screenSize = new Dimension(screenWidth,screenHeight);
    public static GameFrame gameFrame;
    public static SettingFrame settingFrame;
    public static JFrame frame;
    public static DifficultyFrame difficultyFrame;

    static Music music = new Music();
    static SoundEffect soundEffect = new SoundEffect();

    public static boolean isFullScreen = false;
    public static boolean fullScreenClicked = false;

    static Font pixelType;
    Color grey = new Color(17, 17, 17);

    public static void main(String[] args) {
        new Main();
        GamePanel.saveData.load();
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
        frame.setBackground(grey);
        frame.setTitle("PONG");
        frame.setSize(screenSize);

        JPanel gameMenu = new JPanel();
        gameMenu.setBorder(new EmptyBorder(5, 5, 5, 5));
        gameMenu.setLayout(new BorderLayout());

        gameMenu.setBackground(grey);

        JPanel layout = new JPanel(new GridBagLayout());
        layout.setBorder(new EmptyBorder(5, 5, 45, 5));
        layout.setBackground(grey);

        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new GridLayout(2, 2, 20, 20));
        btnPanel.setBackground(grey);

        // * Game Title
        JLabel gameTitle = new JLabel("PONG", SwingConstants.CENTER);
        gameTitle.setForeground(Color.white);
        gameMenu.add(gameTitle, BorderLayout.CENTER);
        gameTitle.setFont(new Font(pixelType.getName(), Font.BOLD, isFullScreen ? 200 : 100));

        // * Start button
        JButton startButton = new JButton("START GAME");
        startButton.setPreferredSize(new Dimension(200, 55));
        startButton.setFont(new Font(pixelType.getName(), Font.BOLD, isFullScreen ? 30 : 25));
        startButton.setBackground(grey);
        startButton.setForeground(Color.WHITE);
        startButton.setFocusable(false);
        startButton.addActionListener(actionEvent -> {

            playSE(3);

            frame.dispose();

            difficultyFrame = new DifficultyFrame();

            if (isFullScreen) {
                difficultyFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            }

        });

        // * SETTING MENU BUTTON
        JButton settingButton = new JButton("OPTIONS");
        settingButton.setPreferredSize(new Dimension(200, 55));
        settingButton.setFont(new Font(pixelType.getName(), Font.BOLD, isFullScreen ? 30 : 25));
        settingButton.setBackground(grey);
        settingButton.setForeground(Color.WHITE);
        settingButton.setFocusable(false);
        settingButton.addActionListener(actionEvent -> {

            playSE(3);

            frame.dispose();

            settingFrame = new SettingFrame();
            if (isFullScreen) {
                settingFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            }
        });

        // * HISTORY BUTTON
        JButton historyButton = new JButton("HISTORY");
        historyButton.setFont(new Font(pixelType.getName(), Font.BOLD, isFullScreen ? 30 : 25));
        historyButton.setBackground(grey);
        historyButton.setForeground(Color.WHITE);
        historyButton.setFocusable(false);
        historyButton.addActionListener(actionEvent -> {

            playSE(3);

            new History();
            frame.dispose();
        });

        // * BACK BUTTON
        JButton quitButton = new JButton("QUIT");
        quitButton.setPreferredSize(new Dimension(200, 55));
        quitButton.setFont(new Font(pixelType.getName(), Font.BOLD, isFullScreen ? 30 : 25));
        quitButton.setBackground(grey);
        quitButton.setForeground(Color.WHITE);
        quitButton.setFocusable(false);
        quitButton.addActionListener(actionEvent -> {

            playSE(3);

            frame.dispose();

            System.out.println("THANK YOU FOR PLAYING!");
        });

        btnPanel.add(startButton);
        btnPanel.add(settingButton);
        btnPanel.add(historyButton);
        btnPanel.add(quitButton);
        layout.add(btnPanel);

        gameMenu.add(layout, BorderLayout.SOUTH);

        if (isFullScreen) {
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        }

        frame.add(gameMenu);
        frame.setUndecorated(true);
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

    public void playSE(int i) {

        soundEffect.setFile(i);
        soundEffect.play();
    }


}

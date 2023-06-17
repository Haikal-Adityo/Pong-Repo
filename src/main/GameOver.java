package main;

import data.SaveData;
import main.GameFrame;
import main.Main;
import main.SoundEffect;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class GameOver extends JFrame {

    static int winnerId;
    String winner;
    Main main;
    GameFrame gameFrame;
    DifficultyFrame difficultyFrame;

    SoundEffect soundEffect = new SoundEffect();

    public GameOver () {

        Color grey = new Color(17, 17, 17);

        JFrame frame = new JFrame();
        frame.setBackground(grey);
        frame.setTitle("PONG");
        frame.setSize(Main.screenSize);

        JPanel gameMenu = new JPanel();
        gameMenu.setBorder(new EmptyBorder(5, 5, 5, 5));
        gameMenu.setLayout(new BorderLayout());
        gameMenu.setBackground(grey);

        JPanel layout = new JPanel(new GridBagLayout());
        layout.setBorder(new EmptyBorder(5, 5, 45, 5));
        layout.setBackground(grey);

        JPanel btnPanel = new JPanel(new GridLayout(3, 1, 10, 20));
        btnPanel.setBackground(grey);

        // * Game Title
        winner = (winnerId == 1) ? "PLAYER 1 WIN!" : "PLAYER 2 WIN!";

        JLabel gameWinner = new JLabel(winner, SwingConstants.CENTER);
        gameWinner.setForeground(Color.white);
        gameWinner.setFont(new Font(Main.pixelType.getName(), Font.BOLD, Main.isFullScreen ? 200 : 100));
        gameMenu.add(gameWinner, BorderLayout.CENTER);

        // * START AGAIN BUTTON
        JButton startButton = new JButton("START AGAIN");
        startButton.setFont(new Font(Main.pixelType.getName(), Font.BOLD, Main.isFullScreen ? 30 : 25));
        startButton.setPreferredSize(new Dimension(200, 55));
        startButton.setBackground(grey);
        startButton.setForeground(Color.WHITE);
        startButton.setFocusable(false);
        startButton.addActionListener(actionEvent -> {

            playSE(3);
            GamePanel.saveData.load();

            frame.dispose();
            difficultyFrame = new DifficultyFrame();
            if (Main.isFullScreen) {
                difficultyFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            }
        });

        // * Menu button
        JButton backToMenuButton = new JButton("BACK TO MENU");
        backToMenuButton.setFont(new Font(Main.pixelType.getName(), Font.BOLD, Main.isFullScreen ? 30 : 25));
        backToMenuButton.setPreferredSize(new Dimension(200, 55));
        backToMenuButton.setBackground(grey);
        backToMenuButton.setForeground(Color.WHITE);
        backToMenuButton.setFocusable(false);
        backToMenuButton.addActionListener(actionEvent -> {

            playSE(3);

            frame.dispose();
            main = new Main();
        });

        // * Quit button
        JButton quitButton = new JButton("QUIT");
        quitButton.setFont(new Font(Main.pixelType.getName(), Font.BOLD, Main.isFullScreen ? 30 : 25));
        quitButton.setPreferredSize(new Dimension(200, 55));
        quitButton.setBackground(grey);
        quitButton.setForeground(Color.WHITE);
        quitButton.setFocusable(false);
        quitButton.addActionListener(actionEvent -> {

            playSE(3);

            frame.dispose();
            System.out.println("THANK YOU FOR PLAYING");
        });

        btnPanel.add(startButton);
        btnPanel.add(backToMenuButton);
        btnPanel.add(quitButton);
        layout.add(btnPanel);

        gameMenu.add(layout, BorderLayout.SOUTH);

        if (Main.isFullScreen) {
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        }

        frame.add(gameMenu);
        frame.setUndecorated(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    public void playSE(int i) {

        soundEffect.setFile(i);
        soundEffect.play();
    }
}

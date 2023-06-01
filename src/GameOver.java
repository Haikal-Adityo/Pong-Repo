import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class GameOver extends JFrame {
    static final int screenWidth = 1000;
    static final int screenHeight = (int)(screenWidth * (0.5555));
    static final Dimension screenSize = new Dimension(screenWidth,screenHeight);
    static int winnerId;
    String winner;
    Main main;

    SoundEffect soundEffect = new SoundEffect();

    public GameOver () {

        Color grey = new Color(17, 17, 17);

        JFrame frame = new JFrame();
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

        JPanel btnPanel = new JPanel(new GridLayout(3, 1, 10, 20));
        btnPanel.setBackground(grey);

        // * Game Title
        if (winnerId == 1) {
            winner = "PLAYER 1 WIN!";
        }
        else if (winnerId == 2) {
            winner = "PLAYER 2 WIN!";
        }

        JLabel gameWinner = new JLabel(winner, SwingConstants.CENTER);
        gameWinner.setForeground(Color.white);
        gameWinner.setFont(new Font(Main.pixelType.getName(), Font.BOLD, 200));
        gameMenu.add(gameWinner, BorderLayout.CENTER);

        // * Start button
        JButton startButton = new JButton("START AGAIN");
        startButton.setFont(new Font(Main.pixelType.getName(), Font.BOLD, 25));
        startButton.setBackground(grey);
        startButton.setForeground(Color.WHITE);
        startButton.setFocusable(false);
        startButton.addActionListener(actionEvent -> {

            soundEffect.setFile(3);
            soundEffect.play();

            frame.dispose();
            Main.gameFrame = new GameFrame();
            if (Main.isFullScreen) {
                Main.gameFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            }
        });

        // * Menu button
        JButton backToMenuButton = new JButton("BACK TO MENU");
        backToMenuButton.setFont(new Font(Main.pixelType.getName(), Font.BOLD, 25));
        backToMenuButton.setBackground(grey);
        backToMenuButton.setForeground(Color.WHITE);
        backToMenuButton.setFocusable(false);
        backToMenuButton.addActionListener(actionEvent -> {

            soundEffect.setFile(3);
            soundEffect.play();

            frame.dispose();
            main = new Main();
        });

        // * Quit button
        JButton quitButton = new JButton("QUIT");
        quitButton.setFont(new Font(Main.pixelType.getName(), Font.BOLD, 25));
        quitButton.setBackground(grey);
        quitButton.setForeground(Color.WHITE);
        quitButton.setFocusable(false);
        quitButton.addActionListener(actionEvent -> {

            soundEffect.setFile(3);
            soundEffect.play();

            frame.dispose();
        });

        btnPanel.add(startButton);
        btnPanel.add(backToMenuButton);
        btnPanel.add(quitButton);
        layout.add(btnPanel);

        gameMenu.add(layout, BorderLayout.SOUTH);

        if (Main.isFullScreen) {
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            gameWinner.setFont(new Font(Main.pixelType.getName(), Font.BOLD, 200));
            btnPanel.setLayout(new GridLayout(4, 1, 10, 30));
            startButton.setPreferredSize(new Dimension(300, 100));
            backToMenuButton.setPreferredSize(new Dimension(250, 100));
            quitButton.setPreferredSize(new Dimension(300, 100));
        }
        else {
            frame.setSize(screenSize);
            gameWinner.setFont(new Font(Main.pixelType.getName(), Font.BOLD, 100));
            btnPanel.setLayout(new GridLayout(3, 1, 10, 20));
            startButton.setPreferredSize(new Dimension(200, 55));
            backToMenuButton.setPreferredSize(new Dimension(200, 55));
            quitButton.setPreferredSize(new Dimension(200, 55));
        }

        frame.add(gameMenu);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
}

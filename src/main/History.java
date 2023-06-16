package main;

import data.DataStorage;
import data.SaveData;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Arrays;

public class History extends JFrame{

    public static String[] winnerList = new String[5];
    public static int count = 0;

    Main main;
    SoundEffect soundEffect = new SoundEffect();
    GridBagConstraints gbc = new GridBagConstraints();

    Color grey = new Color(17, 17, 17);

    public History() {

        // * Create the frame
        JFrame frame = new JFrame();
        frame.setBackground(grey);
        frame.setTitle("PONG");
        frame.setSize(Main.screenSize);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        // * Create the panel
        JPanel panel = new JPanel();
        panel.setBackground(grey);
        panel.setBorder(new EmptyBorder(50, 5, 45, 5));
        panel.setLayout(new BorderLayout());

        // * Create the history label
        JLabel historyLabel = new JLabel("GAME HISTORY", SwingConstants.CENTER);
        historyLabel.setForeground(Color.white);
        historyLabel.setFont(new Font(Main.pixelType.getName(), Font.BOLD, 100));
        panel.add(historyLabel, BorderLayout.NORTH);

        // * Create the layout panel for winner labels
        JPanel layout = new JPanel(new GridBagLayout());
        layout.setBorder(new EmptyBorder(5, 5, 2, 5));
        layout.setBackground(grey);

        // * Create the winner panel
        JPanel winnerPanel = new JPanel();
        winnerPanel.setLayout(new GridLayout(5, 1, 10, 20));
        winnerPanel.setBackground(grey);

        // * Add winner labels
        GamePanel.saveData.load();
        for (String winner : winnerList) {
            JLabel label = new JLabel(winner, SwingConstants.CENTER);
            label.setFont(new Font(Main.pixelType.getName(), Font.BOLD, Main.isFullScreen ? 70 : 45));
            label.setForeground(Color.white);
            winnerPanel.add(label);
        }

        // * BACK BUTTON
        JButton backButton = new JButton("BACK");
        backButton.setPreferredSize(new Dimension(200, 55));
        backButton.setFont(new Font(Main.pixelType.getName(), Font.BOLD, Main.isFullScreen ? 30 : 25));
        backButton.setBackground(grey);
        backButton.setForeground(Color.WHITE);
        backButton.setFocusable(false);
        backButton.addActionListener(actionEvent -> {

            playSE(3);

            frame.dispose();
            main = new Main();

        });

        if (Main.isFullScreen) {
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        }

        panel.add(layout, BorderLayout.CENTER);
        gbc.insets.top = Main.isFullScreen ? 50 : 30;
        layout.add(winnerPanel, gbc);
        gbc.gridy = 1;
        layout.add(backButton, gbc);

        frame.add(panel);
        frame.setUndecorated(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void add(String txt) {
        System.out.println("Winner added to the winner list " + count);

        if (count >= winnerList.length) {
            System.arraycopy(winnerList, 0, winnerList, 1, winnerList.length - 1);
            winnerList[0] = txt;
        } else {
            System.arraycopy(winnerList, 0, winnerList, 1, count);
            winnerList[0] = txt;
            count++;
        }
    }

    public void playSE(int i) {

        soundEffect.setFile(i);
        soundEffect.play();
    }

}

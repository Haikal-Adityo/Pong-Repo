package main;

import main.GamePanel;

import java.awt.*;
import javax.swing.*;

public class GameFrame extends JFrame {

    Color green = new Color(125, 183, 86);

    public GameFrame() {

        GamePanel gamePanel = new GamePanel();
        this.add(gamePanel);
        this.pack();

        gamePanel.startGameThread(); //! Run game

        this.setBackground(green);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }

}

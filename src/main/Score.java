package main;

import java.awt.*;

public class Score {

    static int gameWidth;
    static int gameHeight;
    static int player1;
    static int player2;

    Color white = new Color(251, 255, 246);

    public Score() {
        gameWidth = GamePanel.screenWidth;
        gameHeight = GamePanel.screenHeight;
    }

    public void draw(Graphics2D g) {
        g.setColor(white);

        int fontSize = Main.isFullScreen ? 100 : 60;
        int player1X = Main.isFullScreen ? (gameWidth / 2) - 100 : (gameWidth / 2) - 60;
        int player2X = Main.isFullScreen ? (gameWidth / 2) + 40 : (gameWidth / 2) + 20;

        g.setFont(new Font(Main.pixelType.getName(), Font.PLAIN, fontSize));
        g.drawString(String.valueOf(player1 / 10) + String.valueOf(player1 % 10), player1X, 50);
        g.drawString(String.valueOf(player2 / 10) + String.valueOf(player2 % 10), player2X, 50);

        Stroke dashed = new BasicStroke(4, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{25}, 30);
        g.setStroke(dashed);
        g.drawLine(gameWidth / 2, 0, gameWidth / 2, gameHeight);
    }


    @Override
    public String toString() {
        return "Player1    " + player1 + " : " + player2 + "    Player2";
    }
}

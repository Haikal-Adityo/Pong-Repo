package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean WPressed, SPressed;
    public boolean upPressed, downPressed;

    GamePanel gp;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override //! Not Used
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        switch (code) {
            case KeyEvent.VK_W -> WPressed = true;
            case KeyEvent.VK_S -> SPressed = true;
            case KeyEvent.VK_UP -> upPressed = true;
            case KeyEvent.VK_DOWN -> downPressed = true;
            case KeyEvent.VK_ESCAPE -> {
                if (gp.gameState == GamePanel.playState) {
                    gp.gameState = GamePanel.pauseState;
                    Main.music.pause();
                    playSE(3);
                } else if (gp.gameState == GamePanel.pauseState) {
                    gp.gameState = GamePanel.playState;
                    Main.music.resume();
                    playSE(3);
                }
            }
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        switch (code) {
            case KeyEvent.VK_W -> WPressed = false;
            case KeyEvent.VK_S -> SPressed = false;
            case KeyEvent.VK_UP -> upPressed = false;
            case KeyEvent.VK_DOWN -> downPressed = false;
        }

    }

    public void playSE(int i) {

        Main.soundEffect.setFile(i);
        Main.soundEffect.play();
    }
}

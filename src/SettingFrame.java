

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SettingFrame extends JFrame implements ChangeListener {
    static final int screenWidth = 1000;
    static final int screenHeight = (int)(screenWidth * (0.5555));
    static final Dimension screenSize = new Dimension(screenWidth,screenHeight);
    Color grey = new Color(17, 17, 17);

    static JLabel musicLabel;
    static JLabel soundEffectLabel;
    static JSlider musicSlider;
    static JSlider soundEffectSlider;

    Main main;
    SoundEffect soundEffect = new SoundEffect();

    public static boolean easy;
    public static boolean normal;
    public static boolean hard;

    public SettingFrame() {

        JFrame frame = new JFrame();
        frame.setTitle("PONG");
        frame.setSize(screenSize);
        frame.setBackground(grey);

        // * setting menu
        JPanel settingMenu = new JPanel();
        settingMenu.setBorder(new EmptyBorder(50, 5, 45, 5));
        settingMenu.setLayout(new BorderLayout());
        settingMenu.setBackground(grey);

        JLabel settingTitle = new JLabel("SETTING", SwingConstants.CENTER);
        settingTitle.setForeground(Color.white);
        settingTitle.setFont(new Font(Main.pixelType.getName(), Font.BOLD, 80));
        settingMenu.add(settingTitle, BorderLayout.NORTH);

        // * SETTINGS LAYOUT
        JPanel layout = new JPanel(new GridBagLayout());
        layout.setBorder(new EmptyBorder(5, 5, 45, 5));
        layout.setBackground(grey);

        // * SLIDERS PANEL
        JPanel sliderPanel = new JPanel(new GridLayout(2, 2, 50, 30));
        sliderPanel.setBackground(grey);

        musicLabel = new JLabel("GAME MUSIC ", SwingConstants.LEFT);
        musicLabel.setForeground(Color.white);
        musicLabel.setFont(new Font(Main.pixelType.getName(), Font.PLAIN, 50));
        sliderPanel.add(musicLabel);

        musicSlider = new JSlider(-40, 6);
        musicSlider.setValue((int) Music.currentVolume);
        if (musicSlider.getValue() <= -40.0) {
            musicSlider.setValue(-40);
        }
        musicSlider.setSize(new Dimension(400, 200));
        musicSlider.setBackground(grey);
        musicSlider.setForeground(Color.white);
        musicSlider.addChangeListener(e -> {

            Music.currentVolume = musicSlider.getValue();
            if (Music.currentVolume == -40) {
                Music.currentVolume = -80;
                musicSlider.setValue(-40);
            }
            Music.fc.setValue(Music.currentVolume);
        });
        sliderPanel.add(musicSlider);

        soundEffectLabel = new JLabel("SOUND EFFECTS", SwingConstants.LEFT);
        soundEffectLabel.setForeground(Color.white);
        soundEffectLabel.setFont(new Font(Main.pixelType.getName(), Font.PLAIN, 50));
        sliderPanel.add(soundEffectLabel);


        soundEffectSlider = new JSlider(-40, 6);
        soundEffectSlider.setValue((int) SoundEffect.currentVolume);
        if (soundEffectSlider.getValue() <= -40.0) {
            soundEffectSlider.setValue(-40);
        }
        soundEffectSlider.setSize(new Dimension(400, 200));
        soundEffectSlider.setBackground(grey);
        soundEffectSlider.addChangeListener(e -> {

            SoundEffect.currentVolume = soundEffectSlider.getValue();
            if (SoundEffect.currentVolume == -40) {
                SoundEffect.currentVolume = -80;

            }
            SoundEffect.fc.setValue(SoundEffect.currentVolume);
        });
        sliderPanel.add(soundEffectSlider);

        // * SETTINGS PANEL
        JPanel settingPanel = new JPanel(new GridLayout(2, 1, 50, 30));
        settingPanel.setBackground(grey);

        // * FULL SCREEN BUTTON
        JButton fullButton = new JButton("FULL");
        fullButton.setFont(new Font("Dialog", Font.BOLD, 15));
        fullButton.setBackground(grey);
        fullButton.setForeground(Color.WHITE);
        fullButton.setFocusable(false);
        fullButton.addActionListener(actionEvent -> {
            soundEffect.setFile(3);
            soundEffect.play();

            Main.fullScreenClicked = !Main.fullScreenClicked;

            if (Main.fullScreenClicked) {

                Main.isFullScreen = true;
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

                // * set the gamepanel
                GamePanel.screenWidth = frame.getWidth();
                GamePanel.screenHeight = frame.getHeight();

                GamePanel.ballDiameter = 35;
                GamePanel.paddleWidth = 40;
                GamePanel.paddleHeight = GamePanel.screenHeight / 5;
                Paddle.paddleSpeed = 15;
                Ball.initialSpeed = 10;

                // * size change
//                gameTitle.setFont(new Font(pixelType.getName(), Font.BOLD, 200));
//                btnPanel.setLayout(new GridLayout(4, 1, 10, 30));
//                startButton.setPreferredSize(new Dimension(300, 100));
//                quitButton.setPreferredSize(new Dimension(300, 100));
//                fullButton.setPreferredSize(new Dimension(300, 100));
            } else { //! Not fullscreen

                Main.isFullScreen = false;
                frame.setSize(screenSize);
                frame.setLocationRelativeTo(null);

                // * set the gamepanel
                GamePanel.screenWidth = 1000;
                GamePanel.screenHeight = (int) (screenWidth * (0.5555));

                GamePanel.ballDiameter = 20;
                GamePanel.paddleWidth = 25;
                GamePanel.paddleHeight = 100;
                Paddle.paddleSpeed = 10;
                Ball.initialSpeed = 5;
                // * size change
//                gameTitle.setFont(new Font(pixelType.getName(), Font.BOLD, 100));
//                btnPanel.setLayout(new GridLayout(3, 1, 10, 20));
//                startButton.setPreferredSize(new Dimension(250, 50));
//                quitButton.setPreferredSize(new Dimension(250, 50));
//                fullButton.setPreferredSize(new Dimension(250, 50));
            }

        });
        settingPanel.add(fullButton);


        // * BACK BUTTON
        JButton backButton = new JButton("x");
        backButton.setFont(new Font(Main.pixelType.getName(), Font.BOLD, 25));
        backButton.setBackground(grey);
        backButton.setForeground(Color.WHITE);
        backButton.setFocusable(false);
        backButton.addActionListener(actionEvent -> {

            soundEffect.setFile(3);
            soundEffect.play();

            frame.dispose();
            main = new Main();
        });
        settingPanel.add(backButton);


        if (Main.isFullScreen) {
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        }
        else {
            frame.setSize(screenSize);
        }

        settingMenu.add(layout, BorderLayout.CENTER);
        layout.add(sliderPanel);
        layout.add(settingPanel);

        frame.add(settingMenu);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    @Override
    public void stateChanged(ChangeEvent e) {
    }

}

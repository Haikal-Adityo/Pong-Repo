package main;

import entity.Ball;
import entity.Paddle;
import main.GamePanel;
import main.Main;
import main.Music;
import main.SoundEffect;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SettingFrame extends JFrame {
    Color grey = new Color(17, 17, 17);

    Main main;
    SoundEffect soundEffect = new SoundEffect();
    public static boolean musicIsMuted = false;
    public static boolean soundIsMuted = false;

    public static boolean easy;
    public static boolean normal;
    public static boolean hard;

    ImageIcon xIcon;
    ImageIcon checkIcon;

    GridBagConstraints gbc = new GridBagConstraints();

    public SettingFrame() {

//        xIcon = new ImageIcon("icons/cross.png");
//        checkIcon = new ImageIcon("icons/check.png");

        JFrame frame = new JFrame();
        frame.setBackground(grey);
        frame.setTitle("PONG");
        frame.setSize(Main.screenSize);

        // * setting menu
        JPanel settingMenu = new JPanel();
        settingMenu.setBorder(new EmptyBorder(50, 5, 5, 5));
        settingMenu.setLayout(new BorderLayout());
        settingMenu.setBackground(grey);

        JLabel settingTitle = new JLabel("SETTING", SwingConstants.CENTER);
        settingTitle.setForeground(Color.white);
        settingTitle.setFont(new Font(Main.pixelType.getName(), Font.BOLD, 80));
        settingMenu.add(settingTitle, BorderLayout.NORTH);

        // * SETTINGS LAYOUT
        JPanel layout = new JPanel(new GridBagLayout());
        layout.setBorder(new EmptyBorder(5, 5, 5, 5));
        layout.setBackground(grey);

        // * SLIDERS PANEL
        JPanel soundPanel = new JPanel(new GridLayout(3, 2, 50, 30));
        soundPanel.setBackground(grey);

        // * MUSIC
        JLabel musicLabel = new JLabel("GAME MUSIC ", SwingConstants.LEFT);
        musicLabel.setForeground(Color.white);
        musicLabel.setFont(new Font(Main.pixelType.getName(), Font.PLAIN, 50));
        soundPanel.add(musicLabel);

        JSlider musicSlider = new JSlider(-40, 6);
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
        soundPanel.add(musicSlider);

        JCheckBox musicCheckBox = new  JCheckBox();
        musicCheckBox.setPreferredSize(new Dimension(100, 50));
        musicCheckBox.setText("Music");
        musicCheckBox.setForeground(Color.white);
        musicCheckBox.setBackground(grey);
        musicCheckBox.setFocusable(false);
        musicCheckBox.setFont(new Font(Main.pixelType.getName(), Font.PLAIN, 50));
        musicCheckBox.addActionListener(e -> {

            playSE(3);

            musicIsMuted = !musicIsMuted;
            Music.volumeMute();
        } );
        if (musicIsMuted) {
            musicCheckBox.setSelected(true);
        }

        // * SOUND EFFECTS
        JLabel soundEffectLabel = new JLabel("SOUND EFFECTS", SwingConstants.LEFT);
        soundEffectLabel.setForeground(Color.white);
        soundEffectLabel.setFont(new Font(Main.pixelType.getName(), Font.PLAIN, 50));
        soundPanel.add(soundEffectLabel);

        JSlider soundEffectSlider = new JSlider(-40, 6);
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
        soundPanel.add(soundEffectSlider);

        JCheckBox soundEffectCheckBox = new  JCheckBox();
        soundEffectCheckBox.setPreferredSize(new Dimension(100, 50));
        soundEffectCheckBox.setText("Sound Effect");
        soundEffectCheckBox.setForeground(Color.white);
        soundEffectCheckBox.setBackground(grey);
        soundEffectCheckBox.setFocusable(false);
        soundEffectCheckBox.setFont(new Font(Main.pixelType.getName(), Font.PLAIN, 50));
        soundEffectCheckBox.addActionListener(e -> {

            playSE(3);

            soundIsMuted = !soundIsMuted;
            SoundEffect.volumeMute();
        } );
        if (soundIsMuted) {
            soundEffectCheckBox.setSelected(true);
        }
        soundPanel.add(musicCheckBox);
        soundPanel.add(soundEffectCheckBox);


        // * SETTINGS PANEL
        JPanel settingPanel = new JPanel(new GridLayout(2, 1, 50, 50));
        settingPanel.setBackground(grey);

        // * FULL SCREEN BUTTON
        JButton fullButton = new JButton("FULL SCREEN");
        fullButton.setFont(new Font(Main.pixelType.getName(), Font.PLAIN, Main.isFullScreen ? 30 : 25));
        fullButton.setSize(new Dimension(200, 55));
        fullButton.setBackground(grey);
        fullButton.setForeground(Color.WHITE);
        fullButton.setFocusable(false);
        fullButton.addActionListener(actionEvent -> {

            playSE(3);

            Main.fullScreenClicked = !Main.fullScreenClicked;
            System.out.println(frame.getWidth() + frame.getHeight());

            if (Main.fullScreenClicked) {
                Main.isFullScreen = true;
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            } else { //! Not fullscreen
                Main.isFullScreen = false;
                frame.setSize(Main.screenSize);
                frame.setLocationRelativeTo(null);
            }

        });
        settingPanel.add(fullButton);


        // * BACK BUTTON
        JButton backButton = new JButton("BACK");
        backButton.setFont(new Font(Main.pixelType.getName(), Font.BOLD, Main.isFullScreen ? 30 : 25));
        backButton.setPreferredSize(new Dimension(200, 55));
        backButton.setBackground(grey);
        backButton.setForeground(Color.WHITE);
        backButton.setFocusable(false);
        backButton.addActionListener(actionEvent -> {

            playSE(3);

            frame.dispose();
            main = new Main();
        });
        settingPanel.add(backButton);

        if (Main.isFullScreen) {
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        }

        settingMenu.add(layout, BorderLayout.CENTER);
        gbc.insets.top = 30;
        layout.add(soundPanel, gbc);
        gbc.gridy = 1;
        layout.add(settingPanel, gbc);

        frame.add(settingMenu);
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

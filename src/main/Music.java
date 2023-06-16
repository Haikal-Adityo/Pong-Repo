package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;

public class Music {

    Clip clip;
    File[] soundURL = new File[10];
    static FloatControl fc;
    static float previousVolume = 0;
    static float currentVolume = -17;
    static boolean mute = false;
    static long clipTimePosition;

    public Music() {

        String[] soundPaths = {
                "sound/BGM.wav"
        };

        File[] soundFiles = new File[soundPaths.length];
        for (int i = 0; i < soundPaths.length; i++) {
            soundFiles[i] = new File(soundPaths[i]);
            soundURL[i] = soundFiles[i];
        }
    }

    public void setFile(int i) {

        try {
            AudioInputStream is = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(is);
            fc = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
            fc.setValue(currentVolume);

        } catch (Exception ignored) {

        }
    }

    public void play() {

        clip.start();
    }

    public void loop() {

        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {

        clip.stop();
    }

    public void pause() {
        clipTimePosition = clip.getMicrosecondPosition();
        clip.stop();
    }

    public void resume() {
        clip.setMicrosecondPosition(clipTimePosition);
        clip.start();
    }

    public static void volumeMute() {
        if (!mute) {
            previousVolume = currentVolume;
            currentVolume = -80.0f;
            fc.setValue(currentVolume);
            mute = true;
            System.out.println("Muted");
        }
        else {
            currentVolume = previousVolume;
            fc.setValue(currentVolume);
            mute = false;
            System.out.println("Not muted");
        }
    }

}


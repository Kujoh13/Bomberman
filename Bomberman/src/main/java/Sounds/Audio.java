package Sounds;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Audio {
    private static AudioInputStream audioInputStream;
    private static Clip clip;
    public static String bgm = "src//main//resources//audio//bgm.wav";
    public static String collect_item = "src//main//resources//audio//collect_item.wav";
    public static String enemy_die = "src//main//resources//audio//enemy_die.wav";
    public static String explosion = "src//main//resources//audio//explosion.wav";
    public static String lose = "src//main//resources//audio//lose.wav";
    public static String win = "src//main//resources//audio//win.wav";
    public static String portal = "src//main//resources//audio//portal.wav";
    public static String bomb_fuse = "src//main//resources//audio//bomb_fuse.wav";
    public static String bomb_countdown = "src//main//resources//audio//bomb_countdown.wav";

    public static void playEffect(String fileName) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(fileName).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch(Exception e) {
            System.out.println("Error playing sound effect");
            e.printStackTrace();
        }
    }

    public static void playMusic(String fileName) {
        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File(fileName).getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch(Exception e) {
            System.out.println("Error playing music");
            e.printStackTrace();
        }
    }

    public static void stopMusic() {
        try {
            clip.stop();
        } catch (Exception e) {
            System.out.println("Error stopping music");
            e.printStackTrace();
        }
    }
}

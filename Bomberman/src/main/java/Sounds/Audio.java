package Sounds;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class Audio {
    public static MediaPlayer background_music = new MediaPlayer(new Media("src//main//resources//audio//bgm_1.wav"));
    public static MediaPlayer click = new MediaPlayer(new Media("src//main//resources//audio//click.wav"));
    public static MediaPlayer enemy_die = new MediaPlayer(new Media("src//main//resources//audio//enemy_die.wav"));
    public static MediaPlayer collect_item = new MediaPlayer(new Media("src//main//resources//audio//collect_item.wav"));
    public static MediaPlayer explosion = new MediaPlayer(new Media("src//main//resources//audio//explosion.wav"));
    public static MediaPlayer lose = new MediaPlayer(new Media("src//main//resources//audio//lose.wav"));
    public static MediaPlayer win = new MediaPlayer(new Media("src//main//resources//audio//win.wav"));
    public static MediaPlayer portal = new MediaPlayer(new Media("src//main//resources//audio//portal.wav"));

    private Media media(String fileName) {
        return new Media(new File(fileName).toURI().toString());
    }
}

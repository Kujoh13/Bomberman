package GameObject.NonMovingObjects;

import GameObject.GameObject;
import Main.Bomberman;
import javafx.scene.image.Image;

public class Explosion extends GameObject {
    private int timer = 60;

    public Explosion(int x, int y, Image img) {
        super(x, y, img);
    }

    public void update() {
        timer--;
        if (timer == 0) {
            Bomberman.stillObjects.remove(this);
        }
    }
}

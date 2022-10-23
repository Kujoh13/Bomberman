package GameObject.NonMovingObjects;

import GameObject.GameObject;
import javafx.scene.image.Image;

public class Wall extends GameObject {
    public Wall(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {

    }
}

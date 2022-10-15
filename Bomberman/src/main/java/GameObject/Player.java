package GameObject;

import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;

public class Player extends GameObject {
    public static int velocity = 4;
    public Player() {

    }
    public Player(int x, int y, Image img) {
        super(x, y, img);
    }
    @Override
    public void update() {

    }

    public void moveUp() {
        y -= velocity;
    }

    public void moveDown() {
        y += velocity;
    }

    public void moveLeft() {
        x -= velocity;
    }

    public void moveRight() {
        x += velocity;
    }
}

package GameObject.MovingObjects;

import GameObject.MovingObjects.Enemy;
import Main.Bomberman;
import javafx.scene.image.Image;

import java.util.Random;

public class Enemy4 extends Enemy {
    public Enemy4(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        Random random = new Random();

        boolean addX = random.nextBoolean();
        boolean addY = random.nextBoolean();

        int newX = x + ((addX) ? 1 : -1);
        int newY = x + ((addY) ? 1 : -1);

        if (newX < Bomberman.WIDTH && newX >= 0
                && newY < Bomberman.HEIGHT && newY >= 0) {
            x = newX;
            y = newY;
        }
    }
}

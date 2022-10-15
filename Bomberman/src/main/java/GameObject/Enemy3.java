package GameObject;

import Main.Bomberman;
import javafx.scene.image.Image;

import java.util.Random;

public class Enemy3 extends Enemy {

    public Enemy3(int x, int y, Image img) {
        super(x, y, img);
    }

    /** To move the same as the number 2 but can be separated into 2 Enemy2's. */
    @Override
    public void update() {
        Random random = new Random();

        boolean addX = random.nextBoolean();
        boolean addY = random.nextBoolean();

        int newX = x + ((addX) ? 1 : -1);
        int newY = x + ((addY) ? 1 : -1);

        if (newX <= Bomberman.WIDTH && newX >= 1
                && newY <= Bomberman.HEIGHT && newY >= 1
                && Bomberman.map[newX][newY] == 2) {
            x = newX;
            y = newY;
        }
    }
}

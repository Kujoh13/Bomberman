package GameObject;

import Main.Bomberman;
import javafx.scene.image.Image;

import java.util.Random;

public class Enemy2 extends Enemy{

    public Enemy2(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        Random random = new Random();

        boolean addX = random.nextBoolean();
        boolean addY = random.nextBoolean();

        int newX = x + ((addX) ? 1 : -1);
        int newY = x + ((addY) ? 1 : -1);

        if (newX <= Bomberman.WIDTH && newX >= 1
                && newY <= Bomberman.HEIGHT && newY >= 1
                && map[newX][newY] == 2) {
            x = newX;
            y = newY;
        }
    }
}

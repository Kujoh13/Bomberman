package GameObject.MovingObjects;

import Main.Bomberman;
import javafx.scene.image.Image;

import java.util.Random;

public class Enemy4 extends Enemy {

    private final Random random = new Random();
    private int addX;
    private int addY;
    public Enemy4(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        super.update();
        if (fitSquare()) {
            addX = random.nextInt(3) - 1;
            if (addX == 0) {
                addY = random.nextBoolean() ? -1 : 1;
            } else {
                addY = 0;
            }

            if (inMap(x + addX * velocity, y + addY * velocity)) {
                x += addX * velocity;
                y += addY * velocity;
            }
        } else {
            x += addX * velocity;
            y += addY * velocity;
        }
    }
}

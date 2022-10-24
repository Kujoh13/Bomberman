package GameObject.MovingObjects;

import GameObject.GameObject;
import Main.Bomberman;

import javafx.scene.image.Image;

import java.util.Random;

public class Enemy1 extends Enemy {

    Random random = new Random();
    /** To check if the enemy goes horizontally or vertically. */
    private final boolean horizontal = random.nextBoolean();
    /** To find out the direction the enemy will start with. */
    private final boolean add = random.nextBoolean();

    public Enemy1(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        int dif = (add) ? 1 : -1;

        for (GameObject o : Bomberman.stillObjects) {
            if (!collision(o)) {
                x += dif * velocity;
            }
        }
    }
}

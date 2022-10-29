package GameObject.MovingObjects;

import GameObject.GameObject;
import GameObject.NonMovingObjects.BreakableWall;
import GameObject.NonMovingObjects.Wall;
import Main.Bomberman;

import javafx.scene.image.Image;

import java.util.Random;

public class Enemy1 extends Enemy {

    Random random = new Random();
    /** To check if the enemy goes horizontally or vertically. */
    private final boolean horizontal = random.nextBoolean();
    /** To find out the direction the enemy will start with. */
    private int dif = (random.nextBoolean()) ? 1 : -1;

    public Enemy1(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        boolean collide = false;
        for (GameObject o : Bomberman.stillObjects) {
            int newX = horizontal ? x + dif * velocity : x;
            int newY = horizontal ? y : y + dif * velocity;
            if ((o instanceof Wall || o instanceof BreakableWall)
                 && o.collision(newX, newY)) {
                collide = true;
                break;
            }
        }

        if (collide) {
            dif = -dif;
        } else {
            if (horizontal) {
                x += dif * velocity;
            } else {
                y += dif * velocity;
            }
        }
    }
}

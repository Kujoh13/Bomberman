package GameObject.MovingObjects;

import GameObject.GameObject;
import GameObject.NonMovingObjects.BreakableWall;
import GameObject.NonMovingObjects.Wall;
import Main.Bomberman;
import javafx.scene.image.Image;

import java.util.Random;

public class Enemy2 extends Enemy {
    private Random random = new Random();
    private int addX;
    private int addY;
    public int timer = 35;
    public Enemy2(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        super.update();
        timer--;
        if (fitSquare()) {
            addX = random.nextInt(3) - 1;
            if (addX == 0) {
                addY = random.nextBoolean() ? -1 : 1;
            } else {
                addY = 0;
            }

            int newX = x + addX * velocity;
            int newY = y + addY * velocity;
            boolean collide = false;
            for (GameObject o : Bomberman.stillObjects) {
                if ((o instanceof Wall || o instanceof BreakableWall)
                        && o.collision(newX, newY)) {
                    collide = true;
                    break;
                }
            }

            if (!collide) {
                x += addX * velocity;
                y += addY * velocity;
            }
        } else {
            x += addX * velocity;
            y += addY * velocity;
        }
    }
}

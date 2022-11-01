package GameObject.MovingObjects;

import GameObject.GameObject;
import GameObject.NonMovingObjects.Brick;
import GameObject.NonMovingObjects.Wall;
import Graphics.Sprite;
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
        dead = Sprite.balloon_dead;
    }

    @Override
    public void update() {
        super.update();
        int prevX = x;
        if (isDead) return;
        boolean collide = false;
        int newX = horizontal ? x + dif * velocity : x;
        int newY = horizontal ? y : y + dif * velocity;

        collide = touchBomb(newX, newY);
        for (GameObject o : Bomberman.stillObjects) {
            if ((o instanceof Wall || o instanceof Brick)
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
        if (prevX < x) {
            img = Sprite.movingSprite(Sprite.balloon_right1, Sprite.balloon_right2, Sprite.balloon_right2, Bomberman.animate, 30).getFxImage();
        }
        if (prevX > x) {
            img = Sprite.movingSprite(Sprite.balloon_left1, Sprite.balloon_left2, Sprite.balloon_left2, Bomberman.animate, 30).getFxImage();
        }
    }
}

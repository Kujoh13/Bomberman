package GameObject.MovingObjects;

import GameObject.GameObject;
import GameObject.NonMovingObjects.Brick;
import GameObject.NonMovingObjects.Wall;
import Graphics.Sprite;
import Main.Bomberman;
import javafx.scene.image.Image;

import java.util.Random;

public class Enemy2 extends Enemy {
    private final Random random = new Random();
    private int addX;
    private int addY;
    public Enemy2(int x, int y, Image img) {
        super(x, y, img);
        dead = Sprite.oneal_dead;
    }

    @Override
    public void update() {
        super.update();
        if (isDead) return;
        int prevX = x;
        if (fitSquare()) {
            addX = random.nextInt(3) - 1;
            if (addX == 0) {
                addY = random.nextBoolean() ? -1 : 1;
            } else {
                addY = 0;
            }

            int newX = x + addX * velocity;
            int newY = y + addY * velocity;
            boolean collide = touchBomb(newX, newY);;
            for (GameObject o : Bomberman.stillObjects) {
                if ((o instanceof Wall || o instanceof Brick)
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

        if (prevX < x) {
            img = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right2, Bomberman.animate, 30).getFxImage();
        }
        if (prevX > x) {
            img = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left2, Bomberman.animate, 30).getFxImage();
        }
    }
}

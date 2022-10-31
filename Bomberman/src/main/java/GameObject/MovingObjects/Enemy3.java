package GameObject.MovingObjects;

import GameObject.GameObject;
import GameObject.NonMovingObjects.BreakableWall;
import GameObject.NonMovingObjects.Wall;
import Graphics.Sprite;
import Main.Bomberman;
import javafx.scene.image.Image;

import java.util.Random;

public class Enemy3 extends Enemy {
    private Random random = new Random();
    private int addX;
    private int addY;
    public Enemy3(int x, int y, Image img) {
        super(x, y, img);
    }

    private void modifyPosition() {
        x += addX * velocity;
        y += addY * velocity;
    }

    public void randomMove() {
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
            modifyPosition();
        }
    }

    @Override
    public void update() {
        super.update();
        
        if (fitSquare()) {
            int curX = x / Sprite.SCALED_SIZE;
            int curY = y / Sprite.SCALED_SIZE;

            boolean seePlayer = false;
            int newX, newY;
            for (int i = curX - 1; i > 0; i--) {
                newX = i * Sprite.SCALED_SIZE;
                boolean collide = false;
                for (GameObject o : Bomberman.stillObjects) {
                    if ((o instanceof Wall || o instanceof BreakableWall)
                            && o.collision(newX, y)) {
                        collide = true;
                        break;
                    }
                }

                if (collide) {
                    break;
                }

                if (Bomberman.player.collision(newX, y)) {
                    addX = -1;
                    addY = 0;
                    modifyPosition();
                    return;
                }
            }

            for (int i = curX + 1; i < Bomberman.WIDTH; i++) {
                newX = i * Sprite.SCALED_SIZE;
                boolean collide = false;
                for (GameObject o : Bomberman.stillObjects) {
                    if ((o instanceof Wall || o instanceof BreakableWall)
                            && o.collision(newX, y)) {
                        collide = true;
                        break;
                    }
                }

                if (collide) {
                    break;
                }

                if (Bomberman.player.collision(newX, y)) {
                    addX = 1;
                    addY = 0;
                    modifyPosition();
                    return;
                }
            }

            for (int i = curY - 1; i > 0; i--) {
                newY = i * Sprite.SCALED_SIZE;
                boolean collide = false;
                for (GameObject o : Bomberman.stillObjects) {
                    if ((o instanceof Wall || o instanceof BreakableWall)
                            && o.collision(x, newY)) {
                        collide = true;
                        break;
                    }
                }

                if (collide) {
                    break;
                }

                if (Bomberman.player.collision(x, newY)) {
                    addX = 0;
                    addY = -1;
                    modifyPosition();
                    return;
                }
            }

            for (int i = curY + 1; i < Bomberman.HEIGHT; i++) {
                newY = i * Sprite.SCALED_SIZE;
                boolean collide = false;
                for (GameObject o : Bomberman.stillObjects) {
                    if ((o instanceof Wall || o instanceof BreakableWall)
                            && o.collision(x, newY)) {
                        collide = true;
                        break;
                    }
                }

                if (collide) {
                    break;
                }

                if (Bomberman.player.collision(x, newY)) {
                    addX = 0;
                    addY = 1;
                    modifyPosition();
                    return;
                }
            }

            randomMove();
        }
        else {
            modifyPosition();
        }
    }
}

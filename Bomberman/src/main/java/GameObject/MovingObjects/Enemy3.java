package GameObject.MovingObjects;

import GameObject.GameObject;
import GameObject.NonMovingObjects.Brick;
import GameObject.NonMovingObjects.Wall;
import Graphics.Sprite;
import Main.Bomberman;
import javafx.scene.image.Image;

import java.util.Random;

public class Enemy3 extends Enemy {
    private final Random random = new Random();
    private int addX;
    private int addY;
    public Enemy3(int x, int y, Image img) {
        super(x, y, img);
        dead = Sprite.doll_dead;
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
        boolean collide = touchBomb(newX, newY);;
        for (GameObject o : Bomberman.stillObjects) {
            if ((o instanceof Wall || o instanceof Brick)
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
        if (isDead) return;

        int prevX = x;
        if (fitSquare()) {
            int curX = x / Sprite.SCALED_SIZE;
            int curY = y / Sprite.SCALED_SIZE;

            boolean seePlayer = false;
            int newX, newY;
            for (int i = curX - 1; i > 0; i--) {
                newX = i * Sprite.SCALED_SIZE;
                boolean collide = touchBomb(newX, y);
                for (GameObject o : Bomberman.stillObjects) {
                    if ((o instanceof Wall || o instanceof Brick)
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
                    if (prevX < x) {
                        img = Sprite.movingSprite(Sprite.doll_right1, Sprite.doll_right2, Sprite.doll_right2, Bomberman.animate, 30).getFxImage();
                    }
                    if (prevX > x) {
                        img = Sprite.movingSprite(Sprite.doll_left1, Sprite.doll_left2, Sprite.doll_left2, Bomberman.animate, 30).getFxImage();
                    }
                    return;
                }
            }

            for (int i = curX + 1; i < Bomberman.WIDTH; i++) {
                newX = i * Sprite.SCALED_SIZE;
                boolean collide = touchBomb(newX, y);
                for (GameObject o : Bomberman.stillObjects) {
                    if ((o instanceof Wall || o instanceof Brick)
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
                    if (prevX < x) {
                        img = Sprite.movingSprite(Sprite.doll_right1, Sprite.doll_right2, Sprite.doll_right2, Bomberman.animate, 30).getFxImage();
                    }
                    if (prevX > x) {
                        img = Sprite.movingSprite(Sprite.doll_left1, Sprite.doll_left2, Sprite.doll_left2, Bomberman.animate, 30).getFxImage();
                    }
                    return;
                }
            }

            for (int i = curY - 1; i > 0; i--) {
                newY = i * Sprite.SCALED_SIZE;
                boolean collide = touchBomb(x, newY);
                for (GameObject o : Bomberman.stillObjects) {
                    if ((o instanceof Wall || o instanceof Brick)
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

                    if (prevX < x) {
                        img = Sprite.movingSprite(Sprite.doll_right1, Sprite.doll_right2, Sprite.doll_right2, Bomberman.animate, 30).getFxImage();
                    }
                    if (prevX > x) {
                        img = Sprite.movingSprite(Sprite.doll_left1, Sprite.doll_left2, Sprite.doll_left2, Bomberman.animate, 30).getFxImage();
                    }
                    return;
                }
            }

            for (int i = curY + 1; i < Bomberman.HEIGHT; i++) {
                newY = i * Sprite.SCALED_SIZE;
                boolean collide = touchBomb(x, newY);
                for (GameObject o : Bomberman.stillObjects) {
                    if ((o instanceof Wall || o instanceof Brick)
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

                    if (prevX < x) {
                        img = Sprite.movingSprite(Sprite.doll_right1, Sprite.doll_right2, Sprite.doll_right2, Bomberman.animate, 30).getFxImage();
                    }
                    if (prevX > x) {
                        img = Sprite.movingSprite(Sprite.doll_left1, Sprite.doll_left2, Sprite.doll_left2, Bomberman.animate, 30).getFxImage();
                    }
                    return;
                }
            }

            randomMove();
        }
        else {
            modifyPosition();
        }

        if (prevX < x) {
            img = Sprite.movingSprite(Sprite.doll_right1, Sprite.doll_right2, Sprite.doll_right2, Bomberman.animate, 30).getFxImage();
        }
        if (prevX > x) {
            img = Sprite.movingSprite(Sprite.doll_left1, Sprite.doll_left2, Sprite.doll_left2, Bomberman.animate, 30).getFxImage();
        }
    }
}

package GameObject.MovingObjects;

import GameObject.GameObject;
import GameObject.NonMovingObjects.BreakableWall;
import GameObject.NonMovingObjects.Wall;
import Graphics.Sprite;
import javafx.scene.image.Image;
import Main.Bomberman;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Enemy5 extends Enemy {
    private final Random random = new Random();
    private final int[] difX = {-1, 1, 0, 0};
    private final int[] difY = {0, 0, -1, 1};
    private boolean[][] passed = new boolean[25][15];
    private int addX;
    private int addY;
    public Enemy5(int x, int y, Image img) {
        super(x, y, img);
        dead = Sprite.kondoria_dead;
    }

    private class Stats {
        /** x and y are in position not pixels */
        private int x;
        private int y;
        private Stats pre;
        private Stats() {

        }
        private Stats(int x, int y, Stats pre) {
            this.x = x;
            this.y = y;
            this.pre = pre;
        }

        private boolean equals(Stats o) {
            return this.x == o.x && this.y == o.y;
        }
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
        boolean collide = touchBomb(newX, newY);
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

    public void update() {
        super.update();
        int prevX = x;
        if (fitSquare()) {
            Stats root = new Stats(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE, null);
            Stats cur = new Stats();
            int newX, newY;

            Queue<Stats> move = new LinkedList<>();

            for (int i = 1; i < Bomberman.WIDTH; i++)
                for (int j = 1; j < Bomberman.HEIGHT; j++)
                    passed[i][j] = false;

            passed[x / Sprite.SCALED_SIZE][y / Sprite.SCALED_SIZE] = true;
            move.add(root);
            boolean meetPlayer = false;

            while (!move.isEmpty()) {
                cur = move.poll();
                passed[cur.x][cur.y] = true;

                if (Bomberman.player.collision(cur.x * Sprite.SCALED_SIZE, cur.y * Sprite.SCALED_SIZE)) {
                    meetPlayer = true;
                    break;
                }

                for (int i = 0; i < 4; i++) {
                    newX = cur.x + difX[i];
                    newY = cur.y + difY[i];

                    boolean collide = touchBomb(newX, newY);
                    for (GameObject o : Bomberman.stillObjects) {
                        if ((o instanceof Wall || o instanceof BreakableWall)
                                && o.collision(newX * Sprite.SCALED_SIZE, newY * Sprite.SCALED_SIZE)) {
                            collide = true;
                            break;
                        }
                    }

                    if (!collide && !passed[newX][newY]) {
                        move.add(new Stats(newX, newY, cur));
                    }
                }
            }


            if (meetPlayer) {
                while (!cur.pre.equals(root)) {
                    cur = cur.pre;
                }
                addX = cur.x - (x / Sprite.SCALED_SIZE);
                addY = cur.y - (y / Sprite.SCALED_SIZE);
                modifyPosition();

            } else {
                randomMove();
            }

        } else {
            modifyPosition();
        }
        if (prevX < x) {
            img = Sprite.movingSprite(Sprite.kondoria_right1, Sprite.kondoria_right2, Sprite.kondoria_right2, Bomberman.animate, 30).getFxImage();
        }
        if (prevX > x) {
            img = Sprite.movingSprite(Sprite.kondoria_left1, Sprite.kondoria_left2, Sprite.kondoria_left2, Bomberman.animate, 30).getFxImage();
        }
    }
}

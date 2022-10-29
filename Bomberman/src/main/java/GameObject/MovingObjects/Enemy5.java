package GameObject.MovingObjects;

import GameObject.GameObject;
import GameObject.NonMovingObjects.BreakableWall;
import GameObject.NonMovingObjects.Wall;
import Graphics.Sprite;
import javafx.scene.image.Image;
import Main.Bomberman;

import java.util.ArrayDeque;
import java.util.Queue;

public class Enemy5 extends Enemy {
    private int[] difX = {-1, 1, 0, 0};
    private int[] difY = {0, 0, -1, 1};

    private int addX;
    private int addY;
    public Enemy5(int x, int y, Image img) {
        super(x, y, img);
    }

    private class Stats {
        /** x and y are in position not pixels */
        private int x;
        private int y;
        private int time;
        private Stats pre;
        private Stats() {

        }
        private Stats(int x, int y, int time, Stats pre) {
            this.x = x;
            this.y = y;
            this.time = time;
            this.pre = pre;
        }
    }

    private void modifyPosition() {
        x += addX * velocity;
        y += addY * velocity;
    }

    public void update() {
        if (fitSquare()) {
            Stats cur;
            int newX, newY;

            Queue<Stats> move = new ArrayDeque<>();;
            move.add(new Stats(x % Sprite.SCALED_SIZE, y % Sprite.SCALED_SIZE, 0, null));

            while (true) {
                cur = move.remove();
                if (Bomberman.player.collision(cur.x * Sprite.SCALED_SIZE, cur.y * Sprite.SCALED_SIZE)) {
                    break;
                }

                for (int i = 0; i < 4; i++) {
                    newX = cur.x + difX[i];
                    newY = cur.y + difY[i];

                    boolean collide = false;
                    for (GameObject o : Bomberman.stillObjects) {
                        if ((o instanceof Wall || o instanceof BreakableWall)
                                && o.collision(newX * Sprite.SCALED_SIZE, newY * Sprite.SCALED_SIZE)) {
                            collide = true;
                            break;
                        }
                    }

                    if (!collide) {
                        move.add(new Stats(newX, newY, cur.time + 1, cur));
                    }
                }
            }

            while (cur.pre != null) {
                cur = cur.pre;
            }

            addX = cur.x - x;
            addY = cur.y - y;
            modifyPosition();

        } else {
            modifyPosition();
        }
    }
}

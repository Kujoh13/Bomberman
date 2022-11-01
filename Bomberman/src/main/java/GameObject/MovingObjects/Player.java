package GameObject.MovingObjects;

import GameObject.GameObject;
import GameObject.NonMovingObjects.Bomb;
import GameObject.NonMovingObjects.Brick;
import GameObject.NonMovingObjects.Explosion;
import GameObject.NonMovingObjects.Wall;
import Graphics.Sprite;
import Main.Bomberman;
import Sounds.Audio;
import javafx.scene.image.Image;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Player extends GameObject {
    public static int player_speed = 3;
    private int velX = 0;
    private int velY = 0;
    private boolean immortal = false;
    private int moveId;
    public Player() {

    }
    public Player(int x, int y, Image img) {
        super(x, y, img);
    }
    @Override
    public void update() {
        int collisionTimes = checkCollision(velX, velY);
        moveId = 4;
        if (velX > 0) moveId = 0;
        if (velX < 0) moveId = 1;
        if (velY > 0) moveId = 2;
        if (velY < 0) moveId = 3;
        switch (moveId) {
            case 0:
                setImg(Sprite.movingSprite(Sprite.player_right, Sprite.player_right_1, Sprite.player_right_2, Bomberman.animate, 30).getFxImage());
                break;
            case 1:
                setImg(Sprite.movingSprite(Sprite.player_left, Sprite.player_left_1, Sprite.player_left_2, Bomberman.animate, 30).getFxImage());
                break;
            case 2:
                setImg(Sprite.movingSprite(Sprite.player_down, Sprite.player_down_1, Sprite.player_down_2, Bomberman.animate, 30).getFxImage());
                break;
            case 3:
                setImg(Sprite.movingSprite(Sprite.player_up, Sprite.player_up_1, Sprite.player_up_2, Bomberman.animate, 30).getFxImage());
                break;
            default:
                break;
        }
        if (collisionTimes == 0) {
            x = x + velX;
            y = y + velY;
        } else if (collisionTimes == 1) {
            for (GameObject o: Bomberman.stillObjects) {
                if (o instanceof Wall && o.collision(x + velX, y + velY)) {
                    int x1 = Math.max(x + velX, o.getX());
                    int x2 = Math.min(x + velX, o.getX()) + Sprite.SCALED_SIZE - 1;
                    int y1 = Math.max(y + velY, o.getY());
                    int y2 = Math.min(y + velY, o.getY()) + Sprite.SCALED_SIZE - 1;
                    if ((x2 - x1) * (y2 - y1) <= 2 * Player.player_speed * Player.player_speed) {
                        if (o.getX() < x && o.getY() < y) {
                            if (velY < 0) {
                                x = o.getX() + Sprite.SCALED_SIZE;
                                y = y - Player.player_speed;
                            } else {
                                x = x - Player.player_speed;
                                y = o.getY() + Sprite.SCALED_SIZE;
                            }
                        } else if (o.getX() < x && o.getY() > y) {
                            if (velY > 0) {
                                x = o.getX() + Sprite.SCALED_SIZE;
                                y = y + Player.player_speed;
                            } else {
                                x = x - Player.player_speed;
                                y = o.getY() - Sprite.SCALED_SIZE;
                            }
                        } else if (o.getX() > x && o.getY() < y) {
                            if (velX > 0) {
                                x = x + Player.player_speed;
                                y = o.getY() + Sprite.SCALED_SIZE;
                            } else {
                                x = o.getX() - Sprite.SCALED_SIZE;
                                y = y - Player.player_speed;
                            }
                        } else {
                            if (velY > 0) {
                                x = o.getX() - Sprite.SCALED_SIZE;
                                y = y + Player.player_speed;
                            } else {
                                x = x + Player.player_speed;
                                y = o.getY() - Sprite.SCALED_SIZE;
                            }
                        }
                    }
                }
            }
        }
        if (immortal) {
            return;
        }
        for (GameObject o: Bomberman.movingObjects) {
            if (o instanceof Enemy) {
                if (o.collision(this) && Bomberman.status != -1) {
                    Audio.stopMusic();
                    Bomberman.loseTimer = 300;
                    Bomberman.status = -1;
                    Audio.playEffect(Audio.lose);
                }
            }
        }

        for (GameObject o: Bomberman.stillObjects) {
            if (o instanceof Explosion) {
                if (o.collision(this) && Bomberman.status != -1) {
                    Audio.stopMusic();
                    Bomberman.loseTimer = 300;
                    Bomberman.status = -1;
                    Audio.playEffect(Audio.lose);
                }
            }
        }
    }

    Random random = new Random();
    private int[] difX = {-1, 1, 0, 0};
    private int[] difY = {0, 0, -1, 1};
    private boolean[][] passed = new boolean[25][15];
    private int addX;
    private int addY;

    private static class Stats {
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

    private void moveTo(Stats cur) {
        if (fitSquare()) {
            Stats root = new Stats(x, y, null);
            while (!cur.pre.equals(root)) {
                cur = cur.pre;
            }

            addX = cur.x - (x / Sprite.SCALED_SIZE);
            addY = cur.y - (y / Sprite.SCALED_SIZE);
        }

        x += addX * player_speed;
        y += addY * player_speed;
    }
    public void auto() {
        /** To run away from boom first. */

        if (Bomb.numberOfBombs > 0) {
            Stats root = new Stats(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE, null);
            Stats cur = new Stats();
            Stats block = new Stats();
            int newX, newY;

            Queue<Stats> move = new LinkedList<>();

            for (int i = 1; i < Bomberman.WIDTH; i++)
                for (int j = 1; j < Bomberman.HEIGHT; j++)
                    passed[i][j] = false;

            passed[x / Sprite.SCALED_SIZE][y / Sprite.SCALED_SIZE] = true;
            move.add(root);
            boolean meetEnemy = false;

            while (!move.isEmpty()) {
                cur = move.poll();
                passed[cur.x][cur.y] = true;

                for (GameObject o : Bomberman.movingObjects) {
                    if (o instanceof Enemy
                            && o.collision(cur.x * Sprite.SCALED_SIZE, cur.y * Sprite.SCALED_SIZE)) {
                        meetEnemy = true;
                    }
                }

                for (GameObject o : Bomberman.stillObjects) {
                    if (o instanceof Brick)
                        for (int i = 0; i < 4; i++) {
                            if (o.collision(cur.x * Sprite.SCALED_SIZE + difX[i],
                                    cur.y * Sprite.SCALED_SIZE + difY[i])) {
                                block = cur;
                            }
                        }
                }
                if (Bomberman.player.collision(cur.x * Sprite.SCALED_SIZE, cur.y * Sprite.SCALED_SIZE)) {
                    meetEnemy = true;
                    break;
                }

                for (int i = 0; i < 4; i++) {
                    newX = cur.x + difX[i];
                    newY = cur.y + difY[i];

                    if (checkCollision(newX * Sprite.SCALED_SIZE, newY * Sprite.SCALED_SIZE) > 0
                            && !passed[newX][newY]) {
                        move.add(new Stats(newX, newY, cur));
                    }
                }
            }

            if (meetEnemy) {
                moveTo(cur.pre);
                if (fitSquare() && cur.pre.equals(new Stats(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE, null))) {
                    if (Bomb.placeBomb()) {
                        Audio.playEffect(Audio.bomb_fuse);
                    }
                }
            } else {
                 moveTo(block);
                if (fitSquare() && block.equals(new Stats(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE, null))) {
                    if (Bomb.placeBomb()) {
                        Audio.playEffect(Audio.bomb_fuse);
                    }
                }
            }
        } else {
            /** To hide in a place where booms don't reach. */
        }
    }

    private int checkCollision(int velX, int velY) {
        int xTemp = x + velX;
        int yTemp = y + velY;
        int res = 0;
        for (GameObject o: Bomberman.stillObjects) {
            if ((o instanceof Wall || o instanceof Brick)
                && o.collision(xTemp, yTemp)) {
                res++;
            }
        }
        for (Bomb bomb: Bomb.bombs) {
            if (bomb.collision(xTemp, yTemp) && !bomb.collidePlayer) {
                res++;
            }
        }
        return res;
    }

    public int getVelX() {
        return velX;
    }

    public void setVelX(int velX) {
        this.velX = velX;
    }

    public int getVelY() {
        return velY;
    }

    public void setVelY(int velY) {
        this.velY = velY;
    }

    public boolean isImmortal() {
        return immortal;
    }

    public void setImmortal(boolean immortal) {
        this.immortal = immortal;
    }
}

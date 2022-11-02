package GameObject.MovingObjects;

import GameObject.GameObject;
import GameObject.NonMovingObjects.*;
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
    private final int[] difX = {-1, 1, 0, 0};
    private final int[] difY = {0, 0, -1, 1};
    private boolean[][] passed = new boolean[25][15];
    private int addX;
    private int addY;
    private int timer = 0;

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
        Stats root = new Stats(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE, null);
        if (fitSquare()) {
            while (cur.pre != null && !cur.pre.equals(root)) {
                cur = cur.pre;
            }
            addX = cur.x - (x / Sprite.SCALED_SIZE);
            addY = cur.y - (y / Sprite.SCALED_SIZE);
            //System.out.println(cur.x + " " + cur.y);
        }
        modifyPosition();
    }

    private void resetPassed() {
        for (int i = 1; i < Bomberman.WIDTH; i++)
            for (int j = 1; j < Bomberman.HEIGHT; j++)
                passed[i][j] = false;

        passed[x / Sprite.SCALED_SIZE][y / Sprite.SCALED_SIZE] = true;
    }
    private void modifyPosition() {
        x += addX * player_speed;
        y += addY * player_speed;
    }
    public void auto() {
        /** To run away from boom first. */
        Stats root = new Stats(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE, null);
        Stats cur = new Stats();
        Stats block = null;
        int newX, newY;
        Stats safePlace = null;
        Stats end = null;
        Queue<Stats> move = new LinkedList<>();

        resetPassed();
        move.add(root);
        boolean meetEnemy = false;

        while (!move.isEmpty()) {
            cur = move.poll();
            passed[cur.x][cur.y] = true;

            for (GameObject o : Bomberman.movingObjects) {
                if (o instanceof Enemy
                        && o.collision(cur.x * Sprite.SCALED_SIZE, cur.y * Sprite.SCALED_SIZE)) {
                    meetEnemy = true;
                    break;
                }
            }
            if (meetEnemy) {
                break;
            }

            for (GameObject o : Bomberman.stillObjects) {
                if (o instanceof Portal)
                    for (int i = 0; i < 4; i++) {
                        if (o.collision(cur.x * Sprite.SCALED_SIZE + difX[i],
                                cur.y * Sprite.SCALED_SIZE + difY[i])) {
                            end = cur;
                        }
                    }
            }

            if (block == null) {
                for (GameObject o : Bomberman.stillObjects) {
                    if (o instanceof Brick)
                        for (int i = 0; i < 4; i++) {
                            if (o.collision(cur.x * Sprite.SCALED_SIZE + difX[i],
                                    cur.y * Sprite.SCALED_SIZE + difY[i])) {
                                block = cur;
                            }
                        }
                }
            }

            for (int i = 0; i < 4; i++) {
                newX = cur.x + difX[i];
                newY = cur.y + difY[i];

                boolean collide = touchBomb(newX * Sprite.SCALED_SIZE, newY * Sprite.SCALED_SIZE);
                for (GameObject o : Bomberman.stillObjects) {
                    if ((o instanceof Wall || o instanceof Brick)
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

        if (Bomb.bombs.size() < 1) {
            if (--timer > 0) {
                return;
            }

            if (meetEnemy) {
                moveTo(cur.pre);
                if (checkBombPlace().equals(cur.pre)) {
                    if (Bomb.placeBomb()) {
                        Audio.playEffect(Audio.bomb_fuse);
                    }

                    addX = -addX;
                    addY = -addY;
                }
            } else {
                if (end != null) {
                    moveTo(end);
                }

                if (block != null) {
                    moveTo(block);
                    if (block.equals(new Stats(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE, null))) {
                        if (Bomb.placeBomb()) {
                            Audio.playEffect(Audio.bomb_fuse);
                        }

                        addX = -addX;
                        addY = -addY;
                    }
                }
            }
        } else {
            move.clear();

            root = new Stats(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE, null);

            move.add(root);
            resetPassed();

            while (!move.isEmpty()) {
                cur = move.poll();
                passed[cur.x][cur.y] = true;

                if (!getBoomed(cur.x * Sprite.SCALED_SIZE, cur.y * Sprite.SCALED_SIZE)) {
                    safePlace = cur;
                    break;
                }

                for (int i = 0; i < 4; i++) {
                    newX = cur.x + difX[i];
                    newY = cur.y + difY[i];

                    boolean collide = touchBomb(newX * Sprite.SCALED_SIZE, newY * Sprite.SCALED_SIZE);
                    for (GameObject o : Bomberman.stillObjects) {
                        if ((o instanceof Wall || o instanceof Brick)
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
            if (safePlace != null) {
                moveTo(safePlace);
                timer = 15;
                //System.out.println(safePlace.x + " " + safePlace.y);
            }
        }
    }

    private Stats checkBombPlace() {
        int x = Bomberman.player.getX();
        int y = Bomberman.player.getY();
        int finalX = 1, finalY = 1;
        int collisionArea = 0;
        for (GameObject o: Bomberman.stillObjects) {
            if (o instanceof Grass && o.collision(Bomberman.player)) {
                int x1 = Math.max(Bomberman.player.getX(), o.getX());
                int x2 = Math.min(Bomberman.player.getX(), o.getX()) + Sprite.SCALED_SIZE - 1;
                int y1 = Math.max(Bomberman.player.getY(), o.getY());
                int y2 = Math.min(Bomberman.player.getY(), o.getY()) + Sprite.SCALED_SIZE - 1;
                if ((x2 - x1) * (y2 - y1) > collisionArea) {
                    finalX = o.getX() / Sprite.SCALED_SIZE;
                    finalY = o.getY() / Sprite.SCALED_SIZE;
                    collisionArea = (x2 - x1) * (y2 - y1);
                }
            }
        }

        return new Stats(finalX, finalY, null);
    }

    private static boolean collisionBetween(int ox, int oy, int px, int py) {
        return ox < px + Sprite.SCALED_SIZE && px < ox + Sprite.SCALED_SIZE
                && oy < py + Sprite.SCALED_SIZE && py < oy + Sprite.SCALED_SIZE;
    }

    private boolean getBoomed(int pixelX, int pixelY) {
        int difX, difY;
        for (Bomb bomb : Bomb.bombs) {
            difX = Math.abs(bomb.getX() - pixelX);
            difY = Math.abs(bomb.getY() - pixelY);

            if (difX / Sprite.SCALED_SIZE != 0
                && difY / Sprite.SCALED_SIZE != 0) {
                continue;
            }
            boolean collide = false;
            if (difY / Sprite.SCALED_SIZE == 0) {
                if (bomb.getX() > getX()) {
                    for (int i = bomb.getX(); i >= bomb.getX() - Sprite.SCALED_SIZE * Bomb.radius; i -= Sprite.SCALED_SIZE) {
                        if (collisionBetween(pixelX, pixelY, i, bomb.getY())) {
                            return true;
                        }
                        for (GameObject o : Bomberman.stillObjects) {
                            if ((o instanceof Wall || o instanceof Brick)
                                    && o.collision(i, bomb.getY())) {
                                collide = true;

                                break;
                            }
                        }

                        if (collide) {
                            break;
                        }
                    }
                } else {
                    for (int i = bomb.getX(); i <= bomb.getX() + Sprite.SCALED_SIZE * Bomb.radius; i += Sprite.SCALED_SIZE) {
                        if (collisionBetween(pixelX, pixelY, i, bomb.getY())) {
                            return true;
                        }

                        for (GameObject o : Bomberman.stillObjects) {
                            if ((o instanceof Wall || o instanceof Brick)
                                    && o.collision(i, bomb.getY())) {
                                collide = true;
                                break;
                            }
                        }

                        if (collide) {
                            break;
                        }
                    }
                }
            } else {
                if (bomb.getY() > getY()) {
                    for (int i = bomb.getY(); i >= bomb.getY() - Sprite.SCALED_SIZE * Bomb.radius; i -= Sprite.SCALED_SIZE) {
                        if (collisionBetween(pixelX, pixelY, bomb.getX(), i)) {
                            return true;
                        }

                        for (GameObject o : Bomberman.stillObjects) {
                            if ((o instanceof Wall || o instanceof Brick)
                                    && o.collision(bomb.getX(), i)) {
                                collide = true;
                                break;
                            }
                        }

                        if (collide) {
                            break;
                        }
                    }
                } else {
                    for (int i = bomb.getY(); i <= bomb.getY() + Sprite.SCALED_SIZE * Bomb.radius; i += Sprite.SCALED_SIZE) {
                        if (collisionBetween(pixelX, pixelY, bomb.getX(), i)) {
                            return true;
                        }

                        for (GameObject o : Bomberman.stillObjects) {
                            if ((o instanceof Wall || o instanceof Brick)
                                    && o.collision(bomb.getX(), i)) {
                                collide = true;
                                break;
                            }
                        }

                        if (collide) {
                            break;
                        }
                    }
                }
            }
        }

        return false;
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

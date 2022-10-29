package GameObject.MovingObjects;

import GameObject.GameObject;
import GameObject.NonMovingObjects.Bomb;
import GameObject.NonMovingObjects.BreakableWall;
import GameObject.NonMovingObjects.Explosion;
import GameObject.NonMovingObjects.Wall;
import Main.Bomberman;
import Sounds.Audio;
import javafx.scene.image.Image;

public class Player extends GameObject {
    public static int player_speed = 6;
    private int velX = 0;
    private int velY = 0;
    private boolean immortal = false;
    public Player() {

    }
    public Player(int x, int y, Image img) {
        super(x, y, img);
    }
    @Override
    public void update() {
        if (!checkCollision(velX, velY)) {
            x = x + velX;
            y = y + velY;
        }
        if (immortal) {
            return;
        }
        for (GameObject o: Bomberman.movingObjects) {
            if (o instanceof Enemy) {
                if (o.collision(this) && Bomberman.status != -1) {
                    Audio.stopMusic();
                    Bomberman.status = -1;
                    Audio.playEffect(Audio.lose);
                }
            }
        }

        for (GameObject o: Bomberman.stillObjects) {
            if (o instanceof Explosion) {
                if (o.collision(this) && Bomberman.status != -1) {
                    Audio.stopMusic();
                    Bomberman.status = -1;
                    Audio.playEffect(Audio.lose);
                }
            }
        }
    }

    private int[] difX = {-1, 1, 0, 0};
    private int[] difY = {0, 0, -1, 1};
    private boolean[][] passed = new boolean[25][15];
    private int addX;
    private int addY;
    public void auto() {
        if (Bomb.numberOfBombs > 0) {

        }
    }

    private boolean checkCollision(int velX, int velY) {
        int xTemp = x + velX;
        int yTemp = y + velY;
        for (GameObject o: Bomberman.stillObjects) {
            if ((o instanceof Wall || o instanceof BreakableWall)
            && o.collision(xTemp, yTemp)) {
                return true;
            }
        }
        return false;
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

package GameObject.MovingObjects;

import GameObject.GameObject;
import GameObject.NonMovingObjects.BreakableWall;
import GameObject.NonMovingObjects.Wall;
import Main.Bomberman;
import javafx.scene.image.Image;

public class Player extends GameObject {
    public static int player_speed = 6;
    private int velX = 0;
    private int velY = 0;
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
    }

    private boolean checkCollision(int velX, int velY) {
        int xTemp = x + velX;
        int yTemp = y + velY;
        for(GameObject o: Bomberman.stillObjects) {
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
}

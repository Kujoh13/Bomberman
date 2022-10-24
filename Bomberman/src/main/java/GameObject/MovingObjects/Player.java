package GameObject.MovingObjects;

import GameObject.GameObject;
import GameObject.NonMovingObjects.BreakableWall;
import GameObject.NonMovingObjects.Wall;
import Main.Bomberman;
import javafx.scene.image.Image;

public class Player extends GameObject {
    public static int velocity = 6;
    private boolean leftP = false;
    private boolean rightP = false;
    private boolean upP = false;
    private boolean downP = false;
    public Player() {

    }
    public Player(int x, int y, Image img) {
        super(x, y, img);
    }
    @Override
    public void update() {

    }

    public void moveUp() {
        if (!checkCollision(0, -velocity)) {
            y -= velocity;
        }
    }

    public void moveDown() {
        if (!checkCollision(0, velocity)) {
            y += velocity;
        }
    }

    public void moveLeft() {
        if (!checkCollision(-velocity, 0)) {
            x -= velocity;
        }
    }

    public void moveRight() {
        if (!checkCollision(velocity, 0)) {
            x += velocity;
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
}

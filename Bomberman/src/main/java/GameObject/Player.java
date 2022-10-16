package GameObject;

import Main.Bomberman;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;

public class Player extends GameObject {
    public static int velocity = 4;
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

    public void moveUp(int id) {
        if (id == 0) {
            upP = true;
            if (!checkCollision(0, -velocity)) {
                y -= velocity;
            }
        } else {
            upP = false;
            if (downP) {
                if (checkCollision(0, velocity)) {
                    y += velocity;
                }
            }
        }
    }

    public void moveDown(int id) {
        if (id == 0) {
            downP = true;
            if (!checkCollision(0, velocity)) {
                y += velocity;
            }
        } else {
            downP = false;
            if (upP) {
                if (checkCollision(0, -velocity)) {
                    y -= velocity;
                }
            }
        }
    }

    public void moveLeft(int id) {
        if (id == 0) {
            leftP = true;
            if (!checkCollision(-velocity, 0)) {
                x -= velocity;
            }
        } else {
            leftP = false;
            if (rightP) {
                if (checkCollision(velocity, 0)) {
                    x += velocity;
                }
            }
        }
    }

    public void moveRight(int id) {
        if (id == 0) {
            rightP = true;
            if (!checkCollision(velocity, 0)) {
                x += velocity;
            }
        } else {
            rightP = false;
            if (leftP) {
                if (checkCollision(-velocity, 0)) {
                    x -= velocity;
                }
            }
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

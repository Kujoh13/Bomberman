package GameObject.NonMovingObjects;

import GameObject.GameObject;
import GameObject.MovingObjects.Player;
import Main.Bomberman;
import javafx.scene.image.Image;

import java.util.Random;

public class Item extends GameObject {
    int timer;
    public Item(int x, int y, Image img) {
        super(x, y, img);
    }

    boolean PlayerPickUp() {
        if (Bomberman.player.getX() == x
                && Bomberman.player.getY() == y) {
            return true;
        }

        return false;
    }

    @Override
    public void update() {

    }


}
class Immortality extends Item {
    public Immortality(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        if (PlayerPickUp()) {
            timer = 15;
        }

        if (timer > 0) {
            timer--;
            /** Create code to be immortal. */
        }
    }
}

class SpeedUp extends Item {
    public SpeedUp(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        if (PlayerPickUp()) {
            Player.player_speed *= 2;
        }
    }
}

class Teleport extends Item {
    public Teleport(int x, int y, Image img) {
        super(x, y, img);
    }

    public void update() {
        Random rd = new Random();

        int nextX = rd.nextInt() % (Bomberman.WIDTH - 1) + 1;
        int nextY = rd.nextInt() % (Bomberman.HEIGHT - 1) + 1;

        while (Bomberman.map[nextX][nextY] != 2) {
            nextX = rd.nextInt() % (Bomberman.WIDTH - 1) + 1;
            nextY = rd.nextInt() % (Bomberman.HEIGHT - 1) + 1;
        }

        Bomberman.player.setX(nextX);
        Bomberman.player.setY(nextY);
    }
}

package GameObject.NonMovingObjects;

import GameObject.GameObject;
import Graphics.Sprite;
import Main.Bomberman;
import javafx.scene.image.Image;

public class Bomb extends GameObject {
    private int radius = 1;
    private int timer = 150;
    public static int numberOfBombs = 1;
    public Bomb(int x, int y, Image img) {
        super(x, y, img);
    }

    public void update() {
        timer--;
    }

    public static void placeBomb() {
        if (Bomberman.bombs.size() == numberOfBombs) {
            return;
        }
        int x = Bomberman.player.getX();
        int y = Bomberman.player.getY();
        int finalX = 1, finalY = 1;
        int collisionArea = 0;
        for (GameObject o: Bomberman.stillObjects) {
            if (o instanceof Grass && o.collision(Bomberman.player)) {
                int x1 = Math.max(Bomberman.player.getX(), o.getX());
                int x2 = Math.min(Bomberman.player.getX(), o.getX()) + Sprite.SCALED_SIZE;
                int y1 = Math.max(Bomberman.player.getY(), o.getY());
                int y2 = Math.min(Bomberman.player.getY(), o.getY()) + Sprite.SCALED_SIZE;
                if ((x2 - x1) * (y2 - y1) > collisionArea) {
                    finalX = o.getX() / Sprite.SCALED_SIZE;
                    finalY = o.getY() / Sprite.SCALED_SIZE;
                    collisionArea = (x2 - x1) * (y2 - y1);
                }
            }
        }
        Bomberman.bombs.add(new Bomb(finalX, finalY,Sprite.bomb.getFxImage()));
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }
}

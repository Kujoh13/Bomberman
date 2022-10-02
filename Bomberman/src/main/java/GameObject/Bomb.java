package GameObject;

import javafx.scene.image.Image;

public class Bomb extends GameObject {
    private int radius = 1;
    private int timer = 90;

    public Bomb(int x, int y, Image img) {
        super(x, y, img);
    }

    public void update() {
        timer--;
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

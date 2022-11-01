package GameObject.NonMovingObjects;

import GameObject.GameObject;
import Graphics.Sprite;
import Main.Bomberman;
import javafx.scene.image.Image;

public class Brick extends GameObject {
    public Brick(int x, int y, Image img) {
        super(x, y, img);
    }
    private int timer = 0;
    @Override
    public void update() {
        if (timer == 15) {
            img = Sprite.brick_exploded.getFxImage();
            Bomberman.stillObjects.remove(this);
            Bomberman.stillObjects.add(new Grass(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE, Sprite.grass.getFxImage()));
            Bomberman.stillObjects.add(this);
        } else if (timer == 10) {
            img = Sprite.brick_exploded1.getFxImage();
        } else if (timer == 5) {
            img = Sprite.brick_exploded2.getFxImage();
        }
        timer--;
        if (timer == 0) {
            int curX = x / Sprite.SCALED_SIZE;
            int curY = y / Sprite.SCALED_SIZE;
            Bomberman.map[curY][curX] = 2;
            Bomberman.stillObjects.remove(this);
            if (Bomberman.items[curY][curX] != -1) {
                GameObject object;
                if (Bomberman.items[curY][curX] == 0) {
                    object = new Portal(curX, curY, Sprite.portal.getFxImage());
                } else if (Bomberman.items[curY][curX] == 1) {
                    object = new Flame(curX, curY, Sprite.powerup_flames.getFxImage());
                } else if (Bomberman.items[curY][curX] == 2) {
                    object = new Bombs(curX, curY, Sprite.powerup_bombs.getFxImage());
                } else if (Bomberman.items[curY][curX] == 3){
                    object = new SpeedUp(curX, curY, Sprite.powerup_speed.getFxImage());
                } else if (Bomberman.items[curY][curX] == 4) {
                    object = new Immortality(curX, curY, Sprite.powerup_flamepass.getFxImage());
                } else {
                    object = new Teleport(curX, curY, Sprite.powerup_wallpass.getFxImage());
                }
                Bomberman.stillObjects.add(object);
            }
        }
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }
}

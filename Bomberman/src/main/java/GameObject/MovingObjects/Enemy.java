package GameObject.MovingObjects;

import GameObject.GameObject;
import Graphics.Sprite;
import Main.Bomberman;
import javafx.scene.image.Image;

public class Enemy extends GameObject {
    public int velocity = 2;
    public Sprite dead;
    public int deadTimer = 30;
    public boolean isDead = false;
    public Enemy(int x, int y, Image img) {
        super(x, y, img);
    }

    public static boolean inMap(int x, int y) {
        return x >= Sprite.SCALED_SIZE && x <= (Bomberman.WIDTH - 2) * Sprite.SCALED_SIZE
                && y >= Sprite.SCALED_SIZE && y <= (Bomberman.HEIGHT - 2) * Sprite.SCALED_SIZE;
    }

    @Override
    public void update() {
        if (isDead) {
            if (this instanceof Enemy3) {
                Bomberman.movingObjects.add(new Enemy2(x - (x % Sprite.SCALED_SIZE), y - (y % Sprite.SCALED_SIZE), Sprite.oneal_dead.getFxImage()));
                Bomberman.movingObjects.add(new Enemy2(x - (x % Sprite.SCALED_SIZE), y - (y % Sprite.SCALED_SIZE), Sprite.oneal_dead.getFxImage()));
            } else {
                img = dead.getFxImage();
                deadTimer--;
                if (deadTimer == 0) {
                    Bomberman.movingObjects.remove(this);
                }
            }
        }
    }
}

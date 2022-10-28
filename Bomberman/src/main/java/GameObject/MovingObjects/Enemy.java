package GameObject.MovingObjects;

import GameObject.GameObject;
import Graphics.Sprite;
import Main.Bomberman;
import javafx.scene.image.Image;

public class Enemy extends GameObject {
    public int velocity = 2;
    public Enemy(int x, int y, Image img) {
        super(x, y, img);
    }

    public boolean fitSquare() {
        return x % Sprite.SCALED_SIZE == 0 && y % Sprite.SCALED_SIZE == 0;
    }

    public static boolean inMap(int x, int y) {
        return x >= Sprite.SCALED_SIZE && x <= (Bomberman.WIDTH - 1) * Sprite.SCALED_SIZE
                && y >= Sprite.SCALED_SIZE && y <= (Bomberman.HEIGHT - 1) * Sprite.SCALED_SIZE;
    }
    @Override
    public void update() {
        
    }
}

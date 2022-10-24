package GameObject.MovingObjects;

import GameObject.GameObject;
import javafx.scene.image.Image;

public class Enemy extends GameObject {
    public int velocity = 6;
    public Enemy(int x, int y, Image img) {
        super(x, y, img);
    }
    @Override
    public void update() {
        
    }
}

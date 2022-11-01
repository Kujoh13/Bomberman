package GameObject.NonMovingObjects;

import GameObject.GameObject;
import GameObject.MovingObjects.Enemy;
import GameObject.MovingObjects.Enemy2;
import GameObject.MovingObjects.Enemy3;
import Graphics.Sprite;
import Main.Bomberman;
import Sounds.Audio;
import javafx.scene.image.Image;

public class Explosion extends GameObject {
    private int timer = 15;

    public Explosion(int x, int y, Image img) {
        super(x, y, img);
    }

    public void update() {
        for(GameObject o: Bomberman.movingObjects) {
            if (o instanceof Enemy && o.collision(this)) {
                if (!((Enemy) o).isDead) {
                    Audio.playEffect(Audio.enemy_die);
                }
                ((Enemy) o).isDead = true;
            }
        }
        timer--;
        if (timer == 0) {
            Bomberman.stillObjects.remove(this);
        }
    }
}
